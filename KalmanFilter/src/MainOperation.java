import Domain.KF;

public class MainOperation {

	public static void main(String[] args) {
		System.out.println("--- Kalman Filter Start ---");
		
		double posX = 0;
		double posY = 7.3;
		double posZ = 8.7;
		double speedX = 1.79;
		double speedY = 2.89; 
		double speedZ = 2.5;
		double accelerationX = 0.1;
		double accelerationY = 0.65;
		double accelerationZ = 1;
		
		double[] newStateVector = {posX, posY, posZ, speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ};
		
		KF track = new KF(newStateVector);
		System.out.println("init track : " + track);
		
		track.getKFPredicted(3.0);
		System.out.println("pred track : " + track);
		
		
		System.out.println("--- Kalman Filter Stop ---");
	}

}
