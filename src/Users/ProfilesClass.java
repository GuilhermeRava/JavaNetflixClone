package Users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Programs.*;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description Profiles Class
 */
public class ProfilesClass implements Profiles {
	private String name;
	private int ageRating;
	private List<Program> recentPrograms;
	private List<Program> ratedPrograms;
	
	public ProfilesClass(String name,int ageRating) {
		this.name = name;
		this.ageRating = ageRating;
		recentPrograms = new ArrayList<Program>(10); // so pode ter 10 programas assistidos recentemente
		ratedPrograms = new ArrayList<Program>();
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getAgeRating() {
		return ageRating;
	}
	
	@Override
	public void watchProgram(Program prog) {
		if(recentPrograms.size() == 10)
			recentPrograms.remove(9);
		recentPrograms.add(0, prog);
	}
	
	@Override
	public int watchedProgramsCounter() {
		return recentPrograms.size();
	}
	
	@Override
	public Iterator<Program> recentProgramsIterator(){
		return recentPrograms.iterator();
	}
	@Override
	public Iterator<Program> ratedProgramsIterator(){
		return ratedPrograms.iterator();
	}
	@Override
	public boolean isProgramRecent(Program prog) {
		return recentPrograms.contains(prog);
	}
	@Override
	public boolean isProgramRated(Program prog) {
		return ratedPrograms.contains(prog);
	}
	@Override
	public void rateProgram(Program prog,int rating) {
		ratedPrograms.add(prog);
		prog.rateProgram(this.name,rating);
	}
	@Override
	public boolean isChild() {
		return ageRating < 18;
	}
	
	@Override
	public int ratedProgramsCounter() {
		return ratedPrograms.size();
	}
}
