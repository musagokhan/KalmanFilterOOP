package ekf;

import java.util.Arrays;
import java.util.List;

import ekf.utils.EKFConstant;
import ekf.utils.EKFMathOperation;

public class EKF {
	
	private double deltaT;	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	
	private double[][] Amatrix;
	private double[][] A_trmatrix;
	
	private double[][] inovationMatrix;
	
	private double[][] Hmatrix;
	private double[][] Hmatrix_tr;
	
	private double[][] Smatrix;
	private double[][] Kmatrix;
	
	private final int operationalDimension;
	private String AorA_tr;
		
		
	public EKF(List<double[][]> XandPmatrices, double deltaT) {
		this.deltaT = deltaT;
		this.stateVector = XandPmatrices.get(0);
		this.covarianceMatrix = XandPmatrices.get(1);
		this.operationalDimension = this.stateVector.length / EKFConstant.diffParametersNumberInStateVector;
		//constructerlLogs();        
	}
	
	public EKF(List<double[][]> XandPmatrices) {
		this.stateVector = XandPmatrices.get(0);
		this.covarianceMatrix = XandPmatrices.get(1);
		this.operationalDimension = this.stateVector.length / EKFConstant.diffParametersNumberInStateVector;
		//constructerlLogs();        
	}
	
	public EKF(double[][] stateVector, double[][] covarianceMatrix) {
		this.stateVector = stateVector;
		this.covarianceMatrix = covarianceMatrix;
		this.operationalDimension = this.stateVector.length / EKFConstant.diffParametersNumberInStateVector;
		//constructerlLogs();        
	}
	
	public double[][] getStateVector(){return this.stateVector;}
	
	public double[][] getCovarianceMatrix(){return this.covarianceMatrix;}
	
	private void EKFPredictedForStateVector(double deltaTime) {		
//        System.out.println("Before KF_Pre stateVector    : " +  Arrays.deepToString(this.stateVector));
        this.stateVector = EKFMathOperation.getmultiplyMatrices(this.Amatrix, this.stateVector);
//        System.out.println("After KF_Pre  stateVector    : " +  Arrays.deepToString(this.stateVector));	
	}	
	
	private void EKFPredictedForCovarianceMatrix(double deltaTime) {	
		// P "k+1|k"  = A"k" P"k" A^t"k" + Q"k"
//		System.out.println("Before KF_Pre covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));
		double[][] tempCM = EKFMathOperation.getmultiplyMatrices(this.Amatrix, this.covarianceMatrix);	
		tempCM = EKFMathOperation.getmultiplyMatrices(tempCM, this.A_trmatrix);
		this.covarianceMatrix = EKFMathOperation.getAddMatrices(tempCM, EKFConstant.getQMatrixForPrediction(this.operationalDimension));		
//		System.out.println("After KF_Pre  covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));	
	}
		
	
	private double[][] inovationCalculate(double[][] measurement ) {
		double[][]  predictedMeasurement =  EKFMathOperation.getmultiplyMatrices(this.Hmatrix, this.stateVector); // H*x	
		this.inovationMatrix = EKFMathOperation.getSubtractionMatrices(measurement, predictedMeasurement);  // z - z'
//		System.out.println("LOG KFUP : measurement " + measurement.length + "x" + measurement[0].length  + " : " + Arrays.deepToString(measurement));
//		System.out.println("LOG KFUP : predictedMeasurement " + predictedMeasurement.length + "x" + predictedMeasurement[0].length  + " :"  + Arrays.deepToString(predictedMeasurement));
//		System.out.println("LOG KFUP :  inovation n " + this.inovationMatrix.length + "x" + this.inovationMatrix[0].length  + ": " + Arrays.deepToString(this.inovationMatrix));
		return null;
	}

