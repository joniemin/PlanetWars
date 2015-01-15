package model;

import java.util.List;

public abstract class Problem {

	private State initialState;

	public abstract List<Action> actions(State state);

	public abstract State result(State state, Action action);

	public abstract boolean isGoal(State state);

	public abstract int stepCost(State state, Action action);
	
	public State initialState() {
		return initialState;
	}



}
