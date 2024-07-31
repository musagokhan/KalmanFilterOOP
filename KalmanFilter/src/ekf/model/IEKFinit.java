package ekf.model;

import java.util.List;

public interface IEKFinit {
	
	public List<double[][]> getMainKFInitation (double[][] currentMeasurement, double currentMeasurementTime); 

	// Constant for initialize
	public static final double gapPercet = 0.1; 
	public static final double XminSpeed = 0.0; 
	public static final double XmaxSpeed = 10.0; 
	public static final double YminSpeed = 0.0; 
	public static final double YmaxSpeed = 10.0; 
	public static final double ZminSpeed = 0.0; 
	public static final double ZmaxSpeed = 10.0; 
	
	public static final double XmaxAcceleration = 0.0010;
	public static final double XminAcceleration = 0.0001; 
	public static final double YmaxAcceleration = 0.0010;
	public static final double YminAcceleration = 0.0001;
	public static final double ZmaxAcceleration = 0.0010;
	public static final double ZminAcceleration = 0.0001;
	

}
