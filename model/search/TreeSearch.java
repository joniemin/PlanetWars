package model.search;

import java.util.*;

import model.*;

public abstract class TreeSearch implements SearchStrategy {

	private Queue<Node> frontier;

	@Override
	public List<Action> search(Problem problem) {
		List<Action> solution = new ArrayList<Action>();
		frontier.add(new Node(problem.initialState()));
		while (true) {
			if (!frontier.isEmpty()) {
				Node node = frontier.remove();
				if (problem.isGoal(node.getState())) {
					solution = node.solution();
				}	
				expand(problem, node);
			}
		}
	}


	protected void expand(Problem problem, Node node) {
		for (Action action : problem.actions(node.getState())) {
			Node successor = new Node(problem.result(node.getState(), action));
			frontier.add(successor);
		}
	}
}
