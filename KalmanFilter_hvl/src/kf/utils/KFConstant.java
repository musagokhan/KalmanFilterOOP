package kf.utils;

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
	
	
	
	
	public static final int lastWorkStep = 5;
	
	public static final int sniffMeasNumForInitStateVector = 3;
	
	
	
	private static double[][] matrixA;

    public static double[][] getMatrixA(int dimension, double deltaT) {
    	double t_v = deltaT;
    	double t_a = 0.5 * Math.sqrt(Math.pow(deltaT, 2));
    	
        if (dimension == 1) {
//        	System.out.println("KFConstant / dimension == 1");
//            matrixA = new double[][] {
//                {1.0, t_v, t_a},
//                {0.0, 1.0, t_v},
//                {0.0, 0.0, 1.0},
//            };          
            matrixA = new double[][] {
                {1.0, 0.0, 0.0},
                {t_v, 1.0, 0.0},
                {t_a, t_v, 1.0},
            };
            
            
        }else if (dimension == 2) {
//        	System.out.println("KFConstant / dimension == 2");
//        	matrixA = new double[][] {
//                {1.0, 0.0, t_v, 0.0, t_a, 0.0},
//                {0.0, 1.0, 0.0, t_v, 0.0, t_a},
//                {0.0, 0.0, 1.0, 0.0, t_v, 0.0},
//                {0.0, 0.0, 0.0, 1.0, 0.0, t_v},
//                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
//            };            
        	matrixA = new double[][] {
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                {t_v, 0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, t_v, 0.0, 1.0, 0.0, 0.0},
                {t_a, 0.0, t_v, 0.0, 1.0, 0.0},
                {0.0, t_a, 0.0, t_v, 0.0, 1.0},
            };
            
        }else if (dimension == 3) {       	
//        	System.out.println("KFConstant / dimension == 3");
//        	matrixA = new double[][] {
//                {1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a, 0.0, 0.0},
//                {0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a, 0.0},
//                {0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a},
//                
//                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v},
//                
//                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
//            };            
        	matrixA = new double[][] {
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {t_v, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, t_v, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, t_v, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0},
                {t_a, 0.0, 0.0, t_v, 0.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, t_a, 0.0, 0.0, t_v, 0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, t_a, 0.0, 0.0, t_v, 0.0, 0.0, 1.0},
            };
            
        }else  {
        	System.out.println("!!! STOP SoftWare : java.lang.NullPointerException !!!");
        	System.out.println("dimension max value = 3. Check your StateVector and association");
        }
        
//        System.out.println("KFConstant / matrixA  [2.matrix] : " +  Arrays.deepToString(matrixA));
        
        return matrixA;
    }
    
	public final static double posX = 0;
	public final static double posY = 7.3;
	public final static double posZ = 8.7;
	public final static double speedX = 1.79;
	public final static double speedY = 2.89; 
	public final static double speedZ = 2.5;
	public final static double accelerationX = 0.1;
	public final static double accelerationY = 0.65;
	public final static double accelerationZ = 1;
	public static double[] track0StateVector;
	
	public double[] gettrack0StateVector () {
		
		if (diffParametersNumberInStateVector == 3) {
			track0StateVector = new double[] {posX, posY, posZ, speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ};  // 3D
		}else if (diffParametersNumberInStateVector == 2) {
			track0StateVector = new double[] {posX, posY, speedX, speedY, accelerationX, accelerationY};  //2D
		}else {
			track0StateVector = new double[] {posX, speedX, accelerationX };  //1D
		}	
		return track0StateVector;
	}

	
	
	
	private KFConstant() {}
	
		

}
