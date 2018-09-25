package Collections;
import Programs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description Programs Collection Class
 */
public class ProgramsCollectionClass implements ProgramsCollection {
	private List<Program> programs;
	
	public ProgramsCollectionClass(){
		programs = new ArrayList<Program>();
	}
	@Override
	public int searchIndexOfProgram(String name) {
		int index=-1;
        boolean found=false;
        int size = programs.size();
        if(size>0) {
	        for(int i=0;i<size&&!found;i++) {
	            if(programs.get(i).getTitle().equals(name)) {
	                index=i;
	                found=true;
	            }
	        }
        }
        return index;
	}

	@Override
	public ListIterator<Program> iteratorPrograms() {
		return programs.listIterator();
	}

	@Override
	public void addProgram(Program program) {
		programs.add(program);// insere na posicao counter
	}

	@Override
	public Program getProgram(String programName) {
		return programs.get(searchIndexOfProgram(programName));
	}

}
