package kf;

import java.util.Arrays;
import java.util.List;

import kf.utils.KFConstant;
import kf.utils.MathOperation;


public class KF {
	
	private double deltaT;	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	private final int operationalDimension;
	private String AorA_tr;
		
		
	public KF(List<double[][]> XandPmatrices, double deltaT) {
		this.deltaT = deltaT;
		this.stateVector = XandPmatrices.get(0);
		this.covarianceMatrix = XandPmatrices.get(1);
		this.operationalDimension = this.stateVector[0].length / KFConstant.diffParametersNumberInStateVector;
		//constructerlLogs();        
	}
	
		
	
	private void KFPredictedForStateVector(double deltaTime) {		
		AorA_tr = "A";
//        System.out.println("Before KF_Pre stateVector    : " +  Arrays.deepToString(this.stateVector));
        this.stateVector = MathOperation.getMultiplexWithAmatrixForKFPrediction(AorA_tr, this.stateVector, this.operationalDimension, deltaT);
//        System.out.println("After KF_Pre  stateVector    : " +  Arrays.deepToString(this.stateVector));	
	}	
	



	private void KFPredictedForCovarianceMatrix(double deltaTime) {	
		System.out.println("");
		System.out.println("");
		System.out.println("Before KF_Pre covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));
		
		AorA_tr = "A";
		double[][] tempCM = MathOperation.getMultiplexWithAmatrixForKFPrediction(AorA_tr, this.covarianceMatrix, this.operationalDimension, deltaT);
																						
		
																					// TODO: + Qk+1|k islemi eklenecek:
		AorA_tr = "A_tr";
		this.covarianceMatrix = MathOperation.getMultiplexWithAmatrixForKFPrediction(AorA_tr, tempCM,this.operationalDimension, deltaT);// + KFConstant.getQMatrixForPrediction(this.operationalDimension);
		
		System.out.println("After KF_Pre  covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));	
		System.out.println("");
		System.out.println("");
	}
	
	
	
	
	public void getKFPredicted(double deltaTime) {
		KFPredictedForStateVector(deltaTime);
		KFPredictedForCovarianceMatrix(deltaTime);
	}
	
	
	private void KFUpdate(){
		
	}
	
	public void getKFUpdate(){
		KFUpdate();
	}
	
	
	
 	public String toString() {
		return "x : "     + this.stateVector[0][0] + 
				" ,y : "  + this.stateVector[0][1] + 
				" ,z : "  + this.stateVector[0][2] + 
				" ,Vx : " + this.stateVector[0][3] + 
				" ,Vy : " + this.stateVector[0][4] + 
				" ,Vz : " + this.stateVector[0][5] + 
				" ,Ax : " + this.stateVector[0][6] + 
				" ,Ay : " + this.stateVector[0][7] + 
				" ,Az : " + this.stateVector[0][8] ;
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
