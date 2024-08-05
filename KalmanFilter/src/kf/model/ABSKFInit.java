package kf.model;


public abstract class ABSKFInit{
	
    
    protected abstract boolean mainKFInitation(double[][] currentMeasurement, double currentMeasurementTime);
    protected abstract void stateVectorEstimate ();
    protected abstract void covarianceMatrixEstimate();
	

}
