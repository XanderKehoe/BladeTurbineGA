import java.util.ArrayList;
import java.util.Random;

public class Main {

	static int N = 100;
	static int firstGenSurplus = 1000;
	static float mutationRate = 0.05f;
	static float mutationAmount = 0.15f;
	
	static int generation = 0;
	
	public static void main(String[] args) {
		Random rand = new Random();
		ArrayList<TurbineBlade> turbineBladeList = new ArrayList<TurbineBlade>();
		
		//step #1, initialize
		while (turbineBladeList.size() < N + firstGenSurplus) {
			TurbineBlade newBlade = new TurbineBlade(Limits.min_diameter + rand.nextFloat() * (Limits.max_diameter - Limits.min_diameter), //random diameter
													Limits.min_root_coord + rand.nextFloat() * (Limits.max_root_coord - Limits.min_root_coord), //random root_coord
													Limits.min_tip_coord + rand.nextFloat() * (Limits.max_tip_coord - Limits.min_tip_coord), //random tip_coord
													(int) (Limits.min_number_of_blades + rand.nextFloat() * (Limits.max_number_of_blades - Limits.min_number_of_blades)) , //random number_of_blades
													Limits.min_root_angle + rand.nextFloat() * (Limits.max_root_angle - Limits.min_root_angle), //random root_angle
													Limits.min_tip_angle + rand.nextFloat() * (Limits.max_tip_angle - Limits.min_tip_angle), //random tip_angle
												  true);
			if (newBlade.getCalculatedFitness() != 0) {
				turbineBladeList.add(newBlade);
				newBlade.toString();
				System.out.println("Initialization: " + ((float) turbineBladeList.size() / (float) (N + firstGenSurplus)) + " | "+newBlade.diameter);
			}
		}
		
		while (true) {
			//Elitism
			
			double bestFitness = 0;
			int bestIndex = 0;
			for (int i = 0; i < N; i++) {
				double thisFitness = turbineBladeList.get(i).getCalculatedFitness();
				if (thisFitness > bestFitness) {
					bestFitness = thisFitness;
					bestIndex = i;
				}
			}
			
			ArrayList<TurbineBlade> newTurbineBladeList = new ArrayList<TurbineBlade>(); //new population for step #3(d)
			TurbineBlade bestBlade = turbineBladeList.get(bestIndex);
			newTurbineBladeList.add(bestBlade);
			
			System.out.println("Best Fitness This Generation: "+bestBlade.getCalculatedFitness()+ " | "+bestBlade.getThrust()+" | "+bestBlade.getTorque() + " - " +(bestBlade.getTorque() < (.3 * 4.448f) / 37.39f));
			System.out.println("Best Blade: \n" + bestBlade.toString());
			
			//Step #2 and #3 combined
			while (newTurbineBladeList.size() < N) { //i starts at 1 to make room for bestBlade (start at 2 in MatLab for 0 based indexing)
				//pick parents
				TurbineBlade parent1 = chooseOnWeight(turbineBladeList);
				TurbineBlade parent2 = chooseOnWeight(turbineBladeList);
				
				//Step #3(b) Crossover
				TurbineBlade child = crossover(parent1, parent2);
				
				//Step #3(c) Mutation
				child = mutate(child);
				
				//Step #3(d) add child to new population
				if (child.getCalculatedFitness() != 0)
					newTurbineBladeList.add(child);
			}
			
			//Step #4 replace old pop with new pop
			System.out.println("New Gen ["+generation+"]: ");
			turbineBladeList = newTurbineBladeList;
			generation++;
			
		}
	}
	
	public static TurbineBlade crossover(TurbineBlade parent1, TurbineBlade parent2) {
		Random rand = new Random();
		double diameter;
		double root_coord;
		double tip_coord;

		int number_of_blades;
		double root_angle;
		double tip_angle;
		
		if (rand.nextBoolean()) //pick either parent1 or parent2
			diameter = parent1.diameter;
		else
			diameter = parent2.diameter;
		
		if (rand.nextBoolean()) //pick either parent1 or parent2
			root_coord = parent1.root_coord;
		else
			root_coord = parent2.root_coord;
		
		if (rand.nextBoolean()) //pick either parent1 or parent2
			tip_coord = parent1.tip_coord;
		else
			tip_coord = parent2.tip_coord;
		
		if (rand.nextBoolean()) //pick either parent1 or parent2
			number_of_blades = parent1.number_of_blades;
		else
			number_of_blades = parent2.number_of_blades;
		
		if (rand.nextBoolean()) //pick either parent1 or parent2
			root_angle = parent1.root_angle;
		else
			root_angle = parent2.root_angle;
		
		if (rand.nextBoolean()) //pick either parent1 or parent2
			tip_angle = parent1.tip_angle;
		else
			tip_angle = parent2.tip_angle;
		
		return new TurbineBlade(diameter, root_coord, tip_coord, number_of_blades, root_angle, tip_angle, false);
	}
	
