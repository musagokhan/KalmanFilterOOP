package execute;
import java.util.Arrays;
import java.util.List;

import kf.KF;
import kf.utils.*;
import kf.init.KFInitManager;
import kf.init.KF_BatchEst;
import kf.init.KF_RandomAssing;
import kf.init.KF_SniffMeasurement;
import ekf.EKF;
import ekf.init.EKF_BatchEst;
import ekf.init.EKF_RandomAssing;
import ekf.init.EKF_SniffMeasurement;
import sensor.MeasurementManagement;

public class MainOperation {

	public static void main(String[] args) {

		System.out.println("--- WORK Start ---");	
		MeasurementManagement measurementDatas = new MeasurementManagement();
		String FilterType =    "KalmanFilter"; // "ExtendedKalmanFilter"; //  
		
		KF track0 = null; 
		EKF track1 = null; 
		boolean firstStepFlag = true;
		List<double[][]>  XandPmatrices;
		
		double[][] stateVector;
		double[][] covarianceMatrix;
		
		// use for initation
//		KF_SniffMeasurement kFInitationWithSnifMeas = new KF_SniffMeasurement();
//		KF_RandomAssing kFInitationWithRandom = new KF_RandomAssing();
//		KF_BatchEst kFInitationWithBatchEst = new KF_BatchEst();
		KFInitManager kfInitManager = new KFInitManager();
		
		EKF_SniffMeasurement ekFInitationWithSnifMeas = new EKF_SniffMeasurement(); //31072024
		EKF_RandomAssing ekFInitationWithRandom = new EKF_RandomAssing();
		EKF_BatchEst ekFInitationWithBatchEst = new EKF_BatchEst();
		
		for (int workStep=0; workStep < KFConstant.lastWorkStep ; workStep++) {
			
			double measurementTime;
			double[][] measurement;
			double[][] measurementCovariance;
					
			
			if (FilterType == "KalmanFilter") {
				
				measurementDatas.createMeasurementCartesian(workStep);
				measurement = measurementDatas.getMeasurement();
				measurementTime = measurementDatas.getMeasurementTime();
				measurementCovariance = measurementDatas.getMeasurementCovariance();
			
				// --- KF init Assing  START (3 options) ---//
				XandPmatrices = kfInitManager.initManager("RandomAssing", measurement, measurementTime);
				stateVector = XandPmatrices.get(0);
				covarianceMatrix = XandPmatrices.get(1);
				
//				kFInitationWithRandom.getMainKFInitation(measurement , measurementTime);
//				stateVector = kFInitationWithRandom.getStateVector();
//				covarianceMatrix = kFInitationWithRandom.getCovarianceMatrix();
				
//				kFInitationWithBatchEst.getMainKFInitation(measurement , measurementTime);
//				stateVector = kFInitationWithBatchEst.getStateVector();
//				covarianceMatrix = kFInitationWithBatchEst.getCovarianceMatrix();

				// --- KF init Assing Stop ---//	
				
				
				
				// Kalman Start //
				if (stateVector != null) { 
					System.out.println("   " + workStep + ".Step Kalman phase");
					if (firstStepFlag) {// ilk adim nesne olustur.
						firstStepFlag = false;
						track0 = new KF(stateVector, covarianceMatrix);
					}	
					System.out.println("Measurement      : " + Arrays.deepToString(measurement));
					System.out.println("bfr EKF    SM    : " + Arrays.deepToString(track0.getStateVector()));
					track0.KFDoit(measurementTime, measurement, covarianceMatrix);
					System.out.println("aft EKF    SM    : " + Arrays.deepToString(track0.getStateVector()));
				}else {				
					System.out.println("   " + workStep + ".Step waiting to Kalman. Sniff Meas and calculating initStateVector");
				}
				// Kalman Stop //
			
				
			} else if (FilterType == "ExtendedKalmanFilter") {
				measurementDatas.createMeasurementGlobal(workStep);
				measurement = measurementDatas.getMeasurement();
				measurementTime = measurementDatas.getMeasurementTime();
				measurementCovariance = measurementDatas.getMeasurementCovariance();
				
				// --- EKF init Assing  START (3 options) ---//					
				ekFInitationWithSnifMeas.getMainEKFInitation(measurement , measurementTime);
				stateVector = ekFInitationWithSnifMeas.getStateVector();
				covarianceMatrix = ekFInitationWithSnifMeas.getCovarianceMatrix();
				
//				ekFInitationWithRandom.getMainEKFInitation(measurement , measurementTime);
//				stateVector = ekFInitationWithRandom.getStateVector();
//				covarianceMatrix = ekFInitationWithRandom.getCovarianceMatrix();
				
//				ekFInitationWithBatchEst.getMainEKFInitation(measurement , measurementTime);
//				stateVector = ekFInitationWithBatchEst.getStateVector();
//				covarianceMatrix = ekFInitationWithBatchEst.getCovarianceMatrix();
				// --- EKF init Assing Stop ---//	
								
				// E.Kalman Start //
				if (stateVector != null) {
					System.out.println("   " + workStep + ".Step E.Kalman phase");
					if (firstStepFlag) {// ilk adim nesne olustur.
						firstStepFlag = false;
						track1 = new EKF(stateVector, covarianceMatrix); //, deltaT); 
					}
					System.out.println("Measurement      : " + Arrays.deepToString(measurement));
					System.out.println("bfr EKF    SM    : " + Arrays.deepToString(track1.getStateVector()));
					track1.EKFDoit(measurementTime, measurement, covarianceMatrix);
					System.out.println("aft EKF    SM    : " + Arrays.deepToString(track1.getStateVector()));
					
				}else {				
						System.out.println("   " + workStep + ".Step waiting to E.Kalman. Sniff Meas and calculating initStateVector");
				}
				// E.Kalman Stop //
			
			}
			
		}	
		
		System.out.println("--- WORK Stop ---");
	}
	
	// TODO : MAP gibi bir formda SV ve CM tut.
	

}
