package model.search;

import java.util.List;

import model.*;

public interface SearchStrategy {
	
	public List<Action> search(Problem problem);
 
}
