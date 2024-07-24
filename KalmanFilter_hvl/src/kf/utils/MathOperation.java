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
		
		System.out.println("1. matrix : " +  Arrays.toString(inputStateVector ));
		System.out.println("2. matrix : " +  Arrays.toString(KFConstant.getMatrixA(dimension, deltaT)) );
		
		
		//multiplyMatrices(inputStateVector , KFConstant.getMatrixA(dimension, deltaT) );
		return multiplyMatrices(inputStateVector , KFConstant.getMatrixA(dimension, deltaT) );
	}
	
	public static double[][] getStateVectorCalculateForKFPrediction (double[][] inputStateVector, int dimension, double deltaT ) {
		return stateVectorCalculateForKFPrediction(inputStateVector, dimension, deltaT);
	}
	
    private static double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
    	
    	System.out.println(" !!! multiplyMatrices !!! ");
    	
    	
    	System.out.println("matrix1 :  " + Arrays.toString(matrix1));
    	System.out.println("matrix2 :  " + Arrays.toString(matrix2));
    	
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;
               

        // Check if multiplication is possible
//        if (cols1 != rows2) {
//            throw new IllegalArgumentException("Matrices cannot be multiplied. Columns of first matrix must equal rows of second matrix.");
//        }

         
        double[][] result = new double[rows1][cols2];  // Initialize resulting matrix

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        System.out.println("-result :- : " +  Arrays.toString(result));
        return result;
    }
}
