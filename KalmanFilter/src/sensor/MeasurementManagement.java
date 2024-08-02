package sensor;

import java.util.List;

import sensor.utils.MeasurementParameters;


public class MeasurementManagement {
	
	CreateTimeBrand createTimeBrand = new CreateTimeBrand();
	CreateMeasurementWithTime createMeasurementWithTime = new CreateMeasurementWithTime();
	
	private double measurementTime;
	private double[][] measurement;
	private double deltaT = 0.0;
	private double[][] measurementCovariance; //Rmatrix // give it with every Meas.
	private List<Object> measurementWithTime;
	private double currentTime; 
	
	public MeasurementManagement() {}
	
	
	
	public void createMeasurementCartesian(int step) {
		
		this.currentTime = createTimeBrand.getTimeCalculation(); 
		// --- Create Measurement Start ---//
		this.measurementWithTime = createMeasurementWithTime.measurementCartesian(this.currentTime);	// with time	"Cartesian Coordinate Measurement"
		this.measurementTime = (double) measurementWithTime.get(0); //(double[][]) measurementGlobal.get(0);
		this.measurement = (double[][]) measurementWithTime.get(1);    //(double[][]) measurementGlobal.get(1);	
		
		this.deltaT = this.measurementTime - this.deltaT;			
		
		this.measurementCovariance = MeasurementParameters.RMatrixCartesian();
		
		
		// --- Create Measurement Stop ---//		
	}
	
	
	public void createMeasurementGlobal(int step) {
		
		this.currentTime = createTimeBrand.getTimeCalculation(); 
		// --- Create Measurement Start ---//
		this.measurementWithTime = createMeasurementWithTime.measurementGlobal(this.currentTime);	// with time	"Cartesian Coordinate Measurement"
		this.measurementTime = (double) measurementWithTime.get(0); //(double[][]) measurementGlobal.get(0);
		this.measurement = (double[][]) measurementWithTime.get(1);    //(double[][]) measurementGlobal.get(1);	
		
		this.deltaT = this.measurementTime - this.deltaT;			
		
		this.measurementCovariance = MeasurementParameters.RMatrixCartesian();
		
		
		// --- Create Measurement Stop ---//		
	}
	
	

	public double getMeasurementTime() {
		return measurementTime;
	}

	public double[][] getMeasurement() {
		return measurement;
	}

	public double[][] getMeasurementCovariance() {
		return measurementCovariance;
	}
	
	

	

}
