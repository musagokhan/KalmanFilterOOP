package kf.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kf.model.IKFinit;
import kf.utils.KFConstant;

public class KF_RandomAssing implements  IKFinit{
	
	private int dimension;
	private double XcoorEstimate;
	private double YcoorEstimate;
	private double ZcoorEstimate;
	
	private double XspeedEstimate;
	private double YspeedEstimate;
	private double ZspeedEstimate;
	
	private double XaccelerationEstimate;
	private double YaccelerationEstimate;
	private double ZaccelerationEstimate;
	
	private double XcoorMeasurement;
	private double YcoorMeasurement;
	private double ZcoorMeasurement;
	private double[][] initstateVectoreDatas; 
	private double[][] initCovarianceDatas;
	
	//List<double[][]> XandPmatrices = new ArrayList<>();
	
	private void XCoordinateEstimateCalculation(){
		Random random = new Random();
		this.XcoorEstimate = (this.XcoorMeasurement * (1 + gapPercet) - this.XcoorMeasurement * (1- gapPercet)) * random.nextDouble();
	}
	
	private void YCoordinateEstimateCalculation(){
		Random random = new Random();
		this.YcoorEstimate = (this.YcoorMeasurement * (1 + gapPercet) - this.YcoorMeasurement * (1- gapPercet)) * random.nextDouble();
	}
	
	private void ZCoordinateEstimateCalculation(){
		Random random = new Random();
		this.ZcoorEstimate = (this.ZcoorMeasurement * (1 + gapPercet) - this.ZcoorMeasurement * (1- gapPercet)) * random.nextDouble();
	}
	
	private void XSpeedEstimateCalculation(){
		Random random = new Random();
		this.XspeedEstimate = (XmaxSpeed - XminSpeed) * random.nextDouble();
	}
	
	private void YSpeedEstimateCalculation(){
		Random random = new Random();
		this.YspeedEstimate = (YmaxSpeed - YminSpeed) * random.nextDouble();
	}
	
	private void ZSpeedEstimateCalculation(){
		Random random = new Random();
		this.ZspeedEstimate = (ZmaxSpeed - ZminSpeed) * random.nextDouble();
	}
	
	private void XAccelerationEstimateCalculation(){
		Random random = new Random();
		this.XaccelerationEstimate = (XmaxAcceleration - XminAcceleration) * random.nextDouble();
	}
	
	private void YAccelerationEstimateCalculation(){
		Random random = new Random();
		this.YaccelerationEstimate = (YmaxAcceleration - YminAcceleration) * random.nextDouble();
	}
	
	private void ZAccelerationEstimateCalculation(){
		Random random = new Random();
		this.ZaccelerationEstimate = (ZmaxAcceleration - ZminAcceleration) * random.nextDouble();
	}
	
	public KF_RandomAssing() {}
	
	
	private void stateVectorEstimate () {
		
		if (this.dimension == 3) {
			XCoordinateEstimateCalculation();
			YCoordinateEstimateCalculation();
			ZCoordinateEstimateCalculation();
			XSpeedEstimateCalculation();
			YSpeedEstimateCalculation();
			ZSpeedEstimateCalculation();
		}else if (this.dimension == 2) {
			XCoordinateEstimateCalculation();
			YCoordinateEstimateCalculation();
			XSpeedEstimateCalculation();
			YSpeedEstimateCalculation();
		}else if (this.dimension == 1) {
			XCoordinateEstimateCalculation();
			XSpeedEstimateCalculation();
		} else {
			System.err.println("Check Your Measurement Datas. Measurament=X or Measurament=X,Y or Measurament=X,Y,Z");
		}

		this.initstateVectoreDatas = new double[][] { {this.XcoorEstimate}, {this.XspeedEstimate}, {this.XaccelerationEstimate},
													  {this.YcoorEstimate}, {this.YspeedEstimate}, {this.YaccelerationEstimate}, 
													  {this.ZcoorEstimate}, {this.ZspeedEstimate}, {this.ZaccelerationEstimate} };
	}
	
	private void covarianceMatrixEstimate (){
		int CMlength = this.dimension * KFConstant.diffParametersNumberInStateVector;
		this.initCovarianceDatas = new double[CMlength][CMlength];
		for (int i = 0; i < CMlength; i++) {
			this.initCovarianceDatas[i][i] = 1;
		}
	}
	
		
	@Override
	public void getMainKFInitation(double[][] currentMeasurement, double currentMeasurementTime) {
		this.dimension = currentMeasurement.length;		
		this.XcoorMeasurement = currentMeasurement[0][0];
		this.YcoorMeasurement = currentMeasurement[1][0];
		this.ZcoorMeasurement = currentMeasurement[2][0];
		stateVectorEstimate(); 
		covarianceMatrixEstimate();
	}
	
	public double[][] getStateVector() {return this.initstateVectoreDatas;};
	
	public double[][] getCovarianceMatrix() {return  this.initCovarianceDatas;};
	
}
