package execute;

import execute.utils.*;
import sensor.MeasurementManagement;
import kf.init.KFInitManager;
import ekf.init.EKFInitManager;


public class MainOperation {
	

	public static void main(String[] args) {

		System.out.println("--- WORK Start ---");	
		
		MeasurementManagement measurementDatas = new MeasurementManagement();
		RunKF runKF = new RunKF();
		RunEKF runEKF = new RunEKF();

		KFInitManager kfInitManager = new KFInitManager();
		EKFInitManager ekfInitManager = new EKFInitManager();

		for (int workStep=0; workStep < ExecuterConstants.lastWorkStep ; workStep++) {
			System.out.println(workStep + ". Adim");
			//runKF.runKF(measurementDatas, kfInitManager);
			runEKF.runEKF(measurementDatas, ekfInitManager);
		}	
		
		System.out.println("--- WORK Stop ---");
	}
	
	// TODO : MAP gibi bir formda SV ve CM tut.
	

}