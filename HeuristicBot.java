import java.util.*;

/**
 * HeuristicBot - uses heuristic function to determine the best choice for
 * source and destination planets. The heuristic function is MY_GR / ENEMY_GR +
 * MY_SHIPS / ENEMY_SHIPS
 * 
 */
public class HeuristicBot {

	private static int PREDICTED_TURNS = 1;

	/**
	 * Function that gets called every turn. This is where to implement the
	 * strategies.
	 */
	public static void DoTurn(PlanetWars pw) {

		Planet source = null;
		Planet dest = null;
		int bestScore = Integer.MIN_VALUE;
		for (Planet s : pw.MyPlanets()) {
			for (Planet d : pw.Planets()) {
				if (s.PlanetID() == d.PlanetID()) {
					continue;
				}
				int score = score(s, d);
				if (score > bestScore){
					source = s;
					dest = d;
					bestScore = score;
					pw.log("Source:", s.NumShips(), "Dest:", d.NumShips(), "Score:", score);
				}
			}
		}

		// (3) Attack
		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}

	}

	/**
	 *	Calculate the the change in the difference between players 
	 *	relative to the amount of ships caused by the attack. 
	 *	Also take into acount the gains or losses caused by the changes in
	 *	the growth rates.
	 */
	private static int score(Planet source, Planet dest) {
		if (dest.Owner() == 1) {
			// sending ships to own planet doesn't change the amount
			// of ships or the amount of growth rate
			return 0; 
		}

		int attackingShips = source.NumShips() / 2; 
		int lostShips = Math.min(dest.NumShips(), attackingShips);
		boolean victory = dest.NumShips() < attackingShips;
		int growth = dest.GrowthRate() * PREDICTED_TURNS;
		boolean neutralPlanet = dest.Owner() == 0;

		int myShips = 0;
		int myGrowth = 0;
		int enemyShips = 0;
		int enemyGrowth = 0;

		myShips -= lostShips;
		if (victory) {
			myGrowth += growth;
		}
		if (!neutralPlanet) {
			enemyShips -= lostShips;
			if (victory) {
				enemyGrowth -= growth;
			}
		}

		int myPredictedShips = myShips + myGrowth;
		int predictedEnemyShips = enemyShips + enemyGrowth;

		return myPredictedShips - predictedEnemyShips;
	}

	public static void main(String[] args) {
		String line = "";
		String message = "";
		int c;
		try {
			while ((c = System.in.read()) >= 0) {
				switch (c) {
					case '\n':
						if (line.equals("go")) {
							PlanetWars pw = new PlanetWars(message);
							DoTurn(pw);
							pw.FinishTurn();
							message = "";
						} else {
							message += line + "\n";
						}
						line = "";
						break;
					default:
						line += (char) c;
						break;
				}
			}
		} catch (Exception e) {

		}
	}
}
