import java.util.ArrayList;

public class TurbineBlade {
	float diameter;
	float root_coord;
	float tip_coord;
	int number_of_blades;
	float root_angle;
	float tip_angle;
	
	float hub_diameter = 0.4f / 39.37f; //change this value here to how see fit
	
	float this_fitness;
	
	ArrayList<Float> r;
	
	//base fitness upon: highest thrust, least amount of torque
	
	//constructor
	public TurbineBlade(float diameter, float root_coord, float tip_coord,
				int number_of_blades, float root_angle, float tip_angle, boolean convert) {
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
		
		r = new ArrayList<Float>();
	}

	
	public float getCalculatedFitness() {
		//intake all parameters, calculate thrust and torque with these parameters
		float thrust = getThrust();
		float torque = getTorque();
		
		float fitness;
		float someConstant1 = 1; //feel free to change these
		float someConstant2 = 2;
		
		if (torque < .3) {
			fitness = (thrust * someConstant1) * (someConstant2 / (torque)); //feel free to change this equation to how you see fit
		}
		else {
			//torque was too high, don't consider;
			fitness = 0;
		}
		
		this.this_fitness = fitness;
		
		return 0; //placeholder value
	}
	
	public float getThrust() {
		//insert calculations to get thrust here
		
		return 0; //placeholder value
	}
	
	public float getTorque() {
		//insert calculations to get torque here
		
		return 0; //placeholder value
	}
	
	public float getBladeLength() {
		return (this.diameter / 2) - (this.hub_diameter / 2);
	}
	
	public void calculateRadiusFromHub() {
		//r = linspace(0, l_blade, 100)
	}
	
	public ArrayList<Float> linspace(float start, float end, int slices) {
		ArrayList<Float> retVal = new ArrayList<Float>();
		
		float increment = (start - end) / slices;
		for (int i = 0; i < slices; i++) {
			retVal.add(increment * i);
		}
		
		return retVal;
	}

}
