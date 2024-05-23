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
		
		double[] track0StateVector = {posX, posY, posZ, speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ};
		KF track0 = new KF(track0StateVector);
		System.out.println("init track0 : " + track0);

		double[] track1StateVector = {posX+1, posY-1, posZ+10, speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ};
		KF track1 = new KF(track1StateVector);
		System.out.println("init track1 : " + track1);

		for (double i=0 ; i<10 ; i++){
			track0.getKFPredicted(i);
			System.out.println("Step " + i + " - pred track0 : " + track0);
			track1.getKFPredicted(i);
		}
		
		
		
		
		
		
		System.out.println("--- Kalman Filter Stop ---");
	}

}
