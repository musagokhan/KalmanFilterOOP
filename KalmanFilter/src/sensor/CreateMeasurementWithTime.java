package sensor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import sensor.utils.*;

public class CreateMeasurementWithTime {
	
	private double preTime = 0;
	private double lastTime = 1;
	private double deltaTime = 1;
	
	private int lastStep;	
	private double[] measurementWithTime;
	public static double[][] allKinematics; 
	private int key = 0;
	
	
	public CreateMeasurementWithTime() {
		this.lastStep = lastStep;

		this.allKinematics = MeasurementParameters.measurementStartingParameters;
		this.measurementWithTime = new double[MeasurementParameters.dimension + 1 ]; // +1 for time
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
		
		key = key + 1;		
		for (int i = 0; i < 3; i++) {
			double noise = gausianNoiseCreate(); // !!!!! create Noise
			this.measurementWithTime[i] = this.allKinematics[0][i] + noise;
			}
		
		this.measurementWithTime[this.measurementWithTime.length - 1] = this.lastTime;
		
        
	}
	
	private void show() {
        System.out.print("Sonuç Matrisi :  ");
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
	
    
    private double gausianNoiseCreate() {
    	
    	Random random = new Random();
    	double z1 = random.nextGaussian(); 
    	double z2 = random.nextGaussian(); 
    	return sensor.utils.MeasurementParameters.mu + sensor.utils.MeasurementParameters.sigma * (z1 + z2); 
    }
    
    
	public double[] measurement (double lastTime) {
		measurementCalculation(lastTime);
//		System.out.println(Arrays.toString(this.measurementWithTime));
		return this.measurementWithTime;
	}
	

}
