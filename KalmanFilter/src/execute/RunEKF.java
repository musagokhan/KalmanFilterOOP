package execute;

import java.util.Arrays;

import ekf.EKF;
import ekf.converters.CoordinateSystemConvert;
import ekf.init.EKFInitManager;
import sensor.MeasurementManagement;

public class RunEKF {
	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	private double measurementTime;
	private double[][] measurement;
	private double[][] measurementCovariance;
	
	private double[][] measurementCartesian;
	private EKF track0; 
	
	
	public RunEKF() {}
	
	public void runEKF (MeasurementManagement measurementDatas, EKFInitManager ekfInitManager) {		
						
		measurementDatas.createMeasurementGlobal();
		this.measurement = measurementDatas.getMeasurement();
		this.measurementTime = measurementDatas.getMeasurementTime();
		this.measurementCovariance = measurementDatas.getMeasurementCovariance();
			
		this.measurementCartesian = CoordinateSystemConvert.glabalToCartesian(this.measurement);
		
		if (stateVector == null) {
		// --- EKF init Assing  START (3 options : SniffMeasurement - RandomAssing - BatchEst) ---//
			if ( ekfInitManager.initManager("SniffMeasurement", this.measurementCartesian, this.measurementTime) ) { // run only 1 time
				this.stateVector = ekfInitManager.getStateVector();
				this.covarianceMatrix = ekfInitManager.getCovarianceMatrix();
				System.out.println("////////////////////");
				System.out.println("this.measurement : " + Arrays.deepToString(this.measurement) );
				System.out.println("this.measurementCartesian : " + Arrays.deepToString(this.measurementCartesian) );
				System.out.println("this.stateVector : " + Arrays.deepToString(this.stateVector) );
				System.out.println("////////////////////");
				track0 = new EKF(stateVector, covarianceMatrix);
			} else {
				this.stateVector = null;
				this.covarianceMatrix = null;
			}
		// --- KF init Assing Stop ---//
		} else {
		// Kalman Start //
			System.out.println("Measurement     : " + Arrays.deepToString(this.measurement));
			System.out.println("Bfr EKF   SM    : " + Arrays.deepToString(track0.getStateVector()));
			track0.EKFDoit(measurementTime, measurement, covarianceMatrix);
			System.out.println("Aft EKF   SM    : " + Arrays.deepToString(track0.getStateVector()));
		// Kalman Stop //
		}
		
		
		

	}
	

}
