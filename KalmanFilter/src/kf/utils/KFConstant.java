package utils;

import java.util.Arrays;
import java.util.List;


public class KFConstant {
	
	public final static int diffParametersNumberInStateVector = 3;
	
//	public static double [][] matrixA = {
//            {1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
//            {0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
//            {0.0, 0.0, 1.0, 0.0, 0.0, 0.0},
//            {0.0, 0.0, 0.0, 1.0, 0.0, 0.0},
//            {0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
//            {0.0, 0.0, 0.0, 0.0, 0.0, 1.0}
//    };
	
	
	
	
	public static final int lastWorkStep = 10;
	
	
	
	public static double [][] matrixA;
	
	public double [][] getMatrixA (int dimension , double deltaT)
	
	if (dimension == 1) {
		matrixA = {
	            {1.0, deltaT, 0.5*Math.sqrt(Math.pow(deltaT, 2) },
	            {0.0, 1.0   , deltaT},
	            {0.0, 0.0   , 1.0},
	    };
	}

	
	
	
	private KFConstant() {}
	
		

}
