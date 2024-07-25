package kf.utils;

import java.util.Arrays;
import java.util.List;


public class KFConstant {
	
	public final static int diffParametersNumberInStateVector = 3;
	public static final int lastWorkStep = 8;
	public static final int sniffMeasNumForInitStateVector = 3;
	
	private static double[][] matrixA;
	private static double[][] matrixA_tr;  // = A matrix transpose
	
	
	
	private static final double sigmaXx = 0;
	private static final double sigmaXy = 0;
	private static final double sigmaXz = 0;
	
	private static final double sigmaVx = 0;
	private static final double sigmaVy = 0;
	private static final double sigmaVz = 0;
	
	private static final double sigmaAx = 0;
	private static final double sigmaAy = 0;
	private static final double sigmaAz = 0;
	
	
	private static double[][] matrixQ;
	
    public static double[][] getMatrixA(String AorA_tr,int dimension, double deltaT) {
    	double t_v = deltaT;
    	double t_a = 0.5 * Math.sqrt(Math.pow(deltaT, 2));
    	
        if (dimension == 1) {
//        	System.out.println("KFConstant / dimension == 1");        
            matrixA = new double[][] {
                {1.0, 0.0, 0.0},
                {t_v, 1.0, 0.0},
                {t_a, t_v, 1.0},
            };
            
            matrixA_tr = new double[][] {
	              {1.0, t_v, t_a},
	              {0.0, 1.0, t_v},
	              {0.0, 0.0, 1.0},
	          }; 
            
            
        }else if (dimension == 2) {
//        	System.out.println("KFConstant / dimension == 2");    
        	matrixA = new double[][] {
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                {t_v, 0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, t_v, 0.0, 1.0, 0.0, 0.0},
                {t_a, 0.0, t_v, 0.0, 1.0, 0.0},
                {0.0, t_a, 0.0, t_v, 0.0, 1.0},
            };
            
            matrixA_tr = new double[][] {
		          {1.0, 0.0, t_v, 0.0, t_a, 0.0},
		          {0.0, 1.0, 0.0, t_v, 0.0, t_a},
		          {0.0, 0.0, 1.0, 0.0, t_v, 0.0},
		          {0.0, 0.0, 0.0, 1.0, 0.0, t_v},
		          {0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
		          {0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
		      };
            
        }else if (dimension == 3) {       	
//        	System.out.println("KFConstant / dimension == 3");          
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
            
        	matrixA_tr = new double[][] {
		          {1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a, 0.0, 0.0},
		          {0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a, 0.0},
		          {0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a},
		          {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0},
		          {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0},
		          {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v},
		          {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0},
		          {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
		          {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
		      };  
            
        }else  {
        	System.out.println("!!! STOP SoftWare : java.lang.NullPointerException !!!");
        	System.out.println("dimension max value = 3. Check your StateVector and association");
        }
        
//        System.out.println("KFConstant / matrixA     [2.matrix] : " +  Arrays.deepToString(matrixA));
//        System.out.println("KFConstant / matrixA_tr  [2.matrix] : " +  Arrays.deepToString(matrixA_tr));
        
        if (AorA_tr == "A") {
        	return matrixA;
        } else if (AorA_tr == "A_tr") {
            return matrixA_tr;
        } else {
        	System.out.println("!!! Software Crash - NullPointerEx. - Check matrix Name (A or A_tr)");
        	return null;
        }
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
	
	
	
	 public static double[][] getQMatrixForPrediction(int dimension) {
	    
		 double sXx = Math.pow(sigmaXx, 2);
		 double sVx = Math.pow(sigmaVx, 2);
		 double sAx = Math.pow(sigmaAx, 2);
		 
		 double sXy = Math.pow(sigmaXy, 2);
		 double sVy = Math.pow(sigmaVy, 2);
		 double sAy = Math.pow(sigmaAy, 2);
		 
		 double sXz = Math.pow(sigmaXz, 2);
		 double sVz = Math.pow(sigmaVz, 2);
		 double sAz = Math.pow(sigmaAz, 2);
		 
	     if (dimension == 1) {
	     	matrixQ = new double[][] {
	     		{sXx, 0.0, 0.0},
	            {0.0, sVx, 0.0},
	            {0.0, 0.0, sAx},
	        };
	      }else if (dimension == 2) {
	    	  matrixQ = new double[][] {
		     		{sXx, 0.0, 0.0, 0.0, 0.0, 0.0},
		     		{0.0, sXy, 0.0, 0.0, 0.0, 0.0},
		            {0.0, 0.0, sVx, 0.0, 0.0, 0.0},
		            {0.0, 0.0, 0.0, sVy, 0.0, 0.0},
		            {0.0, 0.0, 0.0, 0.0, sAx, 0.0},
		            {0.0, 0.0, 0.0, 0.0, 0.0, sAy},
		        }; 
	      }else if (dimension == 3) {
	    	  matrixQ = new double[][] {
		     		{sXx, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
		     		{0.0, sXy, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
		     		{0.0, 0.0, sXz, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
		            {0.0, 0.0, 0.0, sVx, 0.0, 0.0, 0.0, 0.0, 0.0},
		            {0.0, 0.0, 0.0, 0.0, sVy, 0.0, 0.0, 0.0, 0.0},
		            {0.0, 0.0, 0.0, 0.0, 0.0, sVy, 0.0, 0.0, 0.0},
		            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, sAx, 0.0, 0.0},
		            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, sAy, 0.0},
		            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, sAy},
		        }; 
	      }
	         
	      return matrixQ;
	 }
	
	
	private KFConstant() {}
	
		

}
