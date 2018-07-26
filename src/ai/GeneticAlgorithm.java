package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utility.MoveDirection;

public class GeneticAlgorithm
{
	private int population_size;
	private MoveStrategy guaranteed_strategy;
	private float guaranteed_fitness;
	private int generation_id;
	private int no_fitness_counter;
	private Map<Float, MoveStrategy> scored_species;
	
	public GeneticAlgorithm(int population_size)
	{
		this.population_size = population_size;
		this.guaranteed_strategy = new MoveStrategy(new ArrayList<MoveDirection>());
		this.guaranteed_fitness = -1;
		this.generation_id = 0;
		this.no_fitness_counter = 0;
		this.scored_species = new HashMap<Float, MoveStrategy>();
	}
	
	public final MoveStrategy getBaseStrategy()
	{
		return this.guaranteed_strategy;
	}
	
	public final float getGuaranteedFitness()
	{
		return this.guaranteed_fitness;
	}
	
	public final int getPopulationSize()
	{
		return this.population_size;
	}
	
	public final MoveStrategy getNewStrategy()
	{
		MoveStrategy new_strategy = new MoveStrategy(this.guaranteed_strategy);
		int i = 0;
		do
		{
			new_strategy.addMove(MoveDirection.Random());
			i++;
		}while(i < (this.no_fitness_counter / 4) + 1);
		return new_strategy;
	}
	
	public final int getGeneration()
	{
		return this.generation_id;
	}
	
	public void rateStrategy(MoveStrategy strategy, float rating)
	{
		this.scored_species.put(rating, strategy);
	}
	
	public void nextGeneration()
	{
		// Get the top scored species, that strategy is now the best guaranteed iff its fitness beats our current guaranteed fitness.
		if(this.scored_species.size() >= 1)
		{
			float best_species_fitness = -1;
			for(float fitness : this.scored_species.keySet())
				best_species_fitness = Math.max(best_species_fitness, fitness);
			MoveStrategy best_species = this.scored_species.get(best_species_fitness);
			if(best_species_fitness > this.guaranteed_fitness)
			{
				this.guaranteed_strategy = best_species;
				this.guaranteed_fitness = best_species_fitness;
				this.no_fitness_counter = 0;
			}
			else
				this.no_fitness_counter++;
		}
		this.scored_species.clear();
		this.generation_id++;
		System.out.println("Generation " + this.generation_id + ", best fitness = " + this.guaranteed_fitness);
		System.out.println("Strategy: " + this.guaranteed_strategy);
		System.out.println("No-fitness Counter = " + this.no_fitness_counter);
	}
}
