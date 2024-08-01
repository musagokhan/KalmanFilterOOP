package ekf.utils;

public class EKFConstant {
	
	public final static int diffParametersNumberInStateVector = 3;  // x, Vx, ax
	public static final int lastWorkStep = 100;
	public static final int sniffMeasNumForInitStateVector = 3;
	
	private static double[][] matrixA;
	private static double[][] matrixA_tr;  // = A matrix transpose
	private static double[][] matrixH;
	private static double[][] matrixH_tr;
	private static double[][] matrixR;
	private static double[][] matrixQ;
	
	
	private static final double sigmaXx = 0.01;
	private static final double sigmaXy = 0.01;
	private static final double sigmaXz = 0.01;
	
	private static final double sigmaVx = 0.01;
	private static final double sigmaVy = 0.01;
	private static final double sigmaVz = 0.01;
	
	private static final double sigmaAx = 0.00001;
	private static final double sigmaAy = 0.00001;
	private static final double sigmaAz = 0.00001;
	

	
    public static double[][] getMatrixA(boolean AorA_tr,int dimension, double deltaT) {
    	double t_v = deltaT;
    	double t_a = 0.5 * Math.sqrt(Math.pow(deltaT, 2));
    	
        if (dimension == 1) {
//        	System.out.println("KFConstant / dimension == 1");        
            matrixA = new double[][] {
                {1.0, t_v, t_a},
                {0.0, 1.0, t_v},
                {0.0, 0.0, 1.0},
            };
            
            matrixA_tr = new double[][] {
                {1.0, 0.0, 0.0},
                {t_v, 1.0, 0.0},
                {t_a, t_v, 1.0},
	          }; 
            
            
        }else if (dimension == 2) {
//        	System.out.println("KFConstant / dimension == 2");    
        	matrixA = new double[][] {
                {1.0, 0.0, t_v, 0.0, t_a, 0.0},
                {0.0, 1.0, 0.0, t_v, 0.0, t_a},
                {0.0, 0.0, 1.0, 0.0, t_v, 0.0},
                {0.0, 0.0, 0.0, 1.0, 0.0, t_v},
                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            };
            
            matrixA_tr = new double[][] {
                {1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                {t_v, 0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, t_v, 0.0, 1.0, 0.0, 0.0},
                {t_a, 0.0, t_v, 0.0, 1.0, 0.0},
                {0.0, t_a, 0.0, t_v, 0.0, 1.0},
		      };
            
        }else if (dimension == 3) {       	
//        	System.out.println("KFConstant / dimension == 3");
        	matrixA = new double[][] {
                {1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a, 0.0},
                {0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0, t_a},
                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, t_v},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0}
            };
          
        	matrixA_tr = new double[][] {
        	    {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        	    {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        	    {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        	    {t_v, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        	    {0.0, t_v, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
        	    {0.0, 0.0, t_v, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0},
        	    {t_a, 0.0, 0.0, t_v, 0.0, 0.0, 1.0, 0.0, 0.0},
        	    {0.0, t_a, 0.0, 0.0, t_v, 0.0, 0.0, 1.0, 0.0},
        	    {0.0, 0.0, t_a, 0.0, 0.0, t_v, 0.0, 0.0, 1.0}
		      }; 
		                  
        }else  {
        	System.out.println("!!! [KFConstant.getMatrixA] . STOP SoftWare : java.lang.NullPointerException !!!");
        	System.out.println("dimension max value = 3. Check your StateVector and association");
        }
                
        if (AorA_tr) { // A matrix
        	return matrixA;
        } else {
            return matrixA_tr;
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
	
	
	public static double[][] getHmatrix( double[][] measurement,  boolean type,  int dimension){
		
		double r     = measurement[0][0];
		double theta = measurement[1][0];
		double phi   = measurement[2][0];
		
		
		if (dimension == 1) {
			matrixH    = new double[][] { 	{Math.sin(phi)*Math.cos(theta)   ,0,0},	{-r*Math.sin(phi)*Math.sin(theta),0,0}, {r*Math.cos(phi)*Math.cos(theta) ,0,0} }; //3x3
			matrixH_tr = new double[][] {{Math.sin(phi)*Math.cos(theta), -r*Math.sin(phi)*Math.sin(theta), r*Math.cos(phi)*Math.cos(theta)}, {0, 0, 0},	{0, 0 ,0} };  //3x3	
				
		} else if (dimension == 2) {
			
			matrixH    = new double[][] {	{   Math.sin(phi)*Math.cos(theta), 0, 0,   Math.sin(phi)*Math.sin(theta), 0, 0}, 
											{-r*Math.sin(phi)*Math.sin(theta), 0, 0, r*Math.sin(phi)*Math.cos(theta), 0, 0}, 
											{ r*Math.cos(phi)*Math.cos(theta), 0, 0, r*Math.cos(phi)*Math.sin(theta), 0, 0} }; // 3x6

			matrixH_tr = new double[][] {	{Math.sin(phi)*Math.cos(theta), -r*Math.sin(phi)*Math.sin(theta), r*Math.cos(phi)*Math.cos(theta)}, {0,0,0} , {0,0,0},
											{Math.sin(phi)*Math.sin(theta),  r*Math.sin(phi)*Math.cos(theta), r*Math.cos(phi)*Math.sin(theta)}, {0,0,0} , {0,0,0} 	}; // 6x3
			
		}else if (dimension == 3) {
//			matrixH    = new double[][] {	{   Math.sin(phi)*Math.cos(theta), 0, 0,   Math.sin(phi)*Math.sin(theta), 0, 0,    Math.cos(phi), 0, 0}, 
//											{-r*Math.sin(phi)*Math.sin(theta), 0, 0, r*Math.sin(phi)*Math.cos(theta), 0, 0,  0              , 0, 0}, 
//											{ r*Math.cos(phi)*Math.cos(theta), 0, 0, r*Math.cos(phi)*Math.sin(theta), 0, 0, -r*Math.sin(phi), 0, 0} }; // 3x9
//			
//			matrixH_tr = new double[][] {
//			    {Math.sin(phi)*Math.cos(theta),-r*Math.sin(phi)*Math.sin(theta), r*Math.cos(phi)*Math.cos(theta)}, {0, 0, 0},  {0, 0, 0},
//			    {Math.sin(phi)*Math.sin(theta), r*Math.sin(phi)*Math.cos(theta), r*Math.cos(phi)*Math.sin(theta)}, {0, 0, 0},  {0, 0, 0},
//			    {Math.cos(phi)				  , 0							   , -r*Math.sin(phi)}				 , {0, 0, 0},  {0, 0, 0}	};
			
		//mami
			    
			matrixH    = new double[][] {	
					{   Math.sin(phi)*Math.cos(theta), 0, 0,   Math.sin(phi)*Math.sin(theta), 0, 0,    Math.sin(phi), 0, 0}, 
					{-r*Math.sin(phi)*Math.sin(theta), 0, 0, r*Math.sin(phi)*Math.cos(theta), 0, 0,  0              , 0, 0}, 
					{ r*Math.cos(phi)*Math.cos(theta), 0, 0, r*Math.cos(phi)*Math.sin(theta), 0, 0,  r*Math.cos(phi), 0, 0} }; // 3x9

			matrixH_tr = new double[][] {
					{Math.sin(phi)*Math.cos(theta),-r*Math.sin(phi)*Math.sin(theta), r*Math.cos(phi)*Math.cos(theta)}, {0, 0, 0},  {0, 0, 0},
					{Math.sin(phi)*Math.sin(theta), r*Math.sin(phi)*Math.cos(theta), r*Math.cos(phi)*Math.sin(theta)}, {0, 0, 0},  {0, 0, 0},
					{Math.sin(phi)				  , 0							   , -r*Math.cos(phi)}				 , {0, 0, 0},  {0, 0, 0}	};
			
		} else  {
        	System.out.println("!!! [KFConstant.getHmatrix] . STOP SoftWare : java.lang.NullPointerException !!!");
        	System.out.println("dimension max value = 3. Check your StateVector and association");
        	matrixH    = null;
        	matrixH_tr = null;
        }
		
        if (type) {
        	return matrixH;
        } else {
            return matrixH_tr;
        }	
	}
	 
	
	public static double[][] getRmatrix(int dimension){
		if (dimension == 1) {
			return new double[][] {{sigmaXx}};
		} else if (dimension == 2) {
			return new double[][] {	{sigmaXx,0}, {0,sigmaXy} };
		}else if (dimension == 3) {
			return new double[][] {	{sigmaXx,0,0}, {0,sigmaXy,0}, {0,0,sigmaXz}	};
		} else  {
        	System.out.println("!!! [KFConstant.getRmatrix] . STOP SoftWare : java.lang.NullPointerException !!!");
        	System.out.println("dimension max value = 3. Check your StateVector and association");
        	return null;
        }
	}
		
	 
	 private EKFConstant() {}
	

}
