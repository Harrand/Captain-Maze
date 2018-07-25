package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utility.MoveDirection;

public class GeneticAlgorithm
{
	private MoveStrategy guaranteed_strategy;
	private float guaranteed_fitness;
	private int generation_id;
	private float last_generation_fitness_improvement;
	private Map<Float, MoveStrategy> scored_species;
	
	public GeneticAlgorithm()
	{
		this.guaranteed_strategy = new MoveStrategy(new ArrayList<MoveDirection>());
		this.guaranteed_fitness = -1;
		this.generation_id = 0;
		this.last_generation_fitness_improvement = 0.0f;
		this.scored_species = new HashMap<Float, MoveStrategy>();
	}
	
	public final float getGuaranteedFitness()
	{
		return this.guaranteed_fitness;
	}
	
	public final MoveStrategy getNewStrategy()
	{
		MoveStrategy new_strategy = new MoveStrategy(this.guaranteed_strategy);
		if(new_strategy.size() == 0)
		{
			new_strategy.addMove(MoveDirection.Random());
			return new_strategy;
		}
		int i = 0;
		do
		{
			MoveDirection previous = new_strategy.top();
			MoveDirection next = MoveDirection.Random();
			if(MoveDirection.get(next.unit().minus()).equals(previous));
				next = MoveDirection.get(next.unit().minus());
			new_strategy.addMove(next);
			i++;
		}while(i < this.generation_id / 10);
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
				this.last_generation_fitness_improvement = best_species_fitness - this.guaranteed_fitness;
				this.guaranteed_fitness = best_species_fitness;
			}
		}
		this.scored_species.clear();
		this.generation_id++;
		System.out.println("Generation " + this.generation_id + ", best fitness = " + this.guaranteed_fitness);
	}
}
