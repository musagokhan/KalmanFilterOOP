package kf.init;


import kf.model.ABSKFInit;
import kf.model.IKFinit;
import kf.utils.KFConstant;

public class KF_SniffMeasurement  extends ABSKFInit {
	
	private double[][] initstateVectoreDatas;
	private double[][] initCovarianceDatas;
	private int operationalDimension;
	private int storage =0;
	private boolean validity;
	
	private double[][] currentMeasurement;
	private double currentMeasurementTime;
	
	private double[] timeArray       = new double[KFConstant.sniffMeasNumForInitStateVector];
	private double[] xPositionArray  = new double[KFConstant.sniffMeasNumForInitStateVector];
	private double[] yPositionArray  = new double[KFConstant.sniffMeasNumForInitStateVector];
	private double[] zPositionArray  = new double[KFConstant.sniffMeasNumForInitStateVector];
	

	
	public KF_SniffMeasurement() {}
	
	
 	private double[] kinematicsCalculate (double xA, double xB, double xC, double tA, double tB, double tC) {
		double v1 = (xB - xA)/(tB - tA);
		double v2 = (xC - xB)/(tC - tB);
		double v = (v2+v1)/2;
		double a =  accelerationCalculate(v2, v1, tA, tC);  //!!! a : closed. if it is open. a is bigger stepbystep
		double position =  xA + v1 * (tC -tA) + 0.5 * a * Math.pow( (tC -tA), 2) ;
		double[] result = {position, v , a};
		return result;
	}
	
	private double accelerationCalculate (double vA, double vB, double tA, double tC) {
		return (vB - vA)/(tC - tA);
	}
	
	
	
	@Override 	
	protected void stateVectorEstimate(){
		this.storage = this.storage + 1;
		if (this.storage < (KFConstant.sniffMeasNumForInitStateVector + 1) ) { //preparing phase +1 next for FOR_LOOP
			if (this.currentMeasurement.length == 3) {	
				this.xPositionArray[this.storage -1] = this.currentMeasurement[0][0];
				this.yPositionArray[this.storage -1] = this.currentMeasurement[1][0];
				this.zPositionArray[this.storage -1] = this.currentMeasurement[2][0];
			}else if (this.currentMeasurement.length == 2) {
				this.xPositionArray[this.storage -1] = this.currentMeasurement[0][0];
				this.yPositionArray[this.storage -1] = this.currentMeasurement[1][0];
			}else if (this.currentMeasurement.length == 1) {
				this.xPositionArray[this.storage -1] =  this.currentMeasurement[0][0];
			}else {
				System.err.println("ERR. Check your Measurement Length. TRUE input : 0< Measurement_Length <4");
			}	
			
			this.timeArray[this.storage -1] = this.currentMeasurementTime;
			this.initstateVectoreDatas  = null;
			this.initCovarianceDatas = null;
			
			this.validity = false;
			
		} else {
			
			this.validity = true;
			double[] xPositionDatas = null;
			double[] yPositionDatas = null;
			double[] zPositionDatas = null;
			
			if (this.currentMeasurement.length == 3) {
				xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				yPositionDatas = kinematicsCalculate(this.yPositionArray[0], this.yPositionArray[1], this.yPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				zPositionDatas = kinematicsCalculate(this.zPositionArray[0], this.zPositionArray[1], this.zPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				this.initstateVectoreDatas = new double[][]{ {xPositionDatas[0]}, {yPositionDatas[0]}, {zPositionDatas[0]},	{xPositionDatas[1]}, {yPositionDatas[1]}, {zPositionDatas[1]},	{xPositionDatas[2]}, {yPositionDatas[2]}, {zPositionDatas[2]}};
			}else if (this.currentMeasurement.length == 2) {
				xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				yPositionDatas = kinematicsCalculate(this.yPositionArray[0], this.yPositionArray[1], this.yPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				this.initstateVectoreDatas = new double[][]{ {xPositionDatas[0]}, {yPositionDatas[0]},	{xPositionDatas[1]}, {yPositionDatas[1]},	{xPositionDatas[2]}, {yPositionDatas[2]}};
			}else if (this.currentMeasurement.length == 1) {
				xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				this.initstateVectoreDatas = new double[][]{ {xPositionDatas[0]},	{xPositionDatas[1]},	{xPositionDatas[2]}};
			}else {
				System.err.println("ERR. Check your Measurement Length. TRUE input : 0< Measurement_Length <4");
			}

		}
		
	}
	
	@Override 
	protected void covarianceMatrixEstimate (){
		this.initCovarianceDatas = new double[this.operationalDimension][this.operationalDimension ];
		for (int i = 0; i < this.operationalDimension; i++) {
			this.initCovarianceDatas[i][i] = 1;
		}
	}
	
	@Override 
	protected boolean mainKFInitation(double[][] currentMeasurement, double currentMeasurementTime) {
		this.operationalDimension = currentMeasurement.length * 3; 
		this.currentMeasurement = currentMeasurement;
		this.currentMeasurementTime = currentMeasurementTime;
				
		stateVectorEstimate();
		covarianceMatrixEstimate();	
			
		return this.validity;
	}
	
	
	
	
	public double[][] getStateVector() {return this.initstateVectoreDatas;};
	
	public double[][] getCovarianceMatrix() {return  this.initCovarianceDatas;};
	
	public boolean getValidity() { return this.validity;}



	
}
