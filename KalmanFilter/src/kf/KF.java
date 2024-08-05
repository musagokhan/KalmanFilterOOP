package kf;

import java.util.Arrays;
import kf.utils.KFConstant;
import kf.utils.KFMathOperation;

public class KF {
	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	private double[][] Amatrix;
	private double[][] A_trmatrix;	
	private double[][] inovationMatrix;
	private double[][] Hmatrix;
	private double[][] Hmatrix_tr;
	private double[][] Smatrix;
	private double[][] measurementCovariance;
	private double[][] Kmatrix;
	private final int operationalDimension;
	
	public KF(double[][] stateVector, double[][] covarianceMatrix) {
		this.stateVector = stateVector;
		this.covarianceMatrix = covarianceMatrix;
		this.operationalDimension = this.stateVector.length / KFConstant.diffParametersNumberInStateVector;
	}
	

	private double[][] inovationCalculate(double[][] measurement ) {
		double[][]  predictedMeasurement =  KFMathOperation.getmultiplyMatrices(this.Hmatrix, this.stateVector); // H*x	
		this.inovationMatrix = KFMathOperation.getSubtractionMatrices(measurement, predictedMeasurement);  // z - z'
//		System.out.println("LOG KFUP : measurement " + measurement.length + "x" + measurement[0].length  + " : " + Arrays.deepToString(measurement));
//		System.out.println("LOG KFUP : predictedMeasurement " + predictedMeasurement.length + "x" + predictedMeasurement[0].length  + " :"  + Arrays.deepToString(predictedMeasurement));
//		System.out.println("LOG KFUP :  inovation n " + this.inovationMatrix.length + "x" + this.inovationMatrix[0].length  + ": " + Arrays.deepToString(this.inovationMatrix));
		return null;
	}

	private void Rmatrix(double[][] measurementCovariance) {
		if (measurementCovariance != null) {
			this.measurementCovariance = measurementCovariance;
		} else {
			this.measurementCovariance = KFConstant.getRmatrix(this.operationalDimension);
		}
		
	}
	
	private void SmatrixCalculate () {
//		System.out.println("");
//		System.out.println(" - SmatrixCalculate - ");
//		System.out.println("this.Hmatrix		  : " + Arrays.deepToString(this.Hmatrix));
//		System.out.println("this.covarianceMatrix : " + Arrays.deepToString(this.covarianceMatrix));
//		System.out.println("this.Hmatrix_tr       : " + Arrays.deepToString(this.Hmatrix_tr));
//		System.out.println("this.getRmatrix       : " + Arrays.deepToString(KFConstant.getRmatrix(this.operationalDimension)));
		
		double[][] temp = KFMathOperation.getmultiplyMatrices(this.Hmatrix, this.covarianceMatrix);
		this.Smatrix = KFMathOperation.getmultiplyMatrices(temp, this.Hmatrix_tr) ;
		this.Smatrix  = KFMathOperation.getAddMatrices(this.Smatrix , KFConstant.getRmatrix(this.operationalDimension)); // KFConstant.getRmatrix(this.operationalDimension) -> default RMatrix
//		System.out.println("!!!! S : "+ Arrays.deepToString(this.Smatrix));
	}
	
	private void KmatrixCalculate () {	
//		System.out.println("");
//		System.out.println(" - KFupdateStateVectorCalculator - ");
//		System.out.println("this.covarianceMatrix : " + Arrays.deepToString(this.covarianceMatrix));
//		System.out.println("this.Hmatrix_tr       : " + Arrays.deepToString(this.Hmatrix_tr));
////		System.out.println(" P H (temp)			       : " + Arrays.deepToString(temp));
//		System.out.println("S_TR			       : " + Arrays.deepToString(MathOperation.invert(this.Smatrix)));

		double[][] temp = KFMathOperation.getmultiplyMatrices(this.covarianceMatrix, this.Hmatrix_tr);
		this.Kmatrix = KFMathOperation.getmultiplyMatrices(temp, KFMathOperation.invert(this.Smatrix));
//		System.out.println("!!!! K : "+ Arrays.deepToString(this.Kmatrix));
//		MathOperation.maxtixLengthInfo(this.Kmatrix);
	}
	
	
	
