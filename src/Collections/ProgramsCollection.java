package Collections;

import java.util.ListIterator;
import Programs.Program;

/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description interface da Colecao de colaboradores.
 */
public interface ProgramsCollection {
	/**
	 * searches for the program in the List
	 * @param name - name of the program to search to
	 * @return -1 if it doesnt exist, int >= 0 if exists.
	 */
	int searchIndexOfProgram(String name);
	/**
	 * returns the List iterator for the all the programs
	 * @return List iterators
	 */
	ListIterator<Program> iteratorPrograms();
	/**
	 * add a program to the List 
	 * @param program - FilmClass || SeriesClass
	 */
	void addProgram(Program program);
	/**
	 * returns the program specified by the parameter
	 * @param programName - name of the program to return
	 * @pre searchIndexOfProgram(programName) >= 0 
	 * @return the program object
	 */
	Program getProgram(String programName);
}
