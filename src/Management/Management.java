package Management;
import Users.*;
import java.util.Iterator;

import Exceptions.NoOneLoggedException;
import Exceptions.NoProfileSelectedException;
import Exceptions.NoProgramWithParameterException;
import Exceptions.TooMuchProfilesException;
import Programs.Program;

/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description interface management que e a do topo, que gere o programa
 */
public interface Management {
	/**
	 * upload the programs to the database
	 * @param title - title of the program
	 * @param creator - creator of the program
	 * @param duration - filmclass = minutes || seriesclass = number of seasons
	 * @param ageRating - age rating of the program
	 * @param releaseYear - release year of the program
	 * @param genre - genre of the program
	 * @param castMembers - cast members of the program
	 * @param epsPerSeason - (FilmClass = 0) || (SeriesClass = Int >=1)
	 * @param type - "FILM" or "SERIES", to know which one to instantiate(new) 
	 */
	void upload(String title, String creator, int duration, int ageRating, int releaseYear, String genre,
			String[] castMembers, int epsPerSeason, String type);
	/**
	 * Iterator for alphabetical ordered programs
	 * @return iterator of programs
	 */
	Iterator<Program> IteratorSortedPrograms();
	/**
	 * register an account to the application
	 * @param name - name of the account
	 * @param email - email of the account
	 * @param password - password of the account
	 * @param disp - first device of the account
	 */
	void registAccount(String name, String email, String password, String disp);
	/**
	 * verify if this account has the same email of the account passed by parameter
	 * @param user
	 * @return
	 */
	boolean sameEmail(User user);
	/**
	 * login the account in
	 * @param email - email of the user account
	 * @param password - password
	 * @param device - device name to login to
	 */
	void login(String email, String password, String device);
	/**
	 * search for the account with the email passed by parameter
	 * @param email - email of the user account
	 * @return index of the user account - Int
	 */
	int searchIndexAccountEmail(String email);
	/**
	 * return the index of the current logged account 
	 * @return index of current logged account
	 */
	int searchIndexAccountLoggedIn();
	/**
	 * disconnects the current user and remove the device
	 */
	void disconnect();
	/**
	 * logout the current user, but the device keeps on
	 */
	void logout();
	/**
	 * change the channel plan of the current logged user
	 * @param plan - plan name to change to
	 */
	void membership(String plan);
	/**
	 * returns the max quantity of devices from the plan passed by parameter
	 * @param name - name of the plan
	 * @return number of max devices
	 */
	int getMaxDevicesFromPlan(String name);
	/**
	 * returns the current user
	 * @return current user Obj - User
	 */
	User getCurrentUser();
	/**
	 * create a CHILDREN user profile to the current logged account
	 * @param name - name of the profile 
	 * @param ageRating - age rating for this account
	 */
	void createUserProfile(String name, int ageRating);
	/**
	 * create a NORMAL user profile to the current logged account
	 * @param name - name of the profile
	 */
	void createUserProfile(String name);
	/**
	 * select the profile to login 
	 * @param name - profile name
	 */
	void selectUserProfile(String name);
	/**
	 * returns the current logged profile
	 * @return current Profile Obj - Profiles
	 */
	Profiles getCurrentUserProfile();
	/**
	 * watch the program passed by parameter
	 * @param nameProgram - Name of the program to watch
	 */
	void watchProgram(String nameProgram);
	/**
	 * the number of watched programs of the current profile of the current user that is logged 
	 * @return quantity of watched programs
	 */
	int watchedProgramsCounter();
	/**
	 * insert the program to the current profile rated programs vector
	 * @param nameProg - name of the program
	 * @param rating - rating of the program
	 */
	void rate(String nameProg, int rating);
	/**
	 * iterator for the recent programs of the profile
	 * @return iterator recent programs
	 */
	Iterator<Program> userRecentProgramsIterator();
	/**
	 * iterator for the specified parameter (genre or name)
	 * @param parameter - name or genre to search for
	 * @param typeSearch - the type of the search ("GENRE" || "NAME")
	 * @return iterator for the specified parameter
	 */
	Iterator<Program> programsByParameterIterator(String parameter,String typeSearch);
	/**
	 * returns the iterator for rated programs
	 * @param rate - the minimum rating of programs
	 * @return iterator for programs with the specified minimum rating
	 */
	Iterator<Program> programsByRateIterator(int rate);
	
}
