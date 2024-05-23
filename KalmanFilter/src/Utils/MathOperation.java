package Utils;

public class MathOperation {
	
	private MathOperation() {}
	
	private static double[] stateVectorLastStepCalculator(double[] positionList, double[] speedList, double[] accelerationList, double deltaT) {
		
		//List<Double> newStateVector = Arrays.asList(null, null, null, null, null, null);
		double[] newStateVector = new double[positionList.length * 3];
		
		for (int step = 0; step < positionList.length ; step++) {
			newStateVector[step] = positionList.length + speedList[step] * deltaT + 0.5 * accelerationList[step] * deltaT * deltaT;
			newStateVector[step + 3] = speedList.length + accelerationList[step] * deltaT;   
			newStateVector[step + 6] = accelerationList[step];
		}
		
		return newStateVector;
	}
	
	public static double[] getstateVectorLastStepCalculator(double[] positionList, double[] speedList, double[] accelerationList, double deltaT) {
		return stateVectorLastStepCalculator(positionList, speedList, accelerationList, deltaT);
	}
	
}
