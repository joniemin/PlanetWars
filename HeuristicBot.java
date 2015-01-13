import java.util.*;

/**
 * HeuristicBot - uses heuristic function to determine the best choice for
 * source and destination planets. The heuristic function is MY_GR / ENEMY_GR +
 * MY_SHIPS / ENEMY_SHIPS
 * 
 */
public class HeuristicBot {

	private static class Pair {
		Planet source;
		Planet dest;
	}

	/**
	 * Function that gets called every turn. This is where to implement the
	 * strategies.
	 */
	public static void DoTurn(PlanetWars pw) {

		int myGrowthRate = 0;
		int enemyGrowthRate = 0;
		int myShips = 0;
		int enemyShips = 0;
		for (Planet p : pw.Planets()) {
			if (p.Owner() == 1) {
				myGrowthRate = myGrowthRate + p.GrowthRate();
				myShips = myShips + p.NumShips();
			} else if (p.Owner() > 1) {
				enemyGrowthRate = enemyGrowthRate + p.GrowthRate();
				enemyShips = enemyShips + p.NumShips();
			}
		}

		Planet source = pw.MyPlanets().get(0);
		Planet dest = pw.Planets().get(0);

		int distance = pw.Distance(source.PlanetID(), dest.PlanetID());

		// After the attack
		int shipsAtDest = dest.NumShips();
		if (dest.Owner() != 0) {
			shipsAtDest += dest.GrowthRate() * distance;
		}
		if (dest.Owner() == 1) {
			shipsAtDest += (source.NumShips() / 2);
		}else {
			shipsAtDest = (source.NumShips() / 2) - shipsAtDest;
		}
		int myShipsAtDest = shipsAtDest > 0 ? shipsAtDest : 0;

		int myShipsAtSource = source.NumShips() / 2 + source.GrowthRate() * distance;
		int myShipsAfterAttack = myShipsAtSource + myShipsAtDest;

		// (1) implement an algorithm to determine the source planet to send
		// your ships from
		// ... code here

		// (2) implement an algorithm to deterimen the destination planet to
		// send your ships to
		// ... code here

		// (3) Attack
		if (source != null && dest != null) {
			pw.IssueOrder(source, dest);
		}

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
