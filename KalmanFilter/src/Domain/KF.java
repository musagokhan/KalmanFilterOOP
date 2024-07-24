package Domain;

import java.util.Arrays;

import kf.utils.KFConstant;
import kf.utils.MathOperation;


public class KF {
		
	private double[] positionArray;
	private double[] speedArray;
	private double[] accelerationArray;
	
	private double[] stateVector;
	
	private final int operationalDimension;

		
	public KF(double[] stateVector) {
		
		this.stateVector = stateVector;
		this.operationalDimension = this.stateVector.length / KFConstant.diffParametersNumberInStateVector;
		this.positionArray = new double[this.operationalDimension];
		this.speedArray = new double[this.operationalDimension];
		this.accelerationArray = new double[this.operationalDimension];
		
		constructerlLogs();        
		
	}
	
	
	private void KFPredicted(double deltaTime) {		

		System.arraycopy(this.stateVector, this.operationalDimension*0 , this.positionArray     , 0, this.operationalDimension);
        System.arraycopy(this.stateVector, this.operationalDimension*1 , this.speedArray        , 0, this.operationalDimension);
        System.arraycopy(this.stateVector, this.operationalDimension*2 , this.accelerationArray , 0, this.operationalDimension);
        
        KFPredictedLogs();
        	
		//this.stateVector = MathOperation.getstateVectorLastStepCalculator(this.positionList, this.speedList, this.accelerationList, deltaTime);
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
		System.out.println("KFConstant.dimension    : " + (KFConstant.diffParametersNumberInStateVector));
		System.out.println("this.stateVector        : " + Arrays.toString(this.stateVector));
		System.out.println("this.stateVector.length : " + (this.stateVector.length));
	    System.out.println("KFConstant.dimension    : " + (KFConstant.diffParametersNumberInStateVector));
        System.out.println("operationalDimension    : " + (this.operationalDimension));
        System.out.println("operationalDimension x2 : " + (this.operationalDimension*2));
        System.out.println("operationalDimension x3 : " + (this.operationalDimension*3));
	}
	
	private void KFPredictedLogs() {
        System.out.println("0ALLLL : " + Arrays.toString(this.stateVector));
        System.out.println("0/ 0-3 : " + Arrays.toString(this.positionArray));
        System.out.println("3/ 0-3 : " + Arrays.toString(this.speedArray));
        System.out.println("6/ 0-3 : " + Arrays.toString(this.accelerationArray));
	}

}
