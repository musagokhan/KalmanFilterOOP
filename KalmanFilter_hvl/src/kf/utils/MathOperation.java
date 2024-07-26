package kf.utils;
import java.util.Arrays;

import kf.utils.*;
public class MathOperation {
	
	
	private MathOperation() {}
	
	private static double[][] multiplexWithAmatrixForKFPrediction (String Atype, double[][] inputStateVector, int dimension, double deltaT) {	
//		System.out.println("1. matrix : " +  Arrays.deepToString(inputStateVector ));
//		System.out.println("2. matrix : " +  Arrays.deepToString(KFConstant.getMatrixA(dimension, deltaT)) );
		return multiplyMatrices(KFConstant.getMatrixA(Atype, dimension, deltaT) , inputStateVector );  // sirasi onemli.
	}
	
	
	public static double[][] getMultiplexWithAmatrixForKFPrediction (String Atype, double[][] inputStateVector, int dimension, double deltaT ) {
		return multiplexWithAmatrixForKFPrediction(Atype, inputStateVector, dimension, deltaT);
	}
	

	// !!! TODO : !!! : fonksiyonel prgramlama yap biFnc
	private static double helperForAddAndSub(boolean doAdd,  double m1, double m2) {
		double result;
		if (doAdd == true) { 
			return result = m1 + m2;
		} else {
			return result = m1 - m2;
		}
    }
	
	
    private static double[][] addMatrices(double[][] m1, double[][] m2) {
        int rows = m1.length;
        int cols = m1[0].length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = helperForAddAndSub (true, m1[i][j], m2[i][j]);
            }
        }
        return result;
    }
    
    private static double[][] subtractionMatrices(double[][] m1, double[][] m2) {
        int rows = m1.length;
        int cols = m1[0].length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = helperForAddAndSub (false, m1[i][j], m2[i][j]);
            }
        }
        return result;
    }
    
    public static double[][] getAddMatrices(double[][] m1, double[][] m2) {	return addMatrices(m1, m2); }
    public static double[][] getSubtractionMatrices(double[][] m1, double[][] m2) {	return subtractionMatrices(m1, m2); }
    public static double[][] getmultiplyMatrices(double[][] m1, double[][] m2) {return multiplyMatrices(m1, m2); }
	
    private static double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {  
    	
//    	System.out.println(" !!! multiplyMatrices !!! ");
//    	System.out.println("matrix1 :  " + Arrays.deepToString(matrix1));
//    	System.out.println("matrix2 :  " + Arrays.deepToString(matrix2));   
    	
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;                     

        if (cols1 != rows2) {
            throw new IllegalArgumentException("!!! Matrices cannot be multiplied !!!. Columns of first matrix must equal rows of second matrix. Check : StateVector and associate");
        }
         
        double[][] result = new double[rows1][cols2];  // Initialize resulting matrix

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
//        System.out.println("matrixA  X MatrixB  : " +  Arrays.deepToString(result));

        return result;
    }
}
