package kf.init;

import java.util.List;

public class KFInitManager {
	
	KF_SniffMeasurement kFInitationWithSnifMeas = new KF_SniffMeasurement();
	KF_RandomAssing kFInitationWithRandom = new KF_RandomAssing();
	KF_BatchEst kFInitationWithBatchEst = new KF_BatchEst();	
	
	private double[][] measurement;
	private double measurementTime;
	private  List<double[][]>  XandPmatrices;
	private boolean validity=false;
	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	
	public KFInitManager() {}
	
	public double[][] getStateVector() {return this.stateVector;};
	public double[][] getCovarianceMatrix() {return  this.covarianceMatrix;};
	public boolean getValidity() { return this.validity;}; 
	
	public boolean initManager(String selectionType, double[][] measurement, double measurementTime) {
		
		System.out.println("KFInitManager.initManager() start ");
		
		this.measurement = measurement;
		this.measurementTime = measurementTime;
		
		if (selectionType == "SniffMeasurement") {
			sniffMeasurement();
			System.out.println("SniffMeasurement ici ");
		}else if (selectionType == "RandomAssing") {
			randomAssing();
			System.out.println("SniffMeasurement ici ");
		}else if (selectionType == "BatchEst") {
			batchEst();
			System.out.println("BatchEst ici ");
		}else {
			System.err.println("ERROR : Select initation type form list: SniffMeasurement - RandomAssing - BatchEst ");
		}
		
		return this.validity;
	}
	
	public List<double[][]> getXandPmatrices() {return this.XandPmatrices;};
	
	private void sniffMeasurement() {
		if (kFInitationWithSnifMeas.getMainKFInitation(this.measurement , this.measurementTime)) {
			this.stateVector = kFInitationWithSnifMeas.getStateVector();
			this.covarianceMatrix = kFInitationWithSnifMeas.getCovarianceMatrix();
			this.validity = true;
		}else {
			this.validity=false;
		}
		
	}
	
	private void randomAssing() {
		if (kFInitationWithRandom.getMainKFInitation(this.measurement , this.measurementTime) ) {
			this.stateVector = kFInitationWithRandom.getStateVector();
			this.covarianceMatrix = kFInitationWithRandom.getCovarianceMatrix();
			this.validity = true;
		}else {
			this.validity=false;
		}
	}
	
	private void batchEst() {
		if (kFInitationWithBatchEst.getMainKFInitation(this.measurement , this.measurementTime) ) {
			this.stateVector = kFInitationWithBatchEst.getStateVector();
			this.covarianceMatrix = kFInitationWithBatchEst.getCovarianceMatrix();
			this.validity = true;
		}else {
			this.validity=false;
		}		
	}
	

}
