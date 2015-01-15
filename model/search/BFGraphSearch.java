package model.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import model.Action;
import model.Node;
import model.Problem;

public class BFGraphSearch implements SearchStrategy {

	private Queue<Node> frontier;
	private Set<Node> explored;

	@Override
	public List<Action> search(Problem problem) {
		Node node = new Node(problem.initialState());
		if (problem.isGoal(node.state())) {
			return node.solution();
		}

		frontier = new LinkedList<Node>();
		frontier.add(node);
		explored = new HashSet<Node>();

		while (!frontier.isEmpty()) {
			node = frontier.poll();
			explored.add(node);
			for (Action action : problem.actions(node.state())) {
				Node child = node.child(problem, action);
				if (!frontier.contains(child) && !explored.contains(child)) {
					if (problem.isGoal(child.state())) {
						return child.solution();
					}
					frontier.add(child);
				}
			}
		}
		return null; // failure
	}
}
