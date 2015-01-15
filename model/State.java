package model;

public interface State {

	public State apply(Action action);
	
}
