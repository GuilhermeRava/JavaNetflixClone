package Management;

import Programs.*;
import Users.*;
import Exceptions.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import Collections.*;
import Comparators.ComparatorRating;

public class ManagementClass implements Management {
	private static final String FILM = "Film";
	private static final String SERIES = "Series";
	private static final String GENRE = "genre";
	private static final String NAME = "name";
	private ProgramsCollection programs;
	private SortedMap<String,User> users;
	private SortedMap<String,Program> alphabeticPrograms;
	private SortedSet<Program> programsByRate;
	private List<User> users1;
	private Map<String,SortedMap<String,Program>> programsByGenre;
	private Map<String,List<Program>> programsByName;
	private boolean someoneLogged;
	private User currentUser;
	
	public ManagementClass() {
		this.programs = new ProgramsCollectionClass();
		users= new TreeMap<String,User>();
		alphabeticPrograms= new TreeMap<String,Program>();
		users1=new ArrayList<User>();
		programsByGenre=new TreeMap<String,SortedMap<String,Program>>();
		programsByName=new TreeMap<String,List<Program>>();
		someoneLogged=false;
		currentUser=null;
	}
	
	@Override
	public void upload(String title,String creator,int duration,int ageRating,int releaseYear,String genre,String[] castMembers,int epsPerSeason,String type) {
		Program program = null;
		if(type.equals(FILM))
			program = new FilmClass(title,creator,duration,ageRating,releaseYear,genre,castMembers);
		else
			program = new SeriesClass(title,creator,duration,ageRating,releaseYear,genre,castMembers,epsPerSeason);
		programs.addProgram(program);
		alphabeticPrograms.put(title, program);
		addToProgramsByGenre(genre,title,program);
		addToProgramsByName(creator,title,castMembers,program);
	}
	
	@Override
	public Iterator<Program> IteratorSortedPrograms() {
		return alphabeticPrograms.values().iterator();
	}
	
	@Override
	public void registAccount(String name,String email,String password,String disp) throws SomeoneLoggedException,SameEmailException{
		User accountTemp = new UserClass(name,email,password,disp);
		if(someoneLogged) {
			throw new SomeoneLoggedException();
		}
		else if(sameEmail(accountTemp)) {
			throw new SameEmailException();
		}
		else {
			users1.add(accountTemp);
			users.put(name, accountTemp);
			accountTemp.addDevice(disp);
			accountTemp.login(disp);
			currentUser=accountTemp;
			someoneLogged=true;
		}
	}
	
	@Override
	public boolean sameEmail(User user) {
		Iterator<User> tempUsers = users1.iterator();
		while(tempUsers.hasNext()) {
			User tempUser = tempUsers.next();
			if(tempUser.getEmail().equals(user.getEmail()))
				return true;
		}
		return false;
	}
	
	@Override
	public void login(String email,String password,String device) throws AccountDoesNotExistException,ClientLoggedInException,SomeoneLoggedException,
	WrongPasswordException,TooMuchDevicesException{
		if(someoneLogged && this.getCurrentUser().getEmail().equals(email)) {
			throw new ClientLoggedInException();
		}
		else if(someoneLogged) {
			throw new SomeoneLoggedException();
		}
		else if(searchIndexAccountEmail(email)==-1) {
			throw new AccountDoesNotExistException();
		}
		else {
			User userTemp = users1.get(searchIndexAccountEmail(email));
			if(!userTemp.getPassword().equals(password)) {
				throw new WrongPasswordException();
			}
			else if(userTemp.getMaxDevices()==userTemp.getDevicesCounter()&&userTemp.searchDevice(device)==-1) {
				throw new TooMuchDevicesException();
			}
			else {
				if(userTemp.searchDevice(device)==-1) {
					userTemp.addDevice(device);
				}
				currentUser=userTemp;
				userTemp.login(device);
				someoneLogged=true;
			}		
		}
	}
	
	@Override
	public void disconnect() throws NoOneLoggedException{
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		else {
			currentUser.removeDevice();
			currentUser.disconnect();
			currentUser=null;
			someoneLogged=false;
		}
	}
	
