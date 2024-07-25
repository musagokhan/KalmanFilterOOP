package kf;

import java.util.ArrayList;
import java.util.List;

import kf.utils.KFConstant;

public class KFInitation {
	
	private double[][] initKinematicDatas;
	private double[][] initCovarianceDatas;
	private int operationalDimension;
	private int storage =0;
	private final int referanceMeasurementNumber = 3;
	
	private double[] timeArray = new double[referanceMeasurementNumber];
	private double[] xPositionArray  = new double[referanceMeasurementNumber];
	private double[] yPositionArray  = new double[referanceMeasurementNumber];
	private double[] zPositionArray  = new double[referanceMeasurementNumber];
	
	List<double[][]> XandPmatrices = new ArrayList<>();

	
	public KFInitation() {}
	
	private void mainKFInitationForStateVector(double[] currentMeasurement) {
		this.storage = this.storage + 1;
		
		if (this.storage < (KFConstant.sniffMeasNumForInitStateVector + 1) ) { //preparing phase
			this.timeArray[this.storage -1] = currentMeasurement[3];
			this.xPositionArray[this.storage -1] =  currentMeasurement[0];
			this.yPositionArray[this.storage -1] = currentMeasurement[1];
			this.zPositionArray[this.storage -1] = currentMeasurement[2];		
			this.initKinematicDatas = null;
			this.initCovarianceDatas = null;
		} else {
			double[] xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] ,
															this.timeArray[0], this.timeArray[1], this.timeArray[2]);
			
			double[] yPositionDatas = kinematicsCalculate(this.yPositionArray[0], this.yPositionArray[1], this.yPositionArray[2] ,
															this.timeArray[0], this.timeArray[1], this.timeArray[2]);
			
			double[] zPositionDatas = kinematicsCalculate(this.zPositionArray[0], this.zPositionArray[1], this.zPositionArray[2] ,
					this.timeArray[0], this.timeArray[1], this.timeArray[2]);
			
			this.initKinematicDatas = new double[][]{{
					xPositionDatas[0], yPositionDatas[0], zPositionDatas[0],
					xPositionDatas[1], yPositionDatas[1], zPositionDatas[1],
					xPositionDatas[2], yPositionDatas[2], zPositionDatas[2]
			}};
			
		}
		
	}
	
	private double[][] mainKFInitationForCovarianceMatrix (){
		this.initCovarianceDatas = new double[this.operationalDimension][this.operationalDimension ];
		for (int i = 0; i < this.operationalDimension; i++) {
			this.initCovarianceDatas[i][i] = 1;
		}
		return this.initCovarianceDatas;
	}
	
 	private double[] kinematicsCalculate (double xA, double xB, double xC, double tA, double tB, double tC) {
		double v1 = (xB - xA)/(tB - tA);
		double v2 = (xC - xB)/(tC - tB);
		double v = (v2+v1)/2;
		double a = accelerationCalculate(v2, v1, tA, tC);
		double position =  xA + v1 * (tC -tA) + 0.5 * a * Math.pow( (tC -tA), 2) ;
		double[] result = {position, v , a};
		return result;
	}
	
	private double accelerationCalculate (double vA, double vB, double tA, double tC) {
		return (vB - vA)/(tC - tA);
	}
	
	public List<double[][]> getMainKFInitation(double[] currentMeasurement) {
		
		
		
		this.operationalDimension = (int) Math.pow( (currentMeasurement.length - 1), 2); // -1 for time
				
		mainKFInitationForStateVector(currentMeasurement);
		mainKFInitationForCovarianceMatrix();
		
		this.XandPmatrices.add(0, this.initKinematicDatas);
		this.XandPmatrices.add(1, this.initCovarianceDatas);
		
		return this.XandPmatrices;
	}
	
}
