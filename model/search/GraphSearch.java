package model.search;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import model.Action;
import model.Node;
import model.Problem;


/**
 * Template class for GraphSearches
 * 
 */
public abstract class GraphSearch implements SearchStrategy {

	protected Queue<Node> frontier;
	protected Set<Node> explored;

	@Override
	public List<Action> search(Problem problem) {
		Node node = new Node(problem.initialState());
		frontier.add(node);
		explored = new HashSet<Node>();

		while (!frontier.isEmpty()) {
			node = frontier.poll();
			if (problem.isGoal(node.state())) {
				return node.solution();
			}
			explored.add(node);
			expand(node, problem);
		}
		return null; // failure
	}

	protected void expand(Node node, Problem problem) {
		for (Action action : problem.actions(node.state())) {
			Node child = node.child(problem, action);
			if (!frontier.contains(child) && !explored.contains(child)) {
				frontier.add(child);
			}
		}
	}

}