	@Override
	public void logout() throws NoOneLoggedException{
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		else {
			currentUser.logoff();
			currentUser=null;
			someoneLogged=false;
		}
	}
	
	
	@Override
	public int searchIndexAccountEmail(String email) {
		int index=-1;
        boolean found=false;
        int size = users1.size();
        if(size>0) {
	        for(int i=0;i<size&&!found;i++) {
	            if(users1.get(i).getEmail().equals(email)) {
	                index=i;
	                found=true;
	            }
	        }
        }
        return index;
	}
	
	@Override
	public int searchIndexAccountLoggedIn() {
		int index=-1;
        boolean found=false;
        int size = users1.size();
        if(size>0) {
	        for(int i=0;i<size&&!found;i++) {
	            if(users1.get(i).isLogged()) {
	                index=i;
	                found=true;
	            }
	        }
        }
        return index;
	}
	
	@Override
	public void membership(String plan) throws NoOneLoggedException,SamePlanException,DownGradeException{
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		else if(currentUser.getChannelPlan().equalsIgnoreCase(plan)) {
			throw new SamePlanException();
		}
		else if(currentUser.getDevicesCounter()>getMaxDevicesFromPlan(plan)) {
			throw new DownGradeException();
		}
		else {
			currentUser.membership(plan);
		}
	}
	
	@Override
	public int getMaxDevicesFromPlan(String name) {
		if(name.equalsIgnoreCase("Standard")) {
			return 2;
		}
		else if(name.equalsIgnoreCase("Premium")) {
			return 4;
		}
		else {
			return 1;
		}
	}
	

	@Override
	public User getCurrentUser() throws NoOneLoggedException{
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		return currentUser;
	}
	
	@Override
	public void createUserProfile(String name,int ageRating) throws TooMuchProfilesException,NoOneLoggedException{
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		else if(currentUser.existProfile(name)){
			throw new ProfileAlreadyExistException();
		}
		else if(currentUser.getProfilesCounter() == currentUser.getMaxProfiles())
			throw new TooMuchProfilesException();
		else {
			currentUser.addProfile(name, ageRating);
			currentUser.selectProfile(name);
			//assert currentUser.getCurrentProfile().getAgeRating() == ageRating;
		}
	}
	
	@Override
	public void createUserProfile(String name) throws TooMuchProfilesException,NoOneLoggedException{
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		else if(currentUser.existProfile(name)){
			throw new ProfileAlreadyExistException();
		}
		else if(currentUser.getProfilesCounter() == currentUser.getMaxProfiles())
			throw new TooMuchProfilesException();
		else {
			currentUser.addProfile(name);
			currentUser.selectProfile(name);
		}
	}
	
	@Override
	public void selectUserProfile(String name) throws NoOneLoggedException,ProfileDoesntExistException{
		if(!someoneLogged)
			throw new NoOneLoggedException();
		else{
			if(!currentUser.existProfile(name))
				throw new ProfileDoesntExistException();
			else {
				currentUser.selectProfile(name);
			}
		}
	}
	
	@Override
	public Profiles getCurrentUserProfile() {
		return currentUser.getCurrentProfile();
	}
	
	@Override
	public void watchProgram(String nameProgram) throws NoOneLoggedException,NoProfileSelectedException,
	ProgramDoesntExistException,ProgramNotAvailableException{
		if(!someoneLogged)
			throw new NoOneLoggedException();
		else if(currentUser.getCurrentProfile() == null)
			throw new NoProfileSelectedException();
		else if(!alphabeticPrograms.containsKey(nameProgram))
			throw new ProgramDoesntExistException();
		else {
			Program selectedProgram = alphabeticPrograms.get(nameProgram);
			if(currentUser.isChild() 
					&& currentUser.getCurrentProfile().getAgeRating() < selectedProgram.getAgeRating())
				throw new ProgramNotAvailableException();
			else {
				currentUser.watchProgram(selectedProgram);
				if(currentUser.getCurrentProfile().isChild())
					assert selectedProgram.getAgeRating() < 18;
			}
			assert currentUser.getCurrentProfile().getAgeRating() >= selectedProgram.getAgeRating();
			
		}
	}
	
	@Override
	public int watchedProgramsCounter() {
		return currentUser.watchedProgramsCounter();
	}
	
