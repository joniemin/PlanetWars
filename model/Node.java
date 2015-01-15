package model;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private State state;
	private Node parent;
	private Action action;
	private int pathCost;
	private int depth;

	public Node(State state) {
		this(state, null, null, 0, 0);
	}

	public Node(State state, Node parent, Action action, int pathCost, int depth) {
		super();
		this.state = state;
		this.parent = parent;
		this.action = action;
		this.pathCost = pathCost;
		this.depth = depth;
	}

	public State getState() {
		return state;
	}

	public List<Action> solution() {
		if (parent == null) {
			return new ArrayList<Action>();
		}
		List<Action> solution = parent.solution();
		solution.add(action);
		return solution;
	}

}