	public static TurbineBlade mutate(TurbineBlade thisTurbineBlade) {
		Random rand = new Random();
		double diameter = thisTurbineBlade.diameter;
		double root_coord = thisTurbineBlade.root_coord;
		double tip_coord = thisTurbineBlade.tip_coord;

		int number_of_blades = thisTurbineBlade.number_of_blades;
		double root_angle = thisTurbineBlade.root_angle;
		double tip_angle = thisTurbineBlade.tip_angle;
		
		if (rand.nextFloat() < mutationRate) {
			if (rand.nextBoolean()) //pick to add or subtract
				diameter += rand.nextFloat() * (diameter * mutationAmount);
			else
				diameter -= rand.nextFloat() * (diameter * mutationAmount);
		}
		
		if (rand.nextFloat() < mutationRate) {
			if (rand.nextBoolean()) //pick to add or subtract
				root_coord += rand.nextFloat() * (root_coord * mutationAmount);
			else
				root_coord -= rand.nextFloat() * (root_coord * mutationAmount);
		}
		
		if (rand.nextFloat() < mutationRate) {
			if (rand.nextBoolean()) //pick to add or subtract
				tip_coord += rand.nextFloat() * (tip_coord * mutationAmount);
			else
				tip_coord -= rand.nextFloat() * (tip_coord * mutationAmount);
		}
		
		if (rand.nextFloat() < mutationRate) {
			if (rand.nextBoolean()) //pick to add or subtract
				number_of_blades += rand.nextFloat() * (number_of_blades * mutationAmount);
			else
				number_of_blades -= rand.nextFloat() * (number_of_blades * mutationAmount);
		}
		
		if (rand.nextFloat() < mutationRate) {
			if (rand.nextBoolean()) //pick to add or subtract
				root_angle += rand.nextFloat() * (root_angle * mutationAmount);
			else
				root_angle -= rand.nextFloat() * (root_angle * mutationAmount);
		}
		
		if (rand.nextFloat() < mutationRate) {
			if (rand.nextBoolean()) //pick to add or subtract
				tip_angle += rand.nextFloat() * (tip_angle * mutationAmount);
			else
				tip_angle -= rand.nextFloat() * (tip_angle * mutationAmount);
		}
		
		//making sure certain values do not dip into impossible range
		if (diameter > MyMath.convertToMeters(Limits.max_diameter)) diameter = MyMath.convertToMeters(Limits.max_diameter);
		if (root_coord > MyMath.convertToMeters(Limits.max_root_coord)) root_coord = MyMath.convertToMeters(Limits.max_root_coord);
		if (tip_coord > MyMath.convertToMeters(Limits.max_tip_coord)) tip_coord = MyMath.convertToMeters(Limits.max_tip_coord);
		if (root_angle > Limits.max_root_angle) root_angle = Limits.max_root_angle;
		if (tip_angle < Limits.min_tip_angle) tip_angle = Limits.min_tip_angle;
		if (tip_angle > Limits.max_tip_angle) tip_angle = Limits.max_tip_angle;
		if (number_of_blades < Limits.min_number_of_blades) number_of_blades = Limits.min_number_of_blades;
		if (number_of_blades > Limits.max_number_of_blades) number_of_blades = Limits.max_number_of_blades;
		
		return new TurbineBlade(diameter, root_coord, tip_coord, number_of_blades, root_angle, tip_angle, false);
	}
	
	public static TurbineBlade chooseOnWeight(ArrayList<TurbineBlade> items) {
		//picks weighted random Climber based upon fitness
        double completeWeight = 0.0;
        for (TurbineBlade item : items)
            completeWeight += item.getCalculatedFitness();
        double r = Math.random() * completeWeight;
        double countWeight = 0.0;
        for (TurbineBlade item : items) {
            countWeight += item.getCalculatedFitness();
            if (countWeight >= r)
                return item;
        }
        return null;
    }

}