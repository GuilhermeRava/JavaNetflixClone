import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;
import Exceptions.*;
import Management.*;
import Programs.*;
import Users.Profiles;
import Users.User;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description main do nosso programa
 */
public class Main {
	private static final String FILM = "Film";
	private static final String SERIES = "Series";
	private static final String GENRE = "genre";
	private static final String NAME = "name";
	private enum Commands{
		UPLOAD,EXIT,REGISTER,LOGIN,DISCONNECT,LOGOUT,MEMBERSHIP,PROFILE,SELECT,WATCH,RATE,INFOACCOUNT,SEARCHBYGENRE,SEARCHBYNAME,SEARCHBYRATE,UNKNOW;
	};
	private enum ProgramOutputMessages{
		EXIT("Exiting..."),
		UNKNOWCOMMAND("Unknow Command."),
		NO_ONE_LOGGED("No client is logged in."),
		DATABASE_UPLOADED("Database was updated:"),
		ANOTHER_CLIENT("Another client is logged in."),
		WRONG_PASS("Wrong password."),
		ACC_DEXIST("Account does not exist."),
		TOO_MDEVICES("Not possible to connect more devices."),
		SOME_LOGGED("Another client is logged in."),
		CLIENT_IS_LOGGED("Client already logged in."),
		PROFILE_DEXIST("Profile does not exist."),
		NO_PROFILE_SELECTED("No profile is selected."),
		PROGRAM_DEXIST("Show does not exist."),
		PROGRAM_NAVAL("Show not available."),
		NOT_RECENT_PROG("Can only rate recently seen shows."),
		ALREADY_RATED_PROG("Show already rated."),
		NO_SHOW_FOUND("No show found.");
		
		private final String output;
		
		ProgramOutputMessages(String output) {
			this.output = output;
		}
		
