package sensor;
import java.util.Arrays;
import java.util.Random;

import sensor.utils.*;

public class CreateMeasurementWithTime {
	
	private double preTime = 0;
	private double lastTime = 1;
	private double deltaTime = 1;
	
	private int lastStep;	
	private double[] measurementCartesianCoordinatePure = new double[MeasurementParameters.dimension];
	private double[] measurementCartesianCoordinateWithTime = new double[MeasurementParameters.dimension];
	private double[] measurementGlobalCoordinateWithTime = new double[MeasurementParameters.dimension];
	public static double[][] allKinematics; 
	
	
	public CreateMeasurementWithTime() {
		this.lastStep = lastStep;

		this.allKinematics = MeasurementParameters.measurementStartingParameters;
		this.measurementCartesianCoordinateWithTime = new double[MeasurementParameters.dimension + 1 ]; // +1 for time
	}
	
	private void deltaTimeCalculate (double lastTime) {
		this.lastTime = lastTime;
		this.deltaTime = this.lastTime - this.preTime;
		this.preTime = this.lastTime ;
	}
			
	private void measurementCalculation(double lastTime) {
		deltaTimeCalculate (lastTime);				
		this.allKinematics = multiplyMatrices(this.allKinematics, sensor.utils.MeasurementParameters.matrixF(this.deltaTime) );
		//show();
		for (int i = 0; i < MeasurementParameters.dimension; i++) {	this.measurementCartesianCoordinatePure[i] = this.allKinematics[0][i];}	
	}
		
	private void measurementCalculationGlobalCoordinate (double lastTime) {
		measurementCalculation(lastTime);
		
		double x = this.measurementCartesianCoordinatePure[0];
		double y = this.measurementCartesianCoordinatePure[1];
		double z = this.measurementCartesianCoordinatePure[2];
		
		double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) + gausianNoiseCreateForR();
		double theta = Math.atan2(y, x) + gausianNoiseCreateForTheta(); // radyan
		double phi = Math.acos(z / r) + gausianNoiseCreateForPhi();   // radyan
//		double thetaDegree = Math.toDegrees(theta);
//		double phiDegree  = Math.toDegrees(phi);
//        System.out.println("theta : " + theta +  " / "  + thetaDegree);
//        System.out.println("phi   : " + phi   +  " / "  + phiDegree);
		
        
		this.measurementGlobalCoordinateWithTime = new double[] { r, theta, phi , this.lastTime};
		
	}
	
	private void measurementCalculationCartesianCoordinate (double lastTime) {
		measurementCalculation(lastTime);
		for (int i = 0; i < MeasurementParameters.dimension; i++) {
			double noise = gausianNoiseCreate(); // !!!!! create Noise
			this.measurementCartesianCoordinateWithTime[i] = this.measurementCartesianCoordinatePure[i] + noise;
		}	
		
		this.measurementCartesianCoordinateWithTime[this.measurementCartesianCoordinateWithTime.length - 1] = this.lastTime;
	}
		
	private void show() {
        System.out.print("Sonu� Matrisi :  ");
        for (int i = 0; i < this.allKinematics.length; i++) {
            for (int j = 0; j < this.allKinematics[i].length; j++) {
                System.out.print(this.allKinematics[i][j] + " ");
            }
            System.out.println();
        }
	}
		
    private static double[][] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;

        // Check if multiplication is possible
        if (cols1 != rows2) {
            throw new IllegalArgumentException("Matrices cannot be multiplied. Columns of first matrix must equal rows of second matrix.");
        }

         
        double[][] result = new double[rows1][cols2];  // Initialize resulting matrix
 
        // Perform matrix multiplication
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }
    
    private double gausianComman(double Mu, double Sigma) {
    	Random random = new Random();
    	double value1 = random.nextGaussian(); 
    	double value2 = random.nextGaussian(); 
    	return Mu + Sigma * (value1 + value2);
    }
	    
    private double gausianNoiseCreate() {return gausianComman(MeasurementParameters.mu, MeasurementParameters.sigma);}
    
    private double gausianNoiseCreateForR() {return gausianComman(MeasurementParameters.mu_R, MeasurementParameters.sigma_R);}
    
    private double gausianNoiseCreateForTheta() {return gausianComman(MeasurementParameters.mu_theta, MeasurementParameters.sigma_theta);}
   
    private double gausianNoiseCreateForPhi() {return gausianComman(MeasurementParameters.mu_phi, MeasurementParameters.sigma_phi);}
       
    
        
	public double[] measurementCartesian (double lastTime) {
		measurementCalculationCartesianCoordinate(lastTime);
//		System.out.println("LOG - / measurementCartesian");
//		System.out.println(Arrays.toString(this.measurementCartesianCoordinateWithTime));
		return this.measurementCartesianCoordinateWithTime;
	}
	
	public double[] measurementGlobal (double lastTime) {
		measurementCalculationGlobalCoordinate(lastTime);
//		System.out.println("LOG - / measurementGlobal");
//		System.out.println(Arrays.toString(this.measurementGlobalCoordinateWithTime));
		return this.measurementCartesianCoordinateWithTime;
	}

}
