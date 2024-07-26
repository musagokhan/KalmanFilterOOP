import java.util.Arrays;
import java.util.List;

import kf.KF;
import kf.KFInitation;
import sensor.CreateMeasurementWithTime;
import sensor.CreateTimeBrand;
import kf.utils.*;

public class MainOperation {

	public static void main(String[] args) {
		System.out.println("--- Kalman Filter Start ---");
	
		CreateTimeBrand createTimeBrand = new CreateTimeBrand();
		CreateMeasurementWithTime createMeasurementWithTime = new CreateMeasurementWithTime();
		KFInitation kFInitation = new KFInitation();
		
		boolean firstStepFlag = true;
		KF track0 = null; // nesne ilk tanımı. ataması için gerekli olan girdi asagida ilk kez uretilecek o zamn kullanacagim.
		
//		double[] track0 = null;
		
//		double[][] m1 = new double[][] {{1,2}};
//		double[][] m2 = new double[][] {{11,2}, {17,20}};
//		System.out.println( Arrays.deepToString( MathOperation.getAddMatrices(m1, m2)) );
		
		for (int workStep=0; workStep < KFConstant.lastWorkStep ; workStep++) {
			
			double currentTime = createTimeBrand.getTimeCalculation(); // random time
			double[] measurementCartesian = createMeasurementWithTime.measurementCartesian(currentTime);	// with time	"Cartesian Coordinate Measurement"
//			double[] measurementGlobal = createMeasurementWithTime.measurementGlobal(currentTime);   		// with time	"Global Coordinate Measurement"
			List<double[][]> kFInitationStatus = kFInitation.getMainKFInitation(measurementCartesian);
			
			if (kFInitationStatus.get(0) != null) {
				System.out.println("   " + workStep + ".Step Kalman phase");
				if (firstStepFlag) {// ilk adim nesne olustur.
					firstStepFlag = false;
					track0 = new KF(kFInitationStatus, 1); // icerige eriistigim icin burada koydum. delta=1
				}
				
//				System.out.println("Main: SV Bfr KF Pre    : " +  Arrays.deepToString(track0.getStateVector()));
//				System.out.println("Main: CM Bfr KF Pre    : " +  Arrays.deepToString(track0.getCovarianceMatrix()));
				track0.getKFPredicted(1);
//				System.out.println("Main: SV Aft KF Pre    : " +  Arrays.deepToString(track0.getStateVector()));
//				System.out.println("Main: CM Aft KF Pre    : " +  Arrays.deepToString(track0.getCovarianceMatrix()));
				

			} else {				
				System.out.println("   " + workStep + ".Step waiting to Kalman. Sniff Meas and calculating initStateVector");
			}
			
		}	
		
		System.out.println("--- Kalman Filter Stop ---");
	}
	

}
