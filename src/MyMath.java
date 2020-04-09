import java.util.ArrayList;

//used if we want to have custom math operations

public class MyMath {
	public static double cosDegrees(double x) {
		//returns cos(x) where x is in degrees
		return Math.cos(x * (Math.PI / 180));
	}
	
	public static double sinDegrees(double x) {
		//returns cos(x) where x is in degrees
		return Math.sin(x * (Math.PI / 180));
	}
	
	public static double secDegrees(double x) {
		//returns sec(x) where x is in degrees
		return 1 / Math.cos(x * (Math.PI / 180));
	}
	
	static double integrate(ArrayList<Double> a, ArrayList<Double> b) {
		double total = 0;
		for (int i = 0; i < a.size() - 1; i++) {
			double trap = ((a.get(i+1) + a.get(i)) / 2) * (b.get(i+1) - b.get(i));
			total += trap;
		}
		
		return total;
	}
	
	public static ArrayList<Double> linspace(double start, double end, int slices) {
		ArrayList<Double> retVal = new ArrayList<Double>();
		double db_slices = (double) slices; //convert to double to avoid weird rounding errors
		
		double increment = (end - start) / (db_slices - 1);
		
		retVal.add(start);
		for (int i = 1; i < slices; i++) {
			retVal.add(start + increment * (i));
		}
		
		return retVal;
	}
	
	public static double convertToMeters(double x) {
		return x / 39.37;
	}
}
