package Users;

import java.util.Iterator;

import Programs.Program;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description Profiles interface
 */
public interface Profiles {
	/**
	 * profile name
	 * @return profile name - String
	 */
	String getName();
	/**
	 * profile age rating
	 * @return profile age rating
	 */
	int getAgeRating();
	/**
	 * Add a program to the recent watched vector
	 * @param prog 
	 */
	void watchProgram(Program prog);
	/**
	 * quantity of watched programs
	 * @return the size of the recent watched vectors
	 */
	int watchedProgramsCounter();
	/**
	 * Verify if the program is recent watched
	 * @param prog 
	 * @return true if the recent watched vectors contains prog, false otherwise
	 */
	boolean isProgramRecent(Program prog);
	/**
	 * Verify if the is already rated
	 * @param prog
	 * @return true if it is already rated, false otherwise
	 */
	boolean isProgramRated(Program prog);
	/**
	 * rate the program
	 * @param prog - Program
	 * @param rating - Int
	 */
	void rateProgram(Program prog,int rating);
	/**
	 * Verify if the Profile is children
	 * @return true if the profile is children, false otherwise
	 */
	boolean isChild();
	/**
	 * the Quantity of rated programs of the profile
	 * @return quantity of rated programs - Int
	 */
	int ratedProgramsCounter();
	/**
	 * iterator for recentPrograms
	 * @return iterator for recentPrograms
	 */
	Iterator<Program> recentProgramsIterator();
	/**
	 * iterator for Programs that are already rated
	 * @return iterator for rated Programs
	 */
	Iterator<Program> ratedProgramsIterator();
	
}
