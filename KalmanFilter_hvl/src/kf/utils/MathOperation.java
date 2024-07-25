package kf.utils;
import java.util.Arrays;

import kf.utils.*;
public class MathOperation {
	
	private MathOperation() {}
	
	/*
	private static double[] stateVectorLastStepCalculator(double[] positionList, double[] speedList, double[] accelerationList, double deltaT) {
		
		//List<Double> newStateVector = Arrays.asList(null, null, null, null, null, null);
		double[] newStateVector = new double[positionList.length * 3];
		
		for (int step = 0; step < positionList.length ; step++) {
			newStateVector[step]     = positionList.length + speedList[step] * deltaT + 0.5 * accelerationList[step] * deltaT * deltaT;
			newStateVector[step + 3] = speedList.length + accelerationList[step] * deltaT;   
			newStateVector[step + 6] = accelerationList[step];
		}
		
		return newStateVector;
	}
	
	public static double[] getstateVectorLastStepCalculator(double[] positionList, double[] speedList, double[] accelerationList, double deltaT) {
		return stateVectorLastStepCalculator(positionList, speedList, accelerationList, deltaT);
	}
	*/
	
	private static double[][] stateVectorCalculateForKFPrediction (double[][] inputStateVector, int dimension, double deltaT) {	
//		System.out.println("1. matrix : " +  Arrays.deepToString(inputStateVector ));
//		System.out.println("2. matrix : " +  Arrays.deepToString(KFConstant.getMatrixA(dimension, deltaT)) );
		return multiplyMatrices(inputStateVector , KFConstant.getMatrixA(dimension, deltaT) );
	}
	
	public static double[][] getStateVectorCalculateForKFPrediction (double[][] inputStateVector, int dimension, double deltaT ) {
		return stateVectorCalculateForKFPrediction(inputStateVector, dimension, deltaT);
	}
	
    private static double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {  
//    private static double[][] multiplyMatrices(double[][] matrix1a, double[][] matrix2a) { 	
//    	
//    	double[][]  matrix1 = new double[][] {{10.0, 20.0, 30.0}};
//    		
//    	
//    	double[][]  matrix2 = new double[][] {
//            {1.0, 2.0, 3.0},
//            {4.0, 5.0, 6.0},
//            {7.0, 8.0, 9.0},
//        };
        
        
        System.out.println("");
        System.out.println("");
        System.out.println("");
    	System.out.println(" !!! multiplyMatrices !!! ");
    	System.out.println("matrix1 :  " + Arrays.deepToString(matrix1));
    	System.out.println("matrix2 :  " + Arrays.deepToString(matrix2));   
    	
    	System.out.println("Xx : " + matrix1[0][0]);
    	System.out.println("Vx : " + matrix1[0][3]);
    	System.out.println("Ax : " + matrix1[0][6]);
    	System.out.println("");
    	System.out.println("Xy : " + matrix1[0][1]);
    	System.out.println("Vy : " + matrix1[0][4]);
    	System.out.println("Ay : " + matrix1[0][7]);
    	System.out.println("");
    	System.out.println("Xy : " + matrix1[0][2]);
    	System.out.println("Vy : " + matrix1[0][5]);
    	System.out.println("Ay : " + matrix1[0][8]);
    	
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
        System.out.println("matrixA  X MatrixB  : " +  Arrays.deepToString(result));
        System.out.println("");
        System.out.println("");
        System.out.println("");
        return result;
    }
}
