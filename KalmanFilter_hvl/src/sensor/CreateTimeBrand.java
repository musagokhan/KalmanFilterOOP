package sensor;
import java.util.Random;

public class CreateTimeBrand {
	
	private double time ;
	private double randomStart = 0;
	private double randomStop  = 1;
	private double timeGenerateSafety = 0.01;
	
	public CreateTimeBrand() {}
	
	private void timeCalculation() {
		Random random = new Random();
		this.time = this.randomStart + (this.randomStop - this.randomStart) * random.nextDouble();
		this.randomStart = Math.ceil(this.time);// + timeGenerateSafety;
		this.randomStop = this.randomStart + 1;
	}
	
	public double getTimeCalculation() {
		timeCalculation();
		return this.time;
	}
	
}


/*
 * Burada amaç : önceki üretilenin bir üst integer ailesi aralýðýnda zaman üretmektir. 
 * 
*/

