package kf;

import java.util.Arrays;

public class KFInitation_old {
	
	private double[] initKinematicDatas;
	private int storage =0;
	private final int referanceMeasurementNumber = 3;
	
	private double[] timeArray = new double[referanceMeasurementNumber];
	private double[] xPositionArray  = new double[referanceMeasurementNumber];
	private double[] yPositionArray  = new double[referanceMeasurementNumber];
	private double[] zPositionArray  = new double[referanceMeasurementNumber];
	
	public KFInitation_old() {
		
	}
	
	private double[] mainKFInitation(double[] currentMeasurement) {
		this.storage = this.storage + 1;
		
		if (this.storage < 4) {
			this.timeArray[this.storage -1] = currentMeasurement[3];
			this.xPositionArray[this.storage -1] =  currentMeasurement[0];
			this.yPositionArray[this.storage -1] = currentMeasurement[1];
			this.zPositionArray[this.storage -1] = currentMeasurement[2];				
			return null;
		} else {
			double[] xPositionDatas = kinematicsCalculate(this.xPositionArray[0], this.xPositionArray[1], this.xPositionArray[2] ,
															this.timeArray[0], this.timeArray[1], this.timeArray[2]);
			
			double[] yPositionDatas = kinematicsCalculate(this.yPositionArray[0], this.yPositionArray[1], this.yPositionArray[2] ,
															this.timeArray[0], this.timeArray[1], this.timeArray[2]);
			
			double[] zPositionDatas = kinematicsCalculate(this.zPositionArray[0], this.zPositionArray[1], this.zPositionArray[2] ,
					this.timeArray[0], this.timeArray[1], this.timeArray[2]);
			
			this.initKinematicDatas = new double[]{
					xPositionDatas[0], yPositionDatas[0], zPositionDatas[0],
					xPositionDatas[1], yPositionDatas[1], zPositionDatas[1],
					xPositionDatas[2], yPositionDatas[2], zPositionDatas[2]
			};
			
			System.out.println("Eski   : " +  Arrays.toString(this.initKinematicDatas));

			return this.initKinematicDatas;
		}
		
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

	public double[] getMainKFInitation(double[] currentMeasurement) {
		return mainKFInitation(currentMeasurement);
	}
}
