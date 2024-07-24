package kf.utils;
import utils.KFConstant;

public class KFStartingParam {
	
	public final static double posX = 0;
	public final static double posY = 7.3;
	public final static double posZ = 8.7;
	public final static double speedX = 1.79;
	public final static double speedY = 2.89; 
	public final static double speedZ = 2.5;
	public final static double accelerationX = 0.1;
	public final static double accelerationY = 0.65;
	public final static double accelerationZ = 1;
	
	
	public static double[] track0StateVector =  {posX, posY, posZ, speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ};  
//	public static double[] track0StateVector =  {posX, posY, speedX, speedY, accelerationX, accelerationY};
//	public static double[] track0StateVector = {posX, speedX, accelerationX };
	
	
//	if (dimension == 3) {
//		track0StateVector = {posX, posY, posZ, speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ};  // 3D
//	}else if (dimension == 2) {
//		track0StateVector = {posX, posY, speedX, speedY, accelerationX, accelerationY};  //2D
//	}else {
//		track0StateVector = {posX, speedX, accelerationX };  //1D
//	}
	
	

}