		public String getCommandsTexts() {
			return output;
		}
	};
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Commands command;
		Management notNetflix = new ManagementClass();
		do {
			//System.out.printf("> ");
			command = getCommand(input);
			switch(command) {
				case EXIT: System.out.printf(ProgramOutputMessages.EXIT.getCommandsTexts());
				break;
				case UPLOAD: uploadProgram(notNetflix,input);
				break;
				case REGISTER: registerUser(notNetflix,input);
				break;
				case LOGIN: loginUser(notNetflix,input);
				break;
				case DISCONNECT: disconnectUser(notNetflix,input);
				break;
				case LOGOUT: logoutUser(notNetflix,input);
				break;
				case MEMBERSHIP: membershipUser(notNetflix,input);
				break;
				case PROFILE: createUserProfile(notNetflix,input);
				break;
				case SELECT: selectProfileUser(notNetflix,input);
				break;
				case WATCH: watchProgram(notNetflix,input);
				break;
				case RATE: rateProgram(notNetflix,input);
				break;
				case INFOACCOUNT: infoUser(notNetflix);
				break;
				case SEARCHBYGENRE: listByParameter(notNetflix,input,GENRE);
				break;
				case SEARCHBYNAME: listByParameter(notNetflix,input,NAME);
				break;
				case SEARCHBYRATE: listByRate(notNetflix,input);
				break;
				default: System.out.println(ProgramOutputMessages.UNKNOWCOMMAND.getCommandsTexts());
				break;
			}
			//Puts a blank line between commands
			System.out.println();
		} while(!command.equals(Commands.EXIT));
		input.close();
	}

	private static void listByRate(Management notNetflix, Scanner input) {
		int rating = input.nextInt(); input.nextLine();
		try {
			Iterator<Program> programsByRating = notNetflix.programsByRateIterator(rating);
			while(programsByRating.hasNext()) {
				Program programtemp = programsByRating.next();
				System.out.printf("%s; %s; %d; ", programtemp.getTitle(),programtemp.getCreator(),programtemp.getDuration());
				if(programtemp.getEpsPerSeason()>0)
					System.out.printf("%d; ",programtemp.getEpsPerSeason());
				System.out.printf("%d+; %d; %s; ",programtemp.getAgeRating(),programtemp.getReleaseYear(),programtemp.getGenre());
				Iterator<String> members = programtemp.iteratorMembers();
					while(members.hasNext()) {
						String memberName =members.next();
						if(!members.hasNext())
							System.out.printf("%s. [%.1f]%n",memberName,programtemp.getRating());
						else
							System.out.printf("%s; ",memberName);
					}
				
			}
		}
		catch(NoOneLoggedException e){
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
		catch(NoProfileSelectedException e) {
			System.out.println(ProgramOutputMessages.NO_PROFILE_SELECTED.getCommandsTexts());
		}
		catch(NoProgramWithParameterException e) {
			System.out.println(ProgramOutputMessages.NO_SHOW_FOUND.getCommandsTexts());
		}
	}

	private static void listByParameter(Management notNetflix, Scanner input,String typeSearch) {
		String parameter = input.nextLine();
		try {
			Iterator<Program> programsByParameter = notNetflix.programsByParameterIterator(parameter,typeSearch);
			while(programsByParameter.hasNext()) {
				Program programtemp=programsByParameter.next();
				System.out.printf("%s; %s; %d; ", programtemp.getTitle(),programtemp.getCreator(),programtemp.getDuration());
				if(programtemp.getEpsPerSeason()>0)
					System.out.printf("%d; ",programtemp.getEpsPerSeason());
				System.out.printf("%d+; %d; %s; ",programtemp.getAgeRating(),programtemp.getReleaseYear(),programtemp.getGenre());
				Iterator<String> members = programtemp.iteratorMembers();
					while(members.hasNext()) {
						String memberName =members.next();
						if(!members.hasNext())
							System.out.printf("%s.%n",memberName);
						else
							System.out.printf("%s; ",memberName);
					}
				
			}
		}
		catch(NoOneLoggedException e){
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
		catch(NoProfileSelectedException e) {
			System.out.println(ProgramOutputMessages.NO_PROFILE_SELECTED.getCommandsTexts());
		}
		catch(NoProgramWithParameterException e) {
			System.out.println(ProgramOutputMessages.NO_SHOW_FOUND.getCommandsTexts());
		}
	}

	private static void infoUser(Management notNetflix) {
		try {
			User currentUser = notNetflix.getCurrentUser();
			printInfoUserAccount(currentUser);
		}
		catch(NoOneLoggedException e) {
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
	}

	private static void printInfoUserAccount(User currentUser) {
		Iterator<String> devices = currentUser.deviceIterator();
		System.out.printf("%s:%n",currentUser.getName());
		System.out.printf("%s (",currentUser.getChannelPlan());
		while(devices.hasNext()) {
			String deviceName = devices.next();
			if(!devices.hasNext())
				System.out.printf("%s).%n",deviceName);
			else
				System.out.printf("%s;",deviceName);
		}
		if(currentUser.getProfilesCounter()==0)
			System.out.println("No profiles defined.");
		else {
			Iterator<Profiles> profilesUser = currentUser.profilesIterator();
			while(profilesUser.hasNext()) {
				Profiles profiles = profilesUser.next();
				System.out.printf("Profile: %s",profiles.getName());
				if(profiles.isChild())
					System.out.printf(" (%d)",profiles.getAgeRating());
				System.out.println(); // espaco entre o profile e os programas
				if(profiles.watchedProgramsCounter()==0)
					System.out.println("Empty list of recently seen shows.");
				else {
					Iterator<Program> programs = profiles.recentProgramsIterator();
					while(programs.hasNext()) {
						Program prog = programs.next();
						if(!programs.hasNext()) {
							System.out.printf("%s.%n",prog.getTitle());
						}
						else {
							System.out.printf("%s; ",prog.getTitle());
						}
					}
					if(profiles.ratedProgramsCounter() == 0)
						;//nao faz nada, omite a linha
					else {
						Iterator<Program> ratedPrograms = profiles.ratedProgramsIterator();
						while(ratedPrograms.hasNext()) {
							Program ratedProg = ratedPrograms.next();
							if(!ratedPrograms.hasNext()) {
								System.out.printf("%s (%d).%n",ratedProg.getTitle(),ratedProg.getProfileRating(profiles.getName()));
							}
							else {
								System.out.printf("%s (%d); ",ratedProg.getTitle(),ratedProg.getProfileRating(profiles.getName()));
							}
						}
					}
				}
			} // end while profiles
		}
	}

	private static void rateProgram(Management notNetflix, Scanner input) {
		String nameProgram = input.nextLine();
		int rating = input.nextInt(); input.nextLine();
		try {
			notNetflix.rate(nameProgram,rating);
			System.out.printf("Thank you for rating %s.%n",nameProgram);
		}
		catch(NoOneLoggedException e) {
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
		catch(NoProfileSelectedException e) {
			System.out.println(ProgramOutputMessages.NO_PROFILE_SELECTED.getCommandsTexts());
		}
		catch(ProgramDoesntExistException e) {
			System.out.println(ProgramOutputMessages.PROGRAM_DEXIST.getCommandsTexts());;
		}
		catch(NotRecentWatchedException e) {
			System.out.println(ProgramOutputMessages.NOT_RECENT_PROG.getCommandsTexts());;
		}
		catch(AlreadyRatedException e) {
			System.out.println(ProgramOutputMessages.ALREADY_RATED_PROG.getCommandsTexts());;

		}
		
	}

	private static void watchProgram(Management notNetflix, Scanner input) {
		String nameProgram = input.nextLine();
		try {
			notNetflix.watchProgram(nameProgram);
			System.out.printf("Loading %s...%n",nameProgram);
		}
		catch(NoOneLoggedException e) {
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
		catch(NoProfileSelectedException e) {
			System.out.println(ProgramOutputMessages.NO_PROFILE_SELECTED.getCommandsTexts());
		}
		catch(ProgramDoesntExistException e) {
			System.out.println(ProgramOutputMessages.PROGRAM_DEXIST.getCommandsTexts());;
		}
		catch(ProgramNotAvailableException e) {
			System.out.println(ProgramOutputMessages.PROGRAM_NAVAL.getCommandsTexts());;

		}
	}

	private static void selectProfileUser(Management notNetflix, Scanner input) {
		String name = input.nextLine();
		try {
			notNetflix.selectUserProfile(name);
			System.out.printf("Welcome %s.%n",name);
		}
		catch(NoOneLoggedException e) {
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
		catch(ProfileDoesntExistException e) {
			System.out.println(ProgramOutputMessages.PROFILE_DEXIST.getCommandsTexts());
		}
	}

	private static void createUserProfile(Management notNetflix, Scanner input) {
		String name = input.nextLine();
		String type = input.nextLine();
		try {
			if(type.equalsIgnoreCase("CHILDREN")) {
				int ageRating = input.nextInt(); input.nextLine();
				notNetflix.createUserProfile(name, ageRating);
			}
			else
				notNetflix.createUserProfile(name);
			System.out.println("New profile added.");
		}
		catch(NoOneLoggedException e) {
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
		catch(ProfileAlreadyExistException e) {
			System.out.printf("There is already a profile %s.%n",name);
		}
		catch(TooMuchProfilesException e) {
			System.out.println("Not possible to add more profiles.");
		}
	}

	private static void membershipUser(Management notNetflix, Scanner input) {
		String plan = input.nextLine();
		try {
			String currentPlanUser = notNetflix.getCurrentUser().getChannelPlan();
			notNetflix.membership(plan);
			System.out.printf("Membership plan was changed from %s to %s.%n",currentPlanUser,plan);
		}
		catch(NoOneLoggedException e) {
			System.out.println("No client is logged in.");
		}
		catch(SamePlanException e) {
			System.out.println("No membership plan change.");
		}
		catch(DownGradeException e) {
			System.out.println("Cannot downgrade membership plan at the moment.");
		}
	}

	private static void logoutUser(Management notNetflix, Scanner input) {
		try {
			User currentUser = notNetflix.getCurrentUser();
			System.out.printf("Goodbye %s (%s still connected).%n",currentUser.getName(),currentUser.getCurrentDevice());
			notNetflix.logout();
		} 
		catch(NoOneLoggedException e) {
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
	}

	private static void disconnectUser(Management notNetflix, Scanner input) {
		try {
			User currentUser = notNetflix.getCurrentUser();
			System.out.printf("Goodbye %s (%s was disconnected).%n",currentUser.getName(),currentUser.getCurrentDevice());
			notNetflix.disconnect();
		}
		catch (NoOneLoggedException e) {
			System.out.println(ProgramOutputMessages.NO_ONE_LOGGED.getCommandsTexts());
		}
		
	}

	private static void loginUser(Management notNetflix, Scanner input) {
		String email = input.nextLine();
		String password = input.nextLine();
		String device = input.nextLine();
		try {
			notNetflix.login(email, password, device);
			User currentUser = notNetflix.getCurrentUser();
			System.out.printf("Welcome %s (%s).%n",currentUser.getName(),device);
		}
		catch (ClientLoggedInException e) {
			System.out.println(ProgramOutputMessages.CLIENT_IS_LOGGED.getCommandsTexts());
		}
		catch (SomeoneLoggedException e) {
			System.out.println(ProgramOutputMessages.SOME_LOGGED.getCommandsTexts());
		}
		catch (AccountDoesNotExistException e) {
			System.out.println(ProgramOutputMessages.ACC_DEXIST.getCommandsTexts());
		}
		catch (WrongPasswordException e) {
			System.out.println(ProgramOutputMessages.WRONG_PASS.getCommandsTexts());
		}
		catch (TooMuchDevicesException e) {
			System.out.println(ProgramOutputMessages.TOO_MDEVICES.getCommandsTexts());
		}
	}

	private static void registerUser(Management notNetflix, Scanner input) {
		String name = input.nextLine();
		String email = input.nextLine();
		String password = input.nextLine();
		String disp = input.nextLine();
		try {
			notNetflix.registAccount(name, email, password, disp);
			System.out.printf("Welcome %s (%s).%n",name,disp);
		}
		catch (SomeoneLoggedException e) {
			System.out.println(ProgramOutputMessages.ANOTHER_CLIENT.getCommandsTexts());
		}
		catch (SameEmailException e) {
			System.out.printf("There is another account with email %s.%n",email);
		}
	}

	private static void uploadProgram(Management notNetflix, Scanner input) {
		int numberOfMovies = input.nextInt(); input.nextLine();
		while(numberOfMovies > 0) {
			String title = input.nextLine();
			String creator = input.nextLine();
			int duration = input.nextInt(); input.nextLine(); // temporadas
			String ageRating = input.nextLine();
			int ageRating2 = Integer.parseInt(ageRating.substring(0, ageRating.length()-1));
			int releaseYear = input.nextInt(); input.nextLine(); // numero episodios
			String genre = input.nextLine();
			int numberOfCastMembers = input.nextInt(); input.nextLine();
			String[] castMembers = new String[numberOfCastMembers];
			for(int i=0;i<numberOfCastMembers;i++)
				castMembers[i] = input.nextLine();
			notNetflix.upload(title, creator, duration, ageRating2, releaseYear, genre, castMembers, 0, FILM);
			numberOfMovies--;
		} 
		int numberOfSeries = input.nextInt(); input.nextLine();
		while(numberOfSeries > 0) {
			String title = input.nextLine();
			String creator = input.nextLine();
			int duration = input.nextInt(); input.nextLine();
			int epsPerSeason = input.nextInt(); input.nextLine();
			String ageRating = input.nextLine();
			int ageRating2 = Integer.parseInt(ageRating.substring(0, ageRating.length()-1));
			int releaseYear = input.nextInt(); input.nextLine();
			String genre = input.nextLine();
			int numberOfCastMembers = input.nextInt(); input.nextLine();
			String[] castMembers = new String[numberOfCastMembers];
			for(int i=0;i<numberOfCastMembers;i++)
				castMembers[i] = input.nextLine();
			notNetflix.upload(title, creator, duration, ageRating2, releaseYear, genre, castMembers, epsPerSeason, SERIES);
			numberOfSeries--;
		}
		System.out.println(ProgramOutputMessages.DATABASE_UPLOADED.getCommandsTexts());
		Iterator<Program> programs = notNetflix.IteratorSortedPrograms();
		while(programs.hasNext()) {
			Program tempProgram = programs.next();
			System.out.printf("%s; %s; ",tempProgram.getTitle(),tempProgram.getCreator());
			System.out.printf("%d; ",tempProgram.getDuration());
			if(tempProgram instanceof SeriesClass) {
				System.out.printf("%d; ",tempProgram.getEpsPerSeason());
			}
			System.out.printf("%d+; %d; %s; ",tempProgram.getAgeRating(),tempProgram.getReleaseYear(),tempProgram.getGenre());
			Iterator<String> membersIterator= tempProgram.iteratorMembers();
			int i=0;
			while(membersIterator.hasNext()&&i<3) {
				String castMember=membersIterator.next();
				if(i == 2 || !membersIterator.hasNext()) {
					System.out.printf("%s.%n",castMember);
				}else {
					System.out.printf("%s; ", castMember);
				}
				i++;
			}
		}
	}
	
	private static Commands getCommand(Scanner input) {
		try {
			String in = input.nextLine().toUpperCase();
			return Commands.valueOf(in);
		} catch (IllegalArgumentException e) {
			return Commands.UNKNOW;
		}
	}
	
}
