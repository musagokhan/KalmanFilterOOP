package kf.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kf.model.ABSKFInit;
import kf.model.IKFinit;
import kf.utils.KFConstant;
import kf.utils.KFMathOperation;

//public class KF_BatchEst implements  IKFinit{
public class KF_BatchEst extends ABSKFInit{
	
	
	private int dimension;
	private double[][] H_matrix;
	private double[][] H_tr_matrix;
	private double[][] R_matrix;
	private double[][] R_inv_matrix;
	private double[][] measurement;
	private double[][] initstateVectoreDatas; 
	private double[][] initCovarianceDatas;
	List<double[][]> XandPmatrices = new ArrayList<>();
	
	

	private double[][]  CMaddNoiseForSecurty (double[][] matrix) {
		double length = this.dimension * KFConstant.diffParametersNumberInStateVector;
		for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
            	Random random = new Random();
            	matrix[i][j] += random.nextDouble();;
            }
        }
		return matrix;
	}
	
	
	@Override
	protected void stateVectorEstimate() {
		double[][] temp = KFMathOperation.getmultiplyMatrices(this.initCovarianceDatas, this.H_tr_matrix);
		temp = KFMathOperation.getmultiplyMatrices(temp, this.R_inv_matrix);
		this.initstateVectoreDatas = KFMathOperation.getmultiplyMatrices(temp, this.measurement);
//		System.out.println("this.initstateVectoreDatas : " + Arrays.deepToString(this.initstateVectoreDatas));
	}
	
	@Override
	protected void covarianceMatrixEstimate() {
		
		double[][] temp = KFMathOperation.getmultiplyMatrices(this.H_tr_matrix, this.R_inv_matrix);
		temp = KFMathOperation.getmultiplyMatrices(temp,this.H_matrix);
//		this.initCovarianceDatas = MathOperation.invert(temp); if dont add small noise inverse operation is not possible.
		temp = CMaddNoiseForSecurty(temp);
		this.initCovarianceDatas = KFMathOperation.invert(temp);
//		System.out.println("this.initCovarianceDatas + Noise: " + Arrays.deepToString(this.initCovarianceDatas));
	}
		
	@Override
	protected boolean mainKFInitation(double[][] currentMeasurement, double currentMeasurementTime) {
		this.dimension = currentMeasurement.length;		
		this.H_matrix = KFConstant.getHmatrix(true, this.dimension);
		this.H_tr_matrix = KFConstant.getHmatrix(false, this.dimension);
		this.R_matrix = KFConstant.getRmatrix(this.dimension);
		this.R_inv_matrix = KFMathOperation.invert(this.R_matrix);
		this.measurement = currentMeasurement;

		covarianceMatrixEstimate();
		stateVectorEstimate();
		
		return true; // everytime X and P calculae possible so TRUE
	}
	
	
	
	public double[][] getStateVector() {return this.initstateVectoreDatas;};
	
	public double[][] getCovarianceMatrix() {return  this.initCovarianceDatas;}
	

}