	private void SmatrixCalculate () {
//		System.out.println("");
//		System.out.println(" - SmatrixCalculate - ");
//		System.out.println("this.Hmatrix		  : " + Arrays.deepToString(this.Hmatrix));
//		System.out.println("this.covarianceMatrix : " + Arrays.deepToString(this.covarianceMatrix));
//		System.out.println("this.Hmatrix_tr       : " + Arrays.deepToString(this.Hmatrix_tr));
//		System.out.println("this.getRmatrix       : " + Arrays.deepToString(KFConstant.getRmatrix(this.operationalDimension)));
		
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.Hmatrix, this.covarianceMatrix);
		this.Smatrix = EKFMathOperation.getmultiplyMatrices(temp, this.Hmatrix_tr) ;
		this.Smatrix  = EKFMathOperation.getAddMatrices(this.Smatrix , EKFConstant.getRmatrix(this.operationalDimension)); // new double[][] {{1.1}};//
//		System.out.println("!!!! S : "+ Arrays.deepToString(this.Smatrix));
	}
	
	private void KmatrixCalculate () {	
//		System.out.println("");
//		System.out.println(" - KFupdateStateVectorCalculator - ");
//		System.out.println("this.covarianceMatrix : " + Arrays.deepToString(this.covarianceMatrix));
//		System.out.println("this.Hmatrix_tr       : " + Arrays.deepToString(this.Hmatrix_tr));
////		System.out.println(" P H (temp)			       : " + Arrays.deepToString(temp));
//		System.out.println("S_TR			       : " + Arrays.deepToString(MathOperation.invert(this.Smatrix)));

		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.covarianceMatrix, this.Hmatrix_tr);
		this.Kmatrix = EKFMathOperation.getmultiplyMatrices(temp, EKFMathOperation.invert(this.Smatrix));
//		System.out.println("!!!! K : "+ Arrays.deepToString(this.Kmatrix));
//		MathOperation.maxtixLengthInfo(this.Kmatrix);
	}
	
	private void EKFupdateStateVectorCalculator() {	
		
//		System.out.println("");
//		System.out.println(" - KFupdateStateVectorCalculator - ");
//		System.out.println("KAZANC			 : " + Arrays.deepToString(this.Kmatrix));
//		System.out.println("inovationMatrix	 : " + Arrays.deepToString(this.inovationMatrix));
		
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.Kmatrix, this.inovationMatrix);
//		System.out.println("K inv (temp)	 : " + Arrays.deepToString(temp));
//		System.out.println("stateVector		 : " + Arrays.deepToString(this.stateVector));
		
		
		this.stateVector = EKFMathOperation.getAddMatrices(this.stateVector, temp);
//		System.out.println("!!!! KF UPT .stateVector : "+ Arrays.deepToString(this.stateVector));
//		MathOperation.maxtixLengthInfo(this.stateVector);
	}
	
	private void EKFupdateCovarianceMatrixCalculator() {
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.Kmatrix, this.Smatrix);
		temp = EKFMathOperation.getmultiplyMatrices(temp, EKFMathOperation.transposeCalculate(this.Kmatrix));
		this.covarianceMatrix = EKFMathOperation.getSubtractionMatrices(this.covarianceMatrix, temp);		
//		System.out.println("!!!! covarianceMatrix : "+ Arrays.deepToString(this.covarianceMatrix));
//		MathOperation.maxtixLengthInfo(this.covarianceMatrix);
	}
	
	private void EKFUpdate(double[][] measurement){
		inovationCalculate(measurement); 
		SmatrixCalculate();
		KmatrixCalculate();
		EKFupdateStateVectorCalculator();
		EKFupdateCovarianceMatrixCalculator();	
	}
	
	public void getEKFPredicted(double deltaTime) {
		this.Amatrix = EKFConstant.getMatrixA(true, this.operationalDimension, deltaTime);
		this.A_trmatrix = EKFConstant.getMatrixA(false, this.operationalDimension, deltaTime);	
		EKFPredictedForStateVector(deltaTime);      // for StateVector Predicted
		EKFPredictedForCovarianceMatrix(deltaTime); // for CovarianceMatrix Predicted
	}
	
	public void getEKFUpdate(double[][] measurement){		
		this.Hmatrix = EKFConstant.getHmatrix(measurement , true, this.operationalDimension);
		this.Hmatrix_tr = EKFConstant.getHmatrix(measurement , false, this.operationalDimension);
		EKFUpdate(measurement);
	}
	
	public void EKFDoit(double deltaTime, double[][] measurement){
		getEKFPredicted(deltaTime);
		getEKFUpdate(measurement);
	}
	
  	public String toString() {
		return  " X :" + Arrays.deepToString(this.stateVector)+
				" /-/" +
				" P : " + Arrays.deepToString(this.covarianceMatrix); 
	}
	
	private void constructerlLogs() {
		System.out.println("constructerlLogs");
		System.out.println("KFConstant.dimension    : " + (EKFConstant.diffParametersNumberInStateVector));
		System.out.println("this.stateVector        : " + Arrays.toString(this.stateVector));
		System.out.println("this.stateVector.length : " + (this.stateVector.length));
	    System.out.println("KFConstant.dimension    : " + (EKFConstant.diffParametersNumberInStateVector));
        System.out.println("operationalDimension    : " + (this.operationalDimension));
        System.out.println("operationalDimension x2 : " + (this.operationalDimension*2));
        System.out.println("operationalDimension x3 : " + (this.operationalDimension*3));
	}
	

}
