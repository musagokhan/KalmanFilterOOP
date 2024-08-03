package ekf.init;

import java.util.List;

public class EKFInitManager {
	
	EKF_SniffMeasurement ekFInitationWithSnifMeas = new EKF_SniffMeasurement();
	EKF_RandomAssing ekFInitationWithRandom = new EKF_RandomAssing();
	EKF_BatchEst ekFInitationWithBatchEst = new EKF_BatchEst();	
	
	private double[][] measurement;
	private double measurementTime;
	private  List<double[][]>  XandPmatrices;
	private boolean validity=false;
	
	private double[][] stateVector;
	private double[][] covarianceMatrix;
	
	public EKFInitManager() {}
	
	public double[][] getStateVector() {return this.stateVector;};
	public double[][] getCovarianceMatrix() {return  this.covarianceMatrix;};
	public boolean getValidity() { return this.validity;}; 
	
	public boolean initManager(String selectionType, double[][] measurement, double measurementTime) {
		
//		System.out.println("eKFInitManager.initManager() start ");
		
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
		if (ekFInitationWithSnifMeas.getMainEKFInitation(this.measurement , this.measurementTime)) {
			this.stateVector = ekFInitationWithSnifMeas.getStateVector();
			this.covarianceMatrix = ekFInitationWithSnifMeas.getCovarianceMatrix();
			this.validity = true;
		}else {
			this.validity=false;
		}
		
	}
	
	private void randomAssing() {
		if (ekFInitationWithRandom.getMainEKFInitation(this.measurement , this.measurementTime) ) {
			this.stateVector = ekFInitationWithRandom.getStateVector();
			this.covarianceMatrix = ekFInitationWithRandom.getCovarianceMatrix();
			this.validity = true;
		}else {
			this.validity=false;
		}
	}
	
	private void batchEst() {
		if (ekFInitationWithBatchEst.getMainEKFInitation(this.measurement , this.measurementTime) ) {
			this.stateVector = ekFInitationWithBatchEst.getStateVector();
			this.covarianceMatrix = ekFInitationWithBatchEst.getCovarianceMatrix();
			this.validity = true;
		}else {
			this.validity=false;
		}		
	}
	

}
