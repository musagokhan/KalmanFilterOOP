package kf;

import java.util.Arrays;
import java.util.List;

import kf.utils.KFConstant;
import kf.utils.KFMathOperation;

// !!! TODO : Interface 'e koy.
public class KF {
	
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
//        System.out.println("Before KF_Pre stateVector    : " +  Arrays.deepToString(this.stateVector));
        this.stateVector = KFMathOperation.getmultiplyMatrices(this.Amatrix, this.stateVector);
//        System.out.println("After KF_Pre  stateVector    : " +  Arrays.deepToString(this.stateVector));	
	}	
	
	private void KFPredictedForCovarianceMatrix(double deltaTime) {	
		// P "k+1|k"  = A"k" P"k" A^t"k" + Q"k"
//		System.out.println("Before KF_Pre covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));
		double[][] tempCM = KFMathOperation.getmultiplyMatrices(this.Amatrix, this.covarianceMatrix);	
		tempCM = KFMathOperation.getmultiplyMatrices(tempCM, this.A_trmatrix);
		this.covarianceMatrix = KFMathOperation.getAddMatrices(tempCM, KFConstant.getQMatrixForPrediction(this.operationalDimension));		
//		System.out.println("After KF_Pre  covarianceMatrix    : " +  Arrays.deepToString(this.covarianceMatrix));	
	}
		
	public void getKFPredicted(double deltaTime) {
		this.Amatrix = KFConstant.getMatrixA(true, this.operationalDimension, deltaTime);
		this.A_trmatrix = KFConstant.getMatrixA(false, this.operationalDimension, deltaTime);	
		KFPredictedForStateVector(deltaTime);      // for StateVector Predicted
		KFPredictedForCovarianceMatrix(deltaTime); // for CovarianceMatrix Predicted
	}
	
	private double[][] inovationCalculate(double[][] measurement ) {
		double[][]  predictedMeasurement =  KFMathOperation.getmultiplyMatrices(this.Hmatrix, this.stateVector); // H*x	
		this.inovationMatrix = KFMathOperation.getSubtractionMatrices(measurement, predictedMeasurement);  // z - z'
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
		
		double[][] temp = KFMathOperation.getmultiplyMatrices(this.Hmatrix, this.covarianceMatrix);
		this.Smatrix = KFMathOperation.getmultiplyMatrices(temp, this.Hmatrix_tr) ;
		this.Smatrix  = KFMathOperation.getAddMatrices(this.Smatrix , KFConstant.getRmatrix(this.operationalDimension)); // new double[][] {{1.1}};//
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
