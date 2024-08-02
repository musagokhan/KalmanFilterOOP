package kf.init;

import java.util.List;

public class KFInitManager {
	
	KF_SniffMeasurement kFInitationWithSnifMeas = new KF_SniffMeasurement();
	KF_RandomAssing kFInitationWithRandom = new KF_RandomAssing();
	KF_BatchEst kFInitationWithBatchEst = new KF_BatchEst();	
	
	private double[][] measurement;
	private double measurementTime;
	private  List<double[][]>  XandPmatrices;
	
	public KFInitManager() {}
	
	
	public List<double[][]>  initManager(String selectionType, double[][] measurement, double measurementTime) {
		this.measurement = measurement;
		this.measurementTime = measurementTime;
		
		if (selectionType == "SniffMeasurement") {
			sniffMeasurement();
		}else if (selectionType == "RandomAssing") {
			sniffMeasurement();
		}else if (selectionType == "BatchEst") {
			sniffMeasurement();
		}else {
			System.err.println("ERROR : Select initation type form list: SniffMeasurement - RandomAssing - BatchEst ");
		}
		
		return XandPmatrices;
	}
	
	private void sniffMeasurement() {
		this.XandPmatrices = kFInitationWithSnifMeas.getMainKFInitation(this.measurement , this.measurementTime);
	}
	
	private void randomAssing() {
		this.XandPmatrices = kFInitationWithRandom.getMainKFInitation(this.measurement , this.measurementTime);
	}
	
	private void batchEst() {
		this.XandPmatrices = kFInitationWithBatchEst.getMainKFInitation(this.measurement , this.measurementTime);
	}
	

}
