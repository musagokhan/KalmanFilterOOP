package ekf;

import java.util.Arrays;

import ekf.utils.EKFConstant;
import ekf.utils.EKFMathOperation;
import kf.utils.KFConstant;

public class EKF {
	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	private double[][] Amatrix;
	private double[][] A_trmatrix;	
	private double[][] inovationMatrix;	
	private double[][] Hmatrix;
	private double[][] Hmatrix_tr;
	private double[][] measurementCovariance;
	private double[][] Smatrix;
	private double[][] Kmatrix;
	
	private final int operationalDimension;
			
	public EKF(double[][] stateVector, double[][] covarianceMatrix) {
		this.stateVector = stateVector;
		this.covarianceMatrix = covarianceMatrix;
		this.operationalDimension = this.stateVector.length / EKFConstant.diffParametersNumberInStateVector;        
	}
	
	public double[][] getStateVector(){return this.stateVector;}
	
	public double[][] getCovarianceMatrix(){return this.covarianceMatrix;}
	
	private void EKFPredictedForStateVector(double deltaTime) {		
        System.out.println("EKF.java : Before EKF_Pre stateVector    : " +  Arrays.deepToString(this.stateVector));
        this.stateVector = EKFMathOperation.getmultiplyMatrices(this.Amatrix, this.stateVector);
        System.out.println("EKF.java : After  EKF_Pre  stateVector    : " +  Arrays.deepToString(this.stateVector));	
	}	
	
	private void EKFPredictedForCovarianceMatrix(double deltaTime) {	
		// P "k+1|k"  = A"k" P"k" A^t"k" + Q"k"
		System.out.println("EKF.java : Before KF_Pre covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));
		double[][] tempCM = EKFMathOperation.getmultiplyMatrices(this.Amatrix, this.covarianceMatrix);	
		tempCM = EKFMathOperation.getmultiplyMatrices(tempCM, this.A_trmatrix);
		this.covarianceMatrix = EKFMathOperation.getAddMatrices(tempCM, EKFConstant.getQMatrixForPrediction(this.operationalDimension));		
		System.out.println("EKF.java : After KF_Pre  covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));	
	}
		
	
	private double[][] inovationCalculate(double[][] measurement ) {
		double[][]  predictedMeasurement =  EKFMathOperation.getmultiplyMatrices(this.Hmatrix, this.stateVector); // H*x	
		this.inovationMatrix = EKFMathOperation.getSubtractionMatrices(measurement, predictedMeasurement);  // z - z'
		System.out.println("EKF.java : LOG KFUP : measurement " + measurement.length + "x" + measurement[0].length  + " : " + Arrays.deepToString(measurement));
		System.out.println("EKF.java : LOG KFUP : predictedMeasurement " + predictedMeasurement.length + "x" + predictedMeasurement[0].length  + " :"  + Arrays.deepToString(predictedMeasurement));
		System.out.println("EKF.java : LOG KFUP :  inovation n " + this.inovationMatrix.length + "x" + this.inovationMatrix[0].length  + ": " + Arrays.deepToString(this.inovationMatrix));
		return null;
	}

	private void Rmatrix(double[][] measurementCovariance) {
		if (measurementCovariance != null) {
			this.measurementCovariance = measurementCovariance;
		} else {
			this.measurementCovariance = KFConstant.getRmatrix(this.operationalDimension);
		}
		System.out.println("EKF.java : Rmatrix  (measurementCovariance)  : " +  Arrays.deepToString(this.measurementCovariance));
	}
	
	private void SmatrixCalculate () {
		System.out.println("");
		System.out.println(" - SmatrixCalculate - ");
		System.out.println("EKF.java : this.Hmatrix		  : " + Arrays.deepToString(this.Hmatrix));
		System.out.println("EKF.java : this.covarianceMatrix : " + Arrays.deepToString(this.covarianceMatrix));
		System.out.println("EKF.java : this.Hmatrix_tr       : " + Arrays.deepToString(this.Hmatrix_tr));
		System.out.println("EKF.java : measCov       : " + Arrays.deepToString(this.measurementCovariance));
		
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.Hmatrix, this.covarianceMatrix);
		this.Smatrix = EKFMathOperation.getmultiplyMatrices(temp, this.Hmatrix_tr) ;
		this.Smatrix  = EKFMathOperation.getAddMatrices(this.Smatrix , this.measurementCovariance); // new double[][] {{1.1}};//
		System.out.println("!!!! S : "+ Arrays.deepToString(this.Smatrix));
	}
	
	private void KmatrixCalculate () {	
		System.out.println("");
		System.out.println(" - KFupdateStateVectorCalculator - ");
		System.out.println("EKF.java : this.covarianceMatrix : " + Arrays.deepToString(this.covarianceMatrix));
		System.out.println("EKF.java : this.Hmatrix_tr       : " + Arrays.deepToString(this.Hmatrix_tr));
//		System.out.println(" P H (temp)			       : " + Arrays.deepToString(temp));
		System.out.println("EKF.java : S_TR			       : " + Arrays.deepToString(EKFMathOperation.invert(this.Smatrix)));

		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.covarianceMatrix, this.Hmatrix_tr);
		this.Kmatrix = EKFMathOperation.getmultiplyMatrices(temp, EKFMathOperation.invert(this.Smatrix));
		System.out.println("EKF.java : !!!! K : "+ Arrays.deepToString(this.Kmatrix));
	}
	
