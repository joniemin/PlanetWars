import java.util.*;

/**
 * HeuristicBot - uses heuristic function to determine the best choice for
 * source and destination planets. The heuristic function is MY_GR / ENEMY_GR +
 * MY_SHIPS / ENEMY_SHIPS
 * 
 */
public class HeuristicBot {

	private static int PREDICTED_TURNS = 2;

	/**
	 * Function that gets called every turn. This is where to implement the
	 * strategies.
	 */
	public static void DoTurn(PlanetWars pw) {

		int myShips = 0;
		int enemyShips = 0;
		int myGrowthRate = 0;
		int enemyGrowthRate = 0;

		for (Planet p : pw.Planets()) {
			if (p.Owner() == 1) {
				myShips = myShips + p.NumShips();
				myGrowthRate = myGrowthRate + p.GrowthRate();
			} else if (p.Owner() > 1) {
				enemyShips = enemyShips + p.NumShips();
				enemyGrowthRate = enemyGrowthRate + p.GrowthRate();
			}
		}

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
					pw.log("Source:", s.PlanetID(), "Dest:", d.PlanetID(), "Score:", score);
				}

			}
		}

		// (3) Attack
		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}

	}

	private static int score(Planet source, Planet dest) {
		if (dest.Owner() == 1) {
			return 0;
		}
		int myShipDelta = 0;
		int enemyShipDelta = 0;
		int myGrowthRateDelta = 0;
		int enemyGrowthRateDelta = 0;

		int attackingShips = source.NumShips() / 2;

		if (dest.NumShips() > attackingShips) {
			myShipDelta -= attackingShips;
		} else {
			myShipDelta -= dest.NumShips();
			myGrowthRateDelta += dest.GrowthRate();
			if (dest.Owner() > 1) {
				enemyShipDelta -= dest.NumShips();
				enemyGrowthRateDelta -= dest.GrowthRate();
			}
		}

		int myPredictedShips = myShipDelta + (myGrowthRateDelta * PREDICTED_TURNS);
		int predictedEnemyShips = enemyShipDelta + (enemyGrowthRateDelta * PREDICTED_TURNS);
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
