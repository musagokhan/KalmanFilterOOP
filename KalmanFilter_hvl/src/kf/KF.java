package kf;

import java.util.Arrays;

import kf.utils.KFConstant;
import kf.utils.MathOperation;


public class KF {
	
	private double deltaT;
	private double[] positionArray;
	private double[] speedArray;
	private double[] accelerationArray;
	
	private double[][] stateVector;
	
	private final int operationalDimension;

		
	public KF(double[][] stateVector, double deltaT) {
		this.deltaT = deltaT;
		this.stateVector = stateVector;
		this.operationalDimension = this.stateVector[0].length / KFConstant.diffParametersNumberInStateVector;
		this.positionArray = new double[this.operationalDimension];
		this.speedArray = new double[this.operationalDimension];
		this.accelerationArray = new double[this.operationalDimension];
				
		//constructerlLogs();        
	}
	
	
	private void KFPredicted(double deltaTime) {		
       
        System.out.println("O stateVector    : " +  Arrays.deepToString(this.stateVector));
        this.stateVector = MathOperation.getStateVectorCalculateForKFPrediction(this.stateVector, this.operationalDimension, deltaT);
        System.out.println("S stateVector    : " +  Arrays.deepToString(this.stateVector));	
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
	
	
	private void constructerlLogs() {
		System.out.println("constructerlLogs");
		System.out.println("KFConstant.dimension    : " + (KFConstant.diffParametersNumberInStateVector));
		System.out.println("this.stateVector        : " + Arrays.toString(this.stateVector));
		System.out.println("this.stateVector.length : " + (this.stateVector.length));
	    System.out.println("KFConstant.dimension    : " + (KFConstant.diffParametersNumberInStateVector));
        System.out.println("operationalDimension    : " + (this.operationalDimension));
        System.out.println("operationalDimension x2 : " + (this.operationalDimension*2));
        System.out.println("operationalDimension x3 : " + (this.operationalDimension*3));
	}
	
}
