package execute;
import java.util.Arrays;
import java.util.List;

import kf.KF;
import kf.utils.*;
import kf.init.KF_SniffMeasurement;
import ekf.init.EKF_SniffMeasurement;

import sensor.CreateMeasurementWithTime;
import sensor.CreateTimeBrand;


public class MainOperation {

	public static void main(String[] args) {

		System.out.println("--- WORK Start ---");	
		CreateTimeBrand createTimeBrand = new CreateTimeBrand();
		CreateMeasurementWithTime createMeasurementWithTime = new CreateMeasurementWithTime();
		String FilterType = "KalmanFilter"; //  "ExtendedKalmanFilter"; //   
		
		KF track0 = null; 
		boolean firstStepFlag = true;
		double deltaT = 0.0;
		List<Object> measurementWithTime ;
		
		KF_SniffMeasurement kFInitationWithSnifMeas = new KF_SniffMeasurement();
//		KF_RandomAssing kFInitationWithRandom = new KF_RandomAssing();
//		KF_BatchEst kFInitationWithBatchEst = new KF_BatchEst();	
		
		EKF_SniffMeasurement ekFInitationWithSnifMeas = new EKF_SniffMeasurement(); //31072024
//		EKF_RandomAssing ekFInitationWithRandom = new EKF_RandomAssing();
//		EKF_BatchEst ekFInitationWithBatchEst = new EKF_BatchEst();
		
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

				List<double[][]> kFInitationStatus = kFInitationWithSnifMeas.getMainKFInitation(measurement , measurementTime);
//				List<double[][]> kFInitationStatus = kFInitationWithRandom.getMainKFInitation(measurement , measurementTime);
//				List<double[][]> kFInitationStatus = kFInitationWithBatchEst.getMainKFInitation(measurement, measurementTime);
				// --- KF init Assing Stop ---//	
				
				
				
				// Kalman Start //
				if (kFInitationStatus.get(0) != null) {
					System.out.println("   " + workStep + ".Step Kalman phase");
					if (firstStepFlag) {// ilk adim nesne olustur.
						firstStepFlag = false;
						track0 = new KF(kFInitationStatus, deltaT); 
					}	
					System.out.println("Measurement      : " + Arrays.deepToString(measurement));
					System.out.println("bfr KF_Pre SM    : " + Arrays.deepToString(track0.getStateVector()));
//					System.out.println("bfr KF_Pre CV    : " + Arrays.deepToString(track0.getCovarianceMatrix()));
					track0.getKFPredicted(deltaT);
//					System.out.println("bfr KF_Middle SM : " + Arrays.deepToString(track0.getStateVector()));
//					System.out.println("bfr KF_Middle CV : " + Arrays.deepToString(track0.getCovarianceMatrix()));
					track0.getKFUpdate(measurement);
					System.out.println("Last KF_Up  SM   : " + Arrays.deepToString(track0.getStateVector()));
//					System.out.println("Last KF_Up  CV   : " + Arrays.deepToString(track0.getCovarianceMatrix()));
//					System.out.println("meas : " + Arrays.deepToString(measurement));
//					System.out.println("SV   : " + Arrays.deepToString(track0.getStateVector()));
				}else {				
					System.out.println("   " + workStep + ".Step waiting to Kalman. Sniff Meas and calculating initStateVector */*/*");
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

				List<double[][]> ekFInitationStatus = ekFInitationWithSnifMeas.getMainKFInitation(measurement , measurementTime);
//				List<double[][]> ekFInitationStatus = ekFInitationWithRandom.getMainKFInitation(measurement , measurementTime);
//				List<double[][]> ekFInitationStatus = ekFInitationWithBatchEst.getMainKFInitation(measurement, measurementTime);
				// --- EKF init Assing Stop ---//	
				
				
				
				// Kalman Start //
				if (ekFInitationStatus.get(0) != null) {
					System.out.println("   " + workStep + ".Step EKalman phase");
					if (firstStepFlag) {// ilk adim nesne olustur.
						firstStepFlag = false;
						track0 = new KF(ekFInitationStatus, deltaT); 
					}	
					System.out.println("Measurement      : " + Arrays.deepToString(measurement));
					System.out.println("bfr EKF_Pre SM    : " + Arrays.deepToString(track0.getStateVector()));
//					System.out.println("bfr EKF_Pre CV    : " + Arrays.deepToString(track0.getCovarianceMatrix()));
					track0.getKFPredicted(deltaT);
//					System.out.println("bfr EKF_Middle SM : " + Arrays.deepToString(track0.getStateVector()));
//					System.out.println("bfr EKF_Middle CV : " + Arrays.deepToString(track0.getCovarianceMatrix()));
					track0.getKFUpdate(measurement);
					System.out.println("Last EKF_Up  SM   : " + Arrays.deepToString(track0.getStateVector()));
//					System.out.println("Last EKF_Up  CV   : " + Arrays.deepToString(track0.getCovarianceMatrix()));
//					System.out.println("meas : " + Arrays.deepToString(measurement));
//					System.out.println("SV   : " + Arrays.deepToString(track0.getStateVector()));
				}else {				
						System.out.println("   " + workStep + ".Step waiting to Kalman. Sniff Meas and calculating initStateVector");
				}
				// Kalman Stop //
			}
			
		}	
		
		System.out.println("--- WORK Stop ---");
	}
	
	// TODO : MAP gibi bir formda SV ve CM tut.
	

}