	@Override
	public Iterator<Program> userRecentProgramsIterator() {
		return currentUser.recentProgramsIterator();
	}
	
	@Override
	public void rate(String nameProg,int rating) throws NoOneLoggedException,NoProfileSelectedException,
	ProgramDoesntExistException,NotRecentWatchedException,AlreadyRatedException{
		if(!someoneLogged)
			throw new NoOneLoggedException();
		else if(currentUser.getCurrentProfile() == null)
			throw new NoProfileSelectedException();
		else if(!alphabeticPrograms.containsKey(nameProg))
			throw new ProgramDoesntExistException();
		else if(!currentUser.getCurrentProfile().isProgramRecent(alphabeticPrograms.get(nameProg)))
			throw new NotRecentWatchedException();
		else if(currentUser.getCurrentProfile().isProgramRated(alphabeticPrograms.get(nameProg)))
			throw new AlreadyRatedException();
		else{
			currentUser.getCurrentProfile().rateProgram(alphabeticPrograms.get(nameProg),rating);
		}
	}
	
	
	private void addToProgramsByGenre(String genre,String title,Program program) {
		if(programsByGenre.get(genre)==null) {
			programsByGenre.put(genre, new TreeMap<String,Program>());
			programsByGenre.get(genre).put(title, program);
		}else {
		programsByGenre.get(genre).put(title,program);
		}
	}
	
	
	private void addToProgramsByName(String creator,String title,String[] castMembers,Program program) {
		if(programsByName.get(creator)== null)
			programsByName.put(creator, new ArrayList<Program>());
		if(!programsByName.get(creator).contains(program))
			programsByName.get(creator).add(0,program);
		
		for(int i=0;i<castMembers.length;i++) {
			if(programsByName.get(castMembers[i]) == null)
				programsByName.put(castMembers[i], new ArrayList<Program>());
			if(!programsByName.get(castMembers[i]).contains(program))
					programsByName.get(castMembers[i]).add(0,program);
		}
	}
	
	@Override
	public Iterator<Program> programsByParameterIterator(String parameter,String typeSearch) throws NoOneLoggedException,NoProfileSelectedException,NoProgramWithParameterException{
		Iterator<Program> iterator = null;
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		else if(currentUser.getCurrentProfile()==null) {
			throw new NoProfileSelectedException();
		}
		switch(typeSearch) {
			case GENRE: iterator = genreIterator(parameter);
			break;
			case NAME: iterator = nameIterator(parameter);
			break;
		}
		return iterator;
	}
	
	private Iterator<Program> genreIterator(String parameter) throws NoProgramWithParameterException{
		if(programsByGenre.get(parameter)==null)
			throw new NoProgramWithParameterException();
		return programsByGenre.get(parameter).values().iterator();
	}
	
	private Iterator<Program> nameIterator(String parameter) throws NoProgramWithParameterException{
		if(programsByName.get(parameter)==null)
			throw new NoProgramWithParameterException();
		return programsByName.get(parameter).iterator();
	}
	
	@Override
	public Iterator<Program> programsByRateIterator(int rate) throws NoOneLoggedException,NoProfileSelectedException,NoProgramWithParameterException{
		Iterator<Program> iterator = null;
		if(!someoneLogged) {
			throw new NoOneLoggedException();
		}
		else if(currentUser.getCurrentProfile()==null) {
			throw new NoProfileSelectedException();
		}
		else {
			iterator = populateRatingIterator(rate);
			if(!iterator.hasNext())
				throw new NoProgramWithParameterException();
		}
		return iterator;
	}
	
	private Iterator<Program> populateRatingIterator(float rate) {
		Iterator<Program> iterator = null;
		Iterator<Program> iteratorTemp = this.programs.iteratorPrograms();
		Comparator<Program> cmp = new ComparatorRating();
		programsByRate = new TreeSet<Program>(cmp);
		while(iteratorTemp.hasNext()) {
			Program tempProgram = iteratorTemp.next();
			if(tempProgram.getRating() >= rate)
				if(currentUser.getCurrentProfile().getAgeRating() >= tempProgram.getAgeRating()) {
					programsByRate.add(tempProgram);
				}
		}
		iterator = programsByRate.iterator();
		return iterator;
	}
}
