import Domain.KF;
import kf.KFInitation;
import sensor.CreateMeasurementWithTime;
import sensor.CreateTimeBrand;
import kf.utils.KFStartingParam;
import kf.utils.*;

public class MainOperation {

	public static void main(String[] args) {
		System.out.println("--- Kalman Filter Start ---");
	
		CreateTimeBrand createTimeBrand = new CreateTimeBrand();
		CreateMeasurementWithTime createMeasurementWithTime = new CreateMeasurementWithTime();
		KFInitation kFInitation = new KFInitation();
		
		boolean firstStepFlag = true;
		KF track0 = null; // nesne ilk tanımı. ataması için gerekli olan girdi asagida ilk kez uretilecek o zamn kullanacagim.
		
		for (int workStep=0; workStep < KFConstant.lastWorkStep ; workStep++) {
			
			double currentTime = createTimeBrand.getTimeCalculation(); // random time
			double[] measurementCartesian = createMeasurementWithTime.measurementCartesian(currentTime);   // with time	
//			double[] measurementGlobal = createMeasurementWithTime.measurementGlobal(currentTime);   // with time	
			double[] kFInitationStatus = kFInitation.getMainKFInitation(measurementCartesian);
			
			if (kFInitationStatus != null) {
				System.out.println(workStep + ".Adim  kalman baslasin artik");
				if (firstStepFlag) {// ilk adim nesne olustur.
					firstStepFlag = false;
					track0 = new KF(kFInitationStatus); // icerige eriistigim icin burada koydum.
				}
				track0.getKFPredicted(1);
			} else {				
				System.out.println(workStep + ".Adim  kalman'a var");
			}
			
		}
		

		
	
//		double[] track0StateVector = KFStartingParam.track0StateVector;
		
		
		
//		System.out.println("init track0 : " + track0);
//
//		double[] track1StateVector = {posX+1, posY-1, posZ+10, speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ};
//		KF track1 = new KF(track1StateVector);
//		System.out.println("init track1 : " + track1);
//
//		for (double i=0 ; i<10 ; i++){
//			track0.getKFPredicted(i);
//			System.out.println("Step " + i + " - pred track0 : " + track0);
//			track1.getKFPredicted(i);
//		}
//		
		
		
		
		
		
		System.out.println("--- Kalman Filter Stop ---");
	}
	
	
	
//	private void measurementManagament (double[] currentMeasurement) {
//		int measuremetCounter = 0;
//		boolean kfStatus  = false;
//		
//		if (measuremetCounter > 2 ) {
//			
//		} else
//		
//	}

}
