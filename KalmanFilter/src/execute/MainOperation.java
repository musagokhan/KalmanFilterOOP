package execute;
import java.util.Arrays;
import java.util.List;

import kf.KF;
import kf.utils.*;
import kf.init.KF_BatchEst;
import kf.init.KF_RandomAssing;
import kf.init.KF_SniffMeasurement;
import ekf.EKF;
import ekf.init.EKF_BatchEst;
import ekf.init.EKF_RandomAssing;
import ekf.init.EKF_SniffMeasurement;

import sensor.CreateMeasurementWithTime;
import sensor.CreateTimeBrand;


public class MainOperation {

	public static void main(String[] args) {

		System.out.println("--- WORK Start ---");	
		CreateTimeBrand createTimeBrand = new CreateTimeBrand();
		CreateMeasurementWithTime createMeasurementWithTime = new CreateMeasurementWithTime();
		String FilterType =  "KalmanFilter"; //   "ExtendedKalmanFilter"; //      
		
		KF track0 = null; 
		EKF track1 = null; 
		boolean firstStepFlag = true;
		double deltaT = 0.0;
		List<Object> measurementWithTime ;
		
		double[][] stateVector;
		double[][] covarianceMatrix;
		
		// use for initation
		KF_SniffMeasurement kFInitationWithSnifMeas = new KF_SniffMeasurement();
		KF_RandomAssing kFInitationWithRandom = new KF_RandomAssing();
		KF_BatchEst kFInitationWithBatchEst = new KF_BatchEst();	
		
		EKF_SniffMeasurement ekFInitationWithSnifMeas = new EKF_SniffMeasurement(); //31072024
		EKF_RandomAssing ekFInitationWithRandom = new EKF_RandomAssing();
		EKF_BatchEst ekFInitationWithBatchEst = new EKF_BatchEst();
		
		for (int workStep=0; workStep < KFConstant.lastWorkStep ; workStep++) {
			
			double measurementTime;
			double[][] measurement;
					
			double currentTime = createTimeBrand.getTimeCalculation(); // random time
			
			if (FilterType == "KalmanFilter") {
				// --- Create Measurement Start ---//
				measurementWithTime = createMeasurementWithTime.measurementCartesian(currentTime);	// with time	"Cartesian Coordinate Measurement"
				measurementTime = (double) measurementWithTime.get(0); //(double[][]) measurementGlobal.get(0);
				measurement = (double[][]) measurementWithTime.get(1);    //(double[][]) measurementGlobal.get(1);	
				deltaT = measurementTime - deltaT;			
				// --- Create Measurement Stop ---//
			
				// --- KF init Assing  START (3 options) ---//
				
				kFInitationWithSnifMeas.getMainKFInitation(measurement , measurementTime);
				stateVector = kFInitationWithSnifMeas.getStateVector();
				covarianceMatrix = kFInitationWithSnifMeas.getCovarianceMatrix();
				
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
					track0.KFDoit(deltaT, measurement);
					System.out.println("aft EKF    SM    : " + Arrays.deepToString(track0.getStateVector()));
					
//					System.out.println("Measurement      : " + Arrays.deepToString(measurement));
//					System.out.println("bfr KF_Pre SM    : " + Arrays.deepToString(track0.getStateVector()));
////					System.out.println("bfr KF_Pre CV    : " + Arrays.deepToString(track0.getCovarianceMatrix()));
//					track0.getKFPredicted(deltaT);
////					System.out.println("bfr KF_Middle SM : " + Arrays.deepToString(track0.getStateVector()));
////					System.out.println("bfr KF_Middle CV : " + Arrays.deepToString(track0.getCovarianceMatrix()));
//					track0.getKFUpdate(measurement);
//					System.out.println("Last KF_Up  SM   : " + Arrays.deepToString(track0.getStateVector()));
////					System.out.println("Last KF_Up  CV   : " + Arrays.deepToString(track0.getCovarianceMatrix()));
////					System.out.println("meas : " + Arrays.deepToString(measurement));
////					System.out.println("SV   : " + Arrays.deepToString(track0.getStateVector()));
				}else {				
					System.out.println("   " + workStep + ".Step waiting to Kalman. Sniff Meas and calculating initStateVector");
				}
				// Kalman Stop //
			
				
			} else if (FilterType == "ExtendedKalmanFilter") {
				// --- Create Measurement Start ---//
				measurementWithTime = createMeasurementWithTime.measurementGlobal(currentTime);			// with time	"Global Coordinate Measurement"
				measurementTime = (double) measurementWithTime.get(0); //(double[][]) measurementGlobal.get(0);
				measurement = (double[][]) measurementWithTime.get(1);    //(double[][]) measurementGlobal.get(1);
				deltaT = measurementTime - deltaT;
				// --- Create Measurement Stop ---//
				
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
					track1.EKFDoit(deltaT, measurement);
					System.out.println("aft EKF    SM    : " + Arrays.deepToString(track1.getStateVector()));
					
//					System.out.println("Measurement      : " + Arrays.deepToString(measurement));
//					System.out.println("bfr EKF_Pre SM    : " + Arrays.deepToString(track1.getStateVector()));
////					System.out.println("bfr EKF_Pre CV    : " + Arrays.deepToString(track1.getCovarianceMatrix()));
//					track1.getEKFPredicted(deltaT);
////					System.out.println("bfr EKF_Middle SM : " + Arrays.deepToString(track1.getStateVector()));
////					System.out.println("bfr EKF_Middle CV : " + Arrays.deepToString(track1.getCovarianceMatrix()));
//					track1.getEKFUpdate(measurement);
//					System.out.println("Last EKF_Up  SM   : " + Arrays.deepToString(track1.getStateVector()));
////					System.out.println("Last EKF_Up  CV   : " + Arrays.deepToString(track1.getCovarianceMatrix()));
////					System.out.println("meas : " + Arrays.deepToString(measurement));
////					System.out.println("SV   : " + Arrays.deepToString(track0.getStateVector()));
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