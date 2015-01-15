package model.agents;

import java.util.ArrayList;
import java.util.List;

import model.Action;
import model.Problem;
import model.State;
import model.search.SearchStrategy;

public abstract class ProblemSolvingAgent {

	private List<Action> seq = new ArrayList<Action>();
	private State state;
	private State goal;
	private Problem problem;
	private SearchStrategy searchStrategy;
	
	protected abstract State updateState(); 
	protected abstract State formulateGoal(State state);
	protected abstract Problem formulateProblem(State state, State goal);
	
	public Action nextAction(){
		updateState();
		if (seq.isEmpty()) {
			goal = formulateGoal(state);
			problem = formulateProblem(state, goal);
			seq = searchStrategy.search(problem);
		}
		return seq == null || seq.isEmpty() ? null : seq.remove(0);
	}
}
