package execute;

import java.util.Arrays;
import kf.KF;
import kf.init.KFInitManager;
import sensor.MeasurementManagement;

public class RunKF {
	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	private double measurementTime;
	private double[][] measurement;
	private double[][] measurementCovariance;
	private KF track0; 
	
	
	public RunKF() {}
	public void runKF (MeasurementManagement measurementDatas, KFInitManager kfInitManager) {		
						
		measurementDatas.createMeasurementCartesian();
		this.measurement = measurementDatas.getMeasurement();
		this.measurementTime = measurementDatas.getMeasurementTime();
		this.measurementCovariance = measurementDatas.getMeasurementCovariance();
			
		
		
		if (stateVector == null) {
		// --- KF init Assing  START (3 options : SniffMeasurement - RandomAssing - BatchEst) ---//
			if ( kfInitManager.initManager("SniffMeasurement", measurement, measurementTime) ) { // run only 1 time
				this.stateVector = kfInitManager.getStateVector();
				this.covarianceMatrix = kfInitManager.getCovarianceMatrix();
				track0 = new KF(stateVector, covarianceMatrix);
			} else {
				this.stateVector = null;
				this.covarianceMatrix = null;
			}
		// --- KF init Assing Stop ---//
		} else {
		// Kalman Start //
			System.out.println("Measurement     : " + Arrays.deepToString(measurement));
			System.out.println("Bfr KF    SM    : " + Arrays.deepToString(track0.getStateVector()));
			track0.KFDoit(measurementTime, measurement, covarianceMatrix);
			System.out.println("Aft KF    SM    : " + Arrays.deepToString(track0.getStateVector()));
		// Kalman Stop //
		}
		
		
		

	}
	

}
