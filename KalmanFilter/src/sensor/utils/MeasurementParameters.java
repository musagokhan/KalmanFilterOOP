package sensor.utils;

public class MeasurementParameters {
	
	public final static int dimension = 3;
	
	public final static double xPositionForStartingMeasurement = 0;
	public final static double yPositionForStartingMeasurement = 0;
	public final static double zPositionForStartingMeasurement = 0;
	public final static double xSpeedForStartingMeasurement = 1;
	public final static double ySpeedForStartingMeasurement = 1; 
	public final static double zSpeedForStartingMeasurement = 1;
	public final static double xAccelerationForStartingMeasurement = 0;
	public final static double yAccelerationForStartingMeasurement = 0;
	public final static double zAccelerationForStartingMeasurement = 0;
	public final static double deltaT = 1;
	
	public final static double mu = 0;
	public final static double sigma = 0.01;
	
	public final static double mu_R = 0;
	public final static double sigma_R = 0.1;
	
	public final static double mu_theta = 0;
	public final static double sigma_theta = 0.01;
	
	public final static double mu_phi = 0;
	public final static double sigma_phi = 0.01;
	
	public static double[][] matrixF(double dt) {
		double[][] matrixF = new double[9][9];
	    matrixF[0][0] = 1; matrixF[0][1] = 0; matrixF[0][2] = 0; matrixF[0][3] = 0; matrixF[0][4] = 0; matrixF[0][5] = 0; matrixF[0][6] = 0; matrixF[0][7] = 0; matrixF[0][8] = 0;
	    matrixF[1][0] = 0; matrixF[1][1] = 1; matrixF[1][2] = 0; matrixF[1][3] = 0; matrixF[1][4] = 0; matrixF[1][5] = 0; matrixF[1][6] = 0; matrixF[1][7] = 0; matrixF[1][8] = 0;
	    matrixF[2][0] = 0; matrixF[2][1] = 0; matrixF[2][2] = 1; matrixF[2][3] = 0; matrixF[2][4] = 0; matrixF[2][5] = 0; matrixF[2][6] = 0; matrixF[2][7] = 0; matrixF[2][8] = 0;
	    matrixF[3][0] = dt; matrixF[3][1] = 0; matrixF[3][2] = 0; matrixF[3][3] = 1; matrixF[3][4] = 0; matrixF[3][5] = 0; matrixF[3][6] = 0; matrixF[3][7] = 0; matrixF[3][8] = 0;
	    matrixF[4][0] = 0; matrixF[4][1] = dt; matrixF[4][2] = 0; matrixF[4][3] = 0; matrixF[4][4] = 1; matrixF[4][5] = 0; matrixF[4][6] = 0; matrixF[4][7] = 0; matrixF[4][8] = 0;
	    matrixF[5][0] = 0; matrixF[5][1] = 0; matrixF[5][2] = dt; matrixF[5][3] = 0; matrixF[5][4] = 0; matrixF[5][5] = 1; matrixF[5][6] = 0; matrixF[5][7] = 0; matrixF[5][8] = 0;
	    matrixF[6][0] = 0.5 * Math.pow(dt, 2); matrixF[6][1] = 0; matrixF[6][2] = 0; matrixF[6][3] = dt; matrixF[6][4] = 0; matrixF[6][5] = 0; matrixF[6][6] = 1; matrixF[6][7] = 0; matrixF[6][8] = 0;
	    matrixF[7][0] = 0; matrixF[7][1] = 0.5 * Math.pow(dt, 2); matrixF[7][2] = 0; matrixF[7][3] = 0; matrixF[7][4] = dt; matrixF[7][5] = 0; matrixF[7][6] = 0; matrixF[7][7] = 1; matrixF[7][8] = 0;
	    matrixF[8][0] = 0; matrixF[8][1] = 0; matrixF[8][2] = 0.5 * Math.pow(dt, 2); matrixF[8][3] = 0; matrixF[8][4] = 0; matrixF[8][5] = dt; matrixF[8][6] = 0; matrixF[8][7] = 0; matrixF[8][8] = 1;
	    return matrixF;
	}

	
	public static double[][] measurementStartingParameters = {{
		xPositionForStartingMeasurement, yPositionForStartingMeasurement, zPositionForStartingMeasurement, 
		xSpeedForStartingMeasurement, ySpeedForStartingMeasurement, zSpeedForStartingMeasurement, 
		xAccelerationForStartingMeasurement, yAccelerationForStartingMeasurement, zAccelerationForStartingMeasurement
		}};
	
	
	public static double[] measurementStartingPositions = {xPositionForStartingMeasurement, yPositionForStartingMeasurement, zPositionForStartingMeasurement};
	
	public static double[] measurementStartingSpeeds = {xSpeedForStartingMeasurement, ySpeedForStartingMeasurement, zSpeedForStartingMeasurement };
	
	public static double[] measurementStartingAccelerations = {xAccelerationForStartingMeasurement, yAccelerationForStartingMeasurement, zAccelerationForStartingMeasurement};
		
}
