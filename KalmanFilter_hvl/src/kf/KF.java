package kf;

import java.util.Arrays;
import java.util.List;

import kf.utils.KFConstant;
import kf.utils.MathOperation;

// !!! TODO : Interface 'e koy.
public class KF {
	
	private double deltaT;	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	
	private double[][] inovationMatrix;
	
	private double[][] Hmatrix;
	private double[][] Hmatrix_tr;
	
	private double[][] Smatrix;
	private double[][] Kmatrix;
	
	private final int operationalDimension;
	private String AorA_tr;
		
		
	public KF(List<double[][]> XandPmatrices, double deltaT) {
		this.deltaT = deltaT;
		this.stateVector = XandPmatrices.get(0);
		this.covarianceMatrix = XandPmatrices.get(1);
		this.operationalDimension = this.stateVector.length / KFConstant.diffParametersNumberInStateVector;
		//constructerlLogs();        
	}
	
	public double[][] getStateVector(){return this.stateVector;}
	public double[][] getCovarianceMatrix(){return this.covarianceMatrix;}
	
	private void KFPredictedForStateVector(double deltaTime) {		
		AorA_tr = "A";
//        System.out.println("Before KF_Pre stateVector    : " +  Arrays.deepToString(this.stateVector));
        this.stateVector = MathOperation.getMultiplexWithAmatrixForKFPrediction(AorA_tr, this.stateVector, this.operationalDimension, deltaT);
//        System.out.println("After KF_Pre  stateVector    : " +  Arrays.deepToString(this.stateVector));	
	}	
	
	private void KFPredictedForCovarianceMatrix(double deltaTime) {	
		// P "k+1|k"  = A"k" P"k" A^t"k" + Q"k"
//		System.out.println("Before KF_Pre covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));
		AorA_tr = "A";
		double[][] tempCM = MathOperation.getMultiplexWithAmatrixForKFPrediction(AorA_tr, this.covarianceMatrix, this.operationalDimension, deltaT);																						
		AorA_tr = "A_tr";
		tempCM = MathOperation.getMultiplexWithAmatrixForKFPrediction(AorA_tr, tempCM,this.operationalDimension, deltaT);
		this.covarianceMatrix = MathOperation.getAddMatrices(tempCM, KFConstant.getQMatrixForPrediction(this.operationalDimension));		
//		System.out.println("After KF_Pre  covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));	
	}
		
	public void getKFPredicted(double deltaTime) {
		KFPredictedForStateVector(deltaTime);      // for StateVector Predicted
		KFPredictedForCovarianceMatrix(deltaTime); // for CovarianceMatrix Predicted
	}
	
	private double[][] inovationCalculate(double[][] measurement ) {
		double[][]  predictedMeasurement =  MathOperation.getmultiplyMatrices(this.Hmatrix, this.stateVector); // H*x	
		this.inovationMatrix = MathOperation.getSubtractionMatrices(measurement, predictedMeasurement);  // z - z'
//		System.out.println("LOG KFUP : measurement " + measurement.length + "x" + measurement[0].length  + " : " + Arrays.deepToString(measurement));
//		System.out.println("LOG KFUP : predictedMeasurement " + predictedMeasurement.length + "x" + predictedMeasurement[0].length  + " :"  + Arrays.deepToString(predictedMeasurement));
//		System.out.println("LOG KFUP :  inovation n " + this.inovationMatrix.length + "x" + this.inovationMatrix[0].length  + ": " + Arrays.deepToString(this.inovationMatrix));
		return null;
	}

	private void SmatrixCalculate () {
		double[][] temp = MathOperation.getmultiplyMatrices(this.Hmatrix, this.covarianceMatrix);
		this.Smatrix = MathOperation.getmultiplyMatrices(temp, this.Hmatrix_tr) ;
		this.Smatrix  = MathOperation.getAddMatrices(this.Smatrix , KFConstant.getRmatrix(this.operationalDimension));
//		System.out.println("!!!! S : "+ Arrays.deepToString(this.Smatrix));
	}
	
	private void KmatrixCalculate () {
		double[][] temp = MathOperation.getmultiplyMatrices(this.covarianceMatrix, this.Hmatrix_tr);
		this.Kmatrix = MathOperation.getmultiplyMatrices(temp, MathOperation.invert(this.Smatrix));
//		System.out.println("!!!! K : "+ Arrays.deepToString(this.Kmatrix));
//		MathOperation.maxtixLengthInfo(this.Kmatrix);
	}
	
	private void KFupdateStateVectorCalculator() {
		double[][] temp = MathOperation.getmultiplyMatrices(this.Kmatrix, this.inovationMatrix);
		this.stateVector = MathOperation.getAddMatrices(this.stateVector, temp);
//		System.out.println("!!!! stateVector : "+ Arrays.deepToString(this.stateVector));
//		MathOperation.maxtixLengthInfo(this.stateVector);
	}
	
	private void KFupdateCovarianceMatrixCalculator() {
		double[][] temp = MathOperation.getmultiplyMatrices(this.Kmatrix, this.Smatrix);
		temp = MathOperation.getmultiplyMatrices(temp, MathOperation.transposeCalculate(this.Kmatrix));
		this.covarianceMatrix = MathOperation.getSubtractionMatrices(this.covarianceMatrix, temp);		
//		System.out.println("!!!! covarianceMatrix : "+ Arrays.deepToString(this.covarianceMatrix));
//		MathOperation.maxtixLengthInfo(this.covarianceMatrix);
	}
	
	private void KFUpdate(double[][] measurement){
		inovationCalculate(measurement); 
		SmatrixCalculate();
		KmatrixCalculate();
		KFupdateStateVectorCalculator();
		KFupdateCovarianceMatrixCalculator();	
	}
	
	public void getKFUpdate(double[][] measurement){
		this.Hmatrix = KFConstant.getHmatrix(true, this.operationalDimension);
		this.Hmatrix_tr = KFConstant.getHmatrix(false, this.operationalDimension);
		KFUpdate(measurement);
	}
	
	
	
 	public String toString() {
		return  " X :" + Arrays.deepToString(this.stateVector)+
				" /-/" +
				" P : " + Arrays.deepToString(this.covarianceMatrix); 
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
