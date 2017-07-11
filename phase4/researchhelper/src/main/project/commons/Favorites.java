package project.commons;

import java.util.HashSet;

/**
 * @author Neethu Prasad
 * @version 1.0
 * @since 2014-04-12
 */
public class Favorites {
	private static HashSet<Result> favorites = null;
	private static Favorites fav = null;
	
	/**
	 * Constructor for the class
	 */
	private Favorites() {
		favorites = new HashSet<Result>(100);
	}
	
	/**
	 * @return the object of Favorites class if already exist otherwise create a new one
	 */
	public static Favorites getInstance() {
		if(fav == null) {
			fav = new Favorites();
		}
		return fav;
	}
	
	/**
	 * @param r is the object of Result class which is taken from the results shown in result table and used to add it in the
	 * favorites list
	 */
	public void addToFavorites(Result r) {
		favorites.add(r);
	}
	
	/**
	 * @param r is the object of Result class which is taken from the results shown in favorites' result table and
	 * used to remove it from the favorites list
	 */
	public void removeFromFavorites(Result r) {
		favorites.remove(r);
	}
	
	/**
	 * @return the set of results stored in Favorites List
	 */
	public HashSet<Result> getFavorites() {
		return this.favorites;
	}
}