	private void KFPredictedForStateVector(double deltaTime) {		
//      System.out.println("Before KF_Pre stateVector    : " +  Arrays.deepToString(this.stateVector));
      this.stateVector = KFMathOperation.getmultiplyMatrices(this.Amatrix, this.stateVector);
//      System.out.println("After KF_Pre  stateVector    : " +  Arrays.deepToString(this.stateVector));	
	}	
	
	private void KFPredictedForCovarianceMatrix(double deltaTime) {	
		// P "k+1|k"  = A"k" P"k" A^t"k" + Q"k"
//		System.out.println("Before KF_Pre covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));
		double[][] tempCM = KFMathOperation.getmultiplyMatrices(this.Amatrix, this.covarianceMatrix);	
		tempCM = KFMathOperation.getmultiplyMatrices(tempCM, this.A_trmatrix);
		this.covarianceMatrix = KFMathOperation.getAddMatrices(tempCM, KFConstant.getQMatrixForPrediction(this.operationalDimension));		
//		System.out.println("After KF_Pre  covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));	
	}
	
	
	
	private void KFupdateStateVectorCalculator() {	
		
//		System.out.println("");
//		System.out.println(" - KFupdateStateVectorCalculator - ");
//		System.out.println("KAZANC			 : " + Arrays.deepToString(this.Kmatrix));
//		System.out.println("inovationMatrix	 : " + Arrays.deepToString(this.inovationMatrix));
		
		double[][] temp = KFMathOperation.getmultiplyMatrices(this.Kmatrix, this.inovationMatrix);
//		System.out.println("K inv (temp)	 : " + Arrays.deepToString(temp));
//		System.out.println("stateVector		 : " + Arrays.deepToString(this.stateVector));
		
		
		this.stateVector = KFMathOperation.getAddMatrices(this.stateVector, temp);
//		System.out.println("!!!! KF UPT .stateVector : "+ Arrays.deepToString(this.stateVector));
//		MathOperation.maxtixLengthInfo(this.stateVector);
	}
	
	private void KFupdateCovarianceMatrixCalculator() {
		double[][] temp = KFMathOperation.getmultiplyMatrices(this.Kmatrix, this.Smatrix);
		temp = KFMathOperation.getmultiplyMatrices(temp, KFMathOperation.transposeCalculate(this.Kmatrix));
		this.covarianceMatrix = KFMathOperation.getSubtractionMatrices(this.covarianceMatrix, temp);		
//		System.out.println("!!!! covarianceMatrix : "+ Arrays.deepToString(this.covarianceMatrix));
//		MathOperation.maxtixLengthInfo(this.covarianceMatrix);
	}
	
	
	
	public void KFPredicted(double deltaTime) {
		this.Amatrix = KFConstant.getMatrixA(true, this.operationalDimension, deltaTime);
		this.A_trmatrix = KFConstant.getMatrixA(false, this.operationalDimension, deltaTime);	
		KFPredictedForStateVector(deltaTime);      // for StateVector Predicted
		KFPredictedForCovarianceMatrix(deltaTime); // for CovarianceMatrix Predicted
	}

	public void KFUpdate(double[][] measurement, double[][] measurementCovariance){
		this.Hmatrix = KFConstant.getHmatrix(true, this.operationalDimension);
		this.Hmatrix_tr = KFConstant.getHmatrix(false, this.operationalDimension);
		inovationCalculate(measurement); 
		Rmatrix(measurementCovariance);
		SmatrixCalculate();
		KmatrixCalculate();
		KFupdateStateVectorCalculator();
		KFupdateCovarianceMatrixCalculator();	
	}
	
	public void KFDoit(double deltaTime, double[][] measurement, double[][] measurementCovariance){
		KFPredicted(deltaTime);
		KFUpdate(measurement, measurementCovariance );
	}
		
	
	
	public double[][] getStateVector(){return this.stateVector;}
	public double[][] getCovarianceMatrix(){return this.covarianceMatrix;}
	
}
