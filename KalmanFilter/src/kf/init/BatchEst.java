package kf.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kf.model.Iinit;
import kf.utils.KFConstant;
import kf.utils.MathOperation;

public class BatchEst implements  Iinit{
	
	private int dimension;
	private double[][] H_matrix;
	private double[][] H_tr_matrix;
	private double[][] R_matrix;
	private double[][] R_inv_matrix;
	private double[][] measurement;
	private double[][] initstateVectoreDatas; 
	private double[][] initCovarianceDatas;
	List<double[][]> XandPmatrices = new ArrayList<>();
	
	
	private void stateVectorEstimate() {
		double[][] temp = MathOperation.getmultiplyMatrices(this.initCovarianceDatas, this.H_tr_matrix);
		temp = MathOperation.getmultiplyMatrices(temp, this.R_inv_matrix);
		this.initstateVectoreDatas = MathOperation.getmultiplyMatrices(temp, this.measurement);
//		System.out.println("this.initstateVectoreDatas : " + Arrays.deepToString(this.initstateVectoreDatas));
	}
	
	private void covarianceMatrixEstimate() {
		
		double[][] temp = MathOperation.getmultiplyMatrices(this.H_tr_matrix, this.R_inv_matrix);
		temp = MathOperation.getmultiplyMatrices(temp,this.H_matrix);
//		this.initCovarianceDatas = MathOperation.invert(temp); if dont add small noise inverse operation is not possible.
		temp = CMaddNoiseForSecurty(temp);
		this.initCovarianceDatas = MathOperation.invert(temp);
//		System.out.println("this.initCovarianceDatas + Noise: " + Arrays.deepToString(this.initCovarianceDatas));
	}
	
	
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
	public List<double[][]> getMainKFInitation(double[][] currentMeasurement, double currentMeasurementTime) {
		this.dimension = currentMeasurement.length;	
		
		this.H_matrix = KFConstant.getHmatrix(true, this.dimension);
		this.H_tr_matrix = KFConstant.getHmatrix(false, this.dimension);
		this.R_matrix = KFConstant.getRmatrix(this.dimension);
		this.R_inv_matrix = MathOperation.invert(this.R_matrix);
		this.measurement = currentMeasurement;
		
//		System.out.println("///// LOG /////");
//		System.out.println("H_matrix 	: " + Arrays.deepToString(H_matrix));
//		System.out.println("H_tr_matrix : " + Arrays.deepToString(H_tr_matrix));
//		System.out.println("R_matrix 	: " + Arrays.deepToString(R_matrix));
//		System.out.println("R_invmatrix : " + Arrays.deepToString(R_inv_matrix));
//		System.out.println("measurement : " + Arrays.deepToString(measurement));
		
		covarianceMatrixEstimate();
		stateVectorEstimate();
		
		this.XandPmatrices.add(0, this.initstateVectoreDatas);
		this.XandPmatrices.add(1, this.initCovarianceDatas);
		
		return this.XandPmatrices;
	}

}
