package kf;

import utils.MathOperation;

public class KF {
	
	private double[] positionList;
	private double[] speedList;
	private double[] accelerationList;
	
	private double[] stateVector;

		
	public KF(double[] stateVector) {
		
		this.stateVector = stateVector;
		this.positionList = new double[3];
		this.speedList = new double[3];
		this.accelerationList = new double[3];
	}
	
	
	private void KFPredicted(double deltaTime) {		
		
		System.arraycopy(this.stateVector, 0, this.positionList, 0, 3);
        System.arraycopy(this.stateVector, 3, this.speedList, 0, 3);
        System.arraycopy(this.stateVector, 6, this.accelerationList, 0, 3);
		
		this.stateVector = MathOperation.getstateVectorLastStepCalculator(this.positionList, this.speedList, this.accelerationList, deltaTime);
	}	
	
	
	public void getKFPredicted(double deltaTime) {
		KFPredicted(deltaTime);
	}
	
	
	
	public String toString() {
		return "x : "     + this.stateVector[0] +
				" ,y : "  + this.stateVector[1] +
				" ,z : "  + this.stateVector[2] +
				" ,Vx : " + this.stateVector[3] +
				" ,Vy : " + this.stateVector[4] +
				" ,Vz : " + this.stateVector[5] +
				" ,Ax : " + this.stateVector[6] +
				" ,Ay : " + this.stateVector[7] +
				" ,Az : " + this.stateVector[8] ;
	}

}
