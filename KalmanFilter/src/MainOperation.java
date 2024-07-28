import java.util.Arrays;
import java.util.List;

import kf.KF;
import kf.KFInitation;
import sensor.CreateMeasurementWithTime;
import sensor.CreateTimeBrand;
import kf.utils.*;

public class MainOperation {

	public static void main(String[] args) {

		System.out.println("--- WORK Start ---");	
		CreateTimeBrand createTimeBrand = new CreateTimeBrand();
		CreateMeasurementWithTime createMeasurementWithTime = new CreateMeasurementWithTime();
		KFInitation kFInitation = new KFInitation();
		KF track0 = null; 
		boolean firstStepFlag = true;
		double deltaT = 0.0;
		
		
		for (int workStep=0; workStep < KFConstant.lastWorkStep ; workStep++) {
			// --- Create Measurement Start ---//
			
			double currentTime = createTimeBrand.getTimeCalculation(); // random time
			
			// TEST
//			double currentTime = workStep; // random time
			
			
			List<Object> measurementCartesian = createMeasurementWithTime.measurementCartesian(currentTime);	// with time	"Cartesian Coordinate Measurement"
			//List<Object> measurementGlobal = createMeasurementWithTime.measurementGlobal(currentTime);			// with time	"Global Coordinate Measurement"
			double measurementTime = (double) measurementCartesian.get(0); //(double[][]) measurementGlobal.get(0);
			double[][] measurement = (double[][]) measurementCartesian.get(1);    //(double[][]) measurementGlobal.get(1);			
			// --- Create Measurement Stop ---//
			
			
			// !!!!!!!!!! TODO !!!!!!!!!!!!! : 3 tip secim var. a)Random - b)OlcumDinleme c)Batch  sadeceb yaptım digerlerinide yapmaliyim. 1Interface 3sub-class tasarla
			List<double[][]> kFInitationStatus = kFInitation.getMainKFInitation(measurement , measurementTime);
			
//			System.out.println("init SM    : " + Arrays.deepToString(kFInitationStatus.get(0)));
//			System.out.println("init CV    : " + Arrays.deepToString(kFInitationStatus.get(1)));
			
			// Kalman Start //
			deltaT = measurementTime - deltaT;
			if (kFInitationStatus.get(0) != null) {
				System.out.println("   " + workStep + ".Step Kalman phase");
				if (firstStepFlag) {// ilk adim nesne olustur.
					firstStepFlag = false;
					track0 = new KF(kFInitationStatus, deltaT); 
				}				
				System.out.println("bfr KF_Pre SM    : " + Arrays.deepToString(track0.getStateVector()));
//				System.out.println("bfr KF_Pre CV    : " + Arrays.deepToString(track0.getCovarianceMatrix()));
				track0.getKFPredicted(deltaT);
//				System.out.println("bfr KF_Middle SM : " + Arrays.deepToString(track0.getStateVector()));
//				System.out.println("bfr KF_Middle CV : " + Arrays.deepToString(track0.getCovarianceMatrix()));
				track0.getKFUpdate(measurement);
				System.out.println("Last KF_Up  SM   : " + Arrays.deepToString(track0.getStateVector()));
//				System.out.println("Last KF_Up  CV   : " + Arrays.deepToString(track0.getCovarianceMatrix()));
//				System.out.println("meas : " + Arrays.deepToString(measurement));
//				System.out.println("SV   : " + Arrays.deepToString(track0.getStateVector()));
				
			} else {				
				System.out.println("   " + workStep + ".Step waiting to Kalman. Sniff Meas and calculating initStateVector");
			}
			// Kalman Stop //

			
		}	
		
		System.out.println("--- WORK Stop ---");
	}
	

}
