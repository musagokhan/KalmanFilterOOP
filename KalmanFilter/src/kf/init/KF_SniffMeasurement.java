package kf.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kf.model.IKFinit;
import kf.utils.KFConstant;

public class KF_SniffMeasurement  implements  IKFinit{
	
	private double[][] initKinematicDatas;
	private double[][] initCovarianceDatas;
	private int operationalDimension;
	private int storage =0;
	
	private double[] timeArray       = new double[KFConstant.sniffMeasNumForInitStateVector];
	private double[] xPositionArray  = new double[KFConstant.sniffMeasNumForInitStateVector];
	private double[] yPositionArray  = new double[KFConstant.sniffMeasNumForInitStateVector];
	private double[] zPositionArray  = new double[KFConstant.sniffMeasNumForInitStateVector];
	
	List<double[][]> XandPmatrices = new ArrayList<>();

	
	public KF_SniffMeasurement() {}
	
	private void mainKFInitationForStateVector(double[][] currentMeasurement, double currentMeasurementTime) {
		this.storage = this.storage + 1;
		if (this.storage < (KFConstant.sniffMeasNumForInitStateVector + 1) ) { //preparing phase +1 next for FOR_LOOP
			if (currentMeasurement.length == 3) {	
				this.xPositionArray[this.storage -1] =  currentMeasurement[0][0];
				this.yPositionArray[this.storage -1] = currentMeasurement[1][0];
				this.zPositionArray[this.storage -1] = currentMeasurement[2][0];	
			}else if (currentMeasurement.length == 2) {
				this.xPositionArray[this.storage -1] =  currentMeasurement[0][0];
				this.yPositionArray[this.storage -1] = currentMeasurement[1][0];
			}else if (currentMeasurement.length == 1) {
				this.xPositionArray[this.storage -1] =  currentMeasurement[0][0];
			}else {
				System.err.println("ERR. Check your Measurement Length. TRUE input : 0< Measurement_Length <4");
			}				
			this.timeArray[this.storage -1] = currentMeasurementTime;
			this.initKinematicDatas = null;
			this.initCovarianceDatas = null;
			
		} else {
			double[] xPositionDatas = null;
			double[] yPositionDatas = null;
			double[] zPositionDatas = null;
			
			if (currentMeasurement.length == 3) {
				xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				yPositionDatas = kinematicsCalculate(this.yPositionArray[0], this.yPositionArray[1], this.yPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				zPositionDatas = kinematicsCalculate(this.zPositionArray[0], this.zPositionArray[1], this.zPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				this.initKinematicDatas = new double[][]{ {xPositionDatas[0]}, {yPositionDatas[0]}, {zPositionDatas[0]},	{xPositionDatas[1]}, {yPositionDatas[1]}, {zPositionDatas[1]},	{xPositionDatas[2]}, {yPositionDatas[2]}, {zPositionDatas[2]}};
			}else if (currentMeasurement.length == 2) {
				xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				yPositionDatas = kinematicsCalculate(this.yPositionArray[0], this.yPositionArray[1], this.yPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				this.initKinematicDatas = new double[][]{ {xPositionDatas[0]}, {yPositionDatas[0]},	{xPositionDatas[1]}, {yPositionDatas[1]},	{xPositionDatas[2]}, {yPositionDatas[2]}};
			}else if (currentMeasurement.length == 1) {
				xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] , this.timeArray[0], this.timeArray[1], this.timeArray[2]);
				this.initKinematicDatas = new double[][]{ {xPositionDatas[0]},	{xPositionDatas[1]},	{xPositionDatas[2]}};
			}else {
				System.err.println("ERR. Check your Measurement Length. TRUE input : 0< Measurement_Length <4");
			}

		}
		
	}
	
	private void mainKFInitationForCovarianceMatrix (){
		this.initCovarianceDatas = new double[this.operationalDimension][this.operationalDimension ];
		for (int i = 0; i < this.operationalDimension; i++) {
			this.initCovarianceDatas[i][i] = 1;
		}
	}
	
 	private double[] kinematicsCalculate (double xA, double xB, double xC, double tA, double tB, double tC) {
 		System.out.println("kinematicsCalculate ");
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
	public List<double[][]> getMainKFInitation(double[][] currentMeasurement, double currentMeasurementTime) {
		
		this.operationalDimension = currentMeasurement.length * 3; 
				
		mainKFInitationForStateVector(currentMeasurement, currentMeasurementTime);
		mainKFInitationForCovarianceMatrix();
				
		this.XandPmatrices.add(0, this.initKinematicDatas);
		this.XandPmatrices.add(1, this.initCovarianceDatas);
		
		return this.XandPmatrices;
	}
	
}
