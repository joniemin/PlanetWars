package model;

import java.util.ArrayList;
import java.util.List;

public class Node{

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

	public State state() {
		return state;
	}
	
	public int pathCost() {
		return pathCost;
	}

	public List<Action> solution() {
		if (parent == null) {
			return new ArrayList<Action>();
		}
		List<Action> solution = parent.solution();
		solution.add(action);
		return solution;
	}

	public Node child(Problem problem, Action action) {
		return new Node(problem.result(state, action), this, action, pathCost + problem.stepCost(state, action),
				depth + 1);
	}

}
