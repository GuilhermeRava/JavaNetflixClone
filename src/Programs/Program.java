package Programs;

import java.util.Iterator;

/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description Program Abstract Interface, used as a base for Films and Series classes.
 */
public interface Program {
	/**
	 * return the name of the program
	 * @return name of the program - String
	 */
	String getTitle();
	/**
	 * return the creator of the program
	 * @return creator of the program - String
	 */
	String getCreator();
	/**
	 * duration of the program (FilmClass = minutes of the movie) (SeriesClass = Number of seasons)
	 * @return Int > 0
	 */
	int getDuration();
	/**
	 * return the age rating of the program
	 * @return int > 0
	 */
	int getAgeRating();
	/**
	 * return the release year of the movie
	 * @return the year of release - Int
	 */
	int getReleaseYear();
	/**
	 * return the genre of the film
	 * @return genre of the film - String
	 */
	String getGenre();
	/**
	 * get the current rating of the program (average of all the votes divided by the quantity of voters)
	 * @return average rating of the program - Floats
	 */
	float getRating();
	/**
	 * return the eps per season (SeriesClass its a int >= 1) (FilmClass int = 0)
	 * @return
	 */
	int getEpsPerSeason();
	/**
	 * rate the program
	 * @param nameUser - name of who is rating
	 * @param rating - score 
	 */
	void rateProgram(String nameUser, int rating);
	/**
	 * returns the rating of the specific Profile
	 * @param profileName - name of the profile
	 * @return the rating of the profile to this specific program between 0 and 5 - Int
	 */
	Integer getProfileRating(String profileName);
	/**
	 * return the quantity of cast members
	 * @return quantity of cast members
	 */
	int getCastMembersCounter();
	/**
	 * return the Iterator for the cast members name
	 * @return iterator<String>
	 */
	Iterator<String> iteratorMembers();
}
