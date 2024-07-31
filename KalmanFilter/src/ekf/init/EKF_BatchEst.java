package ekf.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ekf.model.IEKFinit;
import ekf.utils.EKFConstant;
import ekf.utils.EKFMathOperation;

public class EKF_BatchEst implements  IEKFinit {

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
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.initCovarianceDatas, this.H_tr_matrix);
		temp = EKFMathOperation.getmultiplyMatrices(temp, this.R_inv_matrix);
		this.initstateVectoreDatas = EKFMathOperation.getmultiplyMatrices(temp, this.measurement);
//		System.out.println("this.initstateVectoreDatas : " + Arrays.deepToString(this.initstateVectoreDatas));
	}
	
	private void covarianceMatrixEstimate() {
		
		double[][] temp = EKFMathOperation.getmultiplyMatrices(this.H_tr_matrix, this.R_inv_matrix);
		temp = EKFMathOperation.getmultiplyMatrices(temp,this.H_matrix);
//		this.initCovarianceDatas = MathOperation.invert(temp); if dont add small noise inverse operation is not possible.
		temp = CMaddNoiseForSecurty(temp);
		this.initCovarianceDatas = EKFMathOperation.invert(temp);
//		System.out.println("this.initCovarianceDatas + Noise: " + Arrays.deepToString(this.initCovarianceDatas));
	}
	
	private double[][]  CMaddNoiseForSecurty (double[][] matrix) {
		double length = this.dimension * EKFConstant.diffParametersNumberInStateVector;
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
		
		this.H_matrix = EKFConstant.getHmatrix(currentMeasurement, true, this.dimension);
		this.H_tr_matrix = EKFConstant.getHmatrix(currentMeasurement, false, this.dimension);
		this.R_matrix = EKFConstant.getRmatrix(this.dimension);
		this.R_inv_matrix = EKFMathOperation.invert(this.R_matrix);
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
