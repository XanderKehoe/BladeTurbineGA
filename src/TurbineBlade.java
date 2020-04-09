import java.util.ArrayList;

public class TurbineBlade {
	double diameter;
	double root_coord;
	double tip_coord;
	int number_of_blades;
	double root_angle;
	double tip_angle;
	
	final double density = 1.225f;
	final double hub_diameter = 0.4f / 39.37f; //change this value here to how see fit
	final double b = .02;
	final double rpm = 19000;
	
	double this_fitness;
	
	ArrayList<Double> r;
	
	//base fitness upon: highest thrust, least amount of torque
	
	//constructor
	public TurbineBlade(double diameter, double root_coord, double tip_coord,
				int number_of_blades, double root_angle, double tip_angle, boolean convert) {
		if (convert) {
			//convert inches to meters
			this.diameter = diameter / 39.37f;
			this.root_coord = root_coord / 39.37f;
			this.tip_coord = tip_coord / 39.37f;
		}
		else {
			this.diameter = diameter;
			this.root_coord = root_coord;
			this.tip_coord = tip_coord;
		}
		
		this.number_of_blades = number_of_blades;
		this.root_angle = root_angle;
		this.tip_angle = tip_angle;
		
		r = MyMath.linspace(0, getBladeLength(), 100);
	}

	
	private ArrayList<Double> getAngles() {
		return MyMath.linspace(root_angle, tip_angle, r.size());
	}

	public double getCalculatedFitness() {
		//intake all parameters, calculate thrust and torque with these parameters
		double thrust = getThrust();
		double torque = getRequiredTorque();
		
		double fitness;
		float someConstant1 = 10; //feel free to change these
		float someConstant2 = 1;
		
		if (torque < (.3 * 4.448) / 39.37) {
			//ideal ratio between thrust and torque?
			fitness = 1 + (thrust * someConstant1); //feel free to change this equation to how you see fit
			fitness *= fitness; //squaring fitness
			
		}
		else {
			//torque was too high, don't consider; OR JUST MAKE SUPER LOW??
			//fitness = thrust / Math.pow(1 + torque, 10);
			fitness = 0;
		}
		
		this.this_fitness = fitness;
		
		if (Double.isNaN(fitness)) {
			System.out.println("NaN Fitness: ");
			System.out.println(this.toString());
		}
		
		
		
		return fitness;
	}
	
	public double getThrust() {
		//insert calculations to get thrust here
		ArrayList<Double> thrst_grdnt = getThrstGrdnt();
		Double thrust = MyMath.integrate(thrst_grdnt, r);
		/*
		if (thrust == 0) {
			System.out.println("ZERO THRUST: ");
			System.out.println("\t"+thrst_grdnt.toString());
			System.out.println("\t"+thrust);
		}
		*/
		return thrust;
	}
	
	public double getTorque() {
		//insert calculations to get torque here
		ArrayList<Double> trq_grdnt = getTrqGrdnt();
		Double torque = MyMath.integrate(trq_grdnt, r);	
		return torque;
	}
	
	public double getRequiredTorque() {
		ArrayList<Double> required_trq_grdnt = getRequiredTrqGrdnt();
		Double required_torque = MyMath.integrate(required_trq_grdnt, r);	
		return required_torque;
	}
	
	private ArrayList<Double> getThrstGrdnt(){
		int slices = r.size();
		ArrayList<Double> coord_lengths = MyMath.linspace(root_coord, tip_coord, slices); //cross section length(s)
		ArrayList<Double> VR = getRelativeVelocity();
		ArrayList<Double> t = get_t();
		ArrayList<Double> thrst_grdnt = new ArrayList<Double>();
		
		for (int i = 0; i < slices; i++)
			thrst_grdnt.add(this.number_of_blades * coord_lengths.get(i) * 0.5 * density * Math.pow(VR.get(i), 2) * t.get(i));
		
		return thrst_grdnt;
	}
	