	private void EKFupdateStateVectorCalculator() {	
		
		System.out.println("");
		System.out.println(" - KFupdateStateVectorCalculator - ");
		System.out.println("EKF.java : KAZANC			 : " + Arrays.deepToString(this.Kmatrix));
		System.out.println("EKF.java : inovationMatrix	 : " + Arrays.deepToString(this.inovationMatrix));
		
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.Kmatrix, this.inovationMatrix);
		System.out.println("EKF.java : K inv (temp)	 : " + Arrays.deepToString(temp));
		System.out.println("EKF.java : stateVector		 : " + Arrays.deepToString(this.stateVector));
		
		
		this.stateVector = EKFMathOperation.getAddMatrices(this.stateVector, temp);
		System.out.println("EKF.java : !!!! KF UPT .stateVector : "+ Arrays.deepToString(this.stateVector));
	}
	
	private void EKFupdateCovarianceMatrixCalculator() {
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.Kmatrix, this.Smatrix);
		temp = EKFMathOperation.getmultiplyMatrices(temp, EKFMathOperation.transposeCalculate(this.Kmatrix));
		this.covarianceMatrix = EKFMathOperation.getSubtractionMatrices(this.covarianceMatrix, temp);		
		System.out.println("EKF.java : !!!! covarianceMatrix : "+ Arrays.deepToString(this.covarianceMatrix));
	}
	
	public void EKFUpdate(double[][] measurement,  double[][] measurementCovariance){
		System.out.println("EKF.java : EKFUpdate start ");
		this.Hmatrix = EKFConstant.getHmatrix(measurement , true, this.operationalDimension);
		this.Hmatrix_tr = EKFConstant.getHmatrix(measurement , false, this.operationalDimension);
		inovationCalculate(measurement);
		Rmatrix(measurementCovariance);
		SmatrixCalculate();
		KmatrixCalculate();
		EKFupdateStateVectorCalculator();
		EKFupdateCovarianceMatrixCalculator();
		System.out.println("EKF.java : EKFUpdate stop ");
	}
	
	public void EKFPredicted(double deltaTime) {
		this.Amatrix = EKFConstant.getMatrixA(true, this.operationalDimension, deltaTime);
		this.A_trmatrix = EKFConstant.getMatrixA(false, this.operationalDimension, deltaTime);	
		System.out.println("EKF.java : EKFPredicted start ");
		EKFPredictedForStateVector(deltaTime);      // for StateVector Predicted
		EKFPredictedForCovarianceMatrix(deltaTime); // for CovarianceMatrix Predicted
		System.out.println("EKF.java : EKFPredicted stop ");
	}
		
	public void EKFDoit(double deltaTime, double[][] measurement, double[][] measurementCovariance){
		EKFPredicted(deltaTime);
		EKFUpdate(measurement, measurementCovariance);
	}
	
}
