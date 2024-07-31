package ekf.converters;

public class CoordinateSystemConvert {
	
	
	private CoordinateSystemConvert() {}
	
	public static double[] cartesianToGlabal (double[] cartesian) {
		
		double x = cartesian[0];
		double y = cartesian[1];
		double z = cartesian[2];
		
		double r     = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
		double theta = Math.atan2(y, x);   // radyan
		double phi   = Math.acos(z / r);   // radyan
		
		return new double[] { r, theta, phi };
	}
	
	
	public static double[] glabalToCartesian (double[] glabal) {
		
		double r     = glabal[0];
		double theta = glabal[1];
		double phi   = glabal[2];
		
		double x = r * Math.sin(phi) * Math.cos(theta);
		double y = r * Math.sin(phi) * Math.sin(theta);
		double z = r * Math.cos(phi); 
		
		return new double[] { x, y, z };
	}

}