	private ArrayList<Double> getTrqGrdnt(){
		int slices = r.size();
		ArrayList<Double> coord_lengths = MyMath.linspace(root_coord, tip_coord, slices); //cross section length(s)
		ArrayList<Double> VR = getRelativeVelocity();
		ArrayList<Double> q = get_q();
		ArrayList<Double> trq_grdnt = new ArrayList<Double>();
		
		for (int i = 0; i < slices; i++)
			trq_grdnt.add(this.number_of_blades * coord_lengths.get(i) * 0.5 * density * Math.pow(VR.get(i), 2) * q.get(i));
		
		return trq_grdnt;
	}
	
	/*
	private ArrayList<Double> getRequiredTrqGrdnt(){
		int slices = r.size();
		ArrayList<Double> coord_lengths = MyMath.linspace(root_coord, tip_coord, slices); //cross section length(s)
		ArrayList<Double> VR = getRelativeVelocity();
		ArrayList<Double> q = get_q();
		ArrayList<Double> trq_grdnt = new ArrayList<Double>();
		
		for (int i = 0; i < slices; i++)
			trq_grdnt.add(2 * (Math.pow(Math.PI, 2) * density * CD * B (())));
		
		return trq_grdnt;
	}
	*/
	
	private ArrayList<Double> get_t() {
		ArrayList<Double> chi = getAngles();
		ArrayList<Double> ret = new ArrayList<Double>();
		
		for (int i = 0; i < r.size(); i++) {
			double CL = -.0008 * Math.pow(chi.get(i), 2) + .0724 * chi.get(i) + 1.0141;
			double CD = .091 * Math.exp(.0667 * chi.get(i));
			
			ret.add(CL * MyMath.cosDegrees(chi.get(i)) - CD * MyMath.sinDegrees(chi.get(i)));
		}
		
		return ret;
	}
	
	private ArrayList<Double> get_q() {
		ArrayList<Double> chi = getAngles();
		ArrayList<Double> ret = new ArrayList<Double>();
		
		for (int i = 0; i < r.size(); i++) {
			double CL = -.0008 * Math.pow(chi.get(i), 2) + .0724 * chi.get(i) + 1.0141;
			double CD = .091 * Math.exp(.0667 * chi.get(i));
			
			ret.add(CL * MyMath.sinDegrees(chi.get(i)) + CD * MyMath.cosDegrees(chi.get(i)));
		}
		
		return ret;
	}
	
	private ArrayList<Double> getRequiredTrqGrdnt(){
		int slices = r.size();
		ArrayList<Double> chi = getAngles();
		ArrayList<Double> ret = new ArrayList<Double>();
		ArrayList<Double> coord_lengths = MyMath.linspace(root_coord, tip_coord, slices); //cross section length(s)
		
		for (int i = 0; i < r.size(); i++) {
			double CL = -.0008 * Math.pow(chi.get(i), 2) + .0724 * chi.get(i) + 1.0141;
			double CD = .091 * Math.exp(.0667 * chi.get(i));
			
			ret.add(2 * (Math.pow(Math.PI, 2)) * density * CD * number_of_blades * (Math.pow(rpm/60, 2)) * coord_lengths.get(i) * Math.pow(r.get(i), 3));
		}
		
		return ret;
	}


	private ArrayList<Double> getRelativeVelocity() {
		ArrayList<Double> ret = new ArrayList<Double>(); //list to return
		ArrayList<Double> chi = getAngles();
		for (int i = 0; i < r.size(); i++)
			ret.add(b * rpm * 6 * r.get(i) * (1-b) * MyMath.secDegrees(chi.get(i)));
		
		return ret;
	}
	
	public double getBladeLength() {
		return (this.diameter / 2) - (this.hub_diameter / 2);
	}
	
	public String toString() {
		return "Diameter: " + (this.diameter * 39.37f) + '"' 
				+ " | Root Coord Length: " + (this.root_coord * 39.37f) + '"'
				+ " | Tip Coord Length: " + (this.tip_coord * 39.37f) + '"'
				+ " | Number of Blades: " + this.number_of_blades
				+ " | Root Angle: " + this.root_angle
				+ " | Tip Angle: " + this.tip_angle
				+ " | Thrust: " + (this.getThrust() / 4.448f) + " lbs"
				+ " | Torque: " + (((this.getRequiredTorque()) * 39.37) / 4.448) + "in*lbs";
	}

}
