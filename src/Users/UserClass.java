package Users;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Programs.Program;

/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description User Class
 */
public class UserClass implements User {
	private String name,email,password,plan;
	private List<String> devices;
	private List<Profiles> profiles;
	private boolean loggedIn;
	private int deviceCounter,maxDevices,profilesCounter,maxProfiles;
	private String currentDevice;
	private Profiles currentProfile;
	
	public UserClass(String name,String email,String password,String device) {
		this.devices = new ArrayList<String>();
		this.deviceCounter = 0;
		this.maxDevices = 1;
		this.name=name;
		this.email=email;
		this.password=password;
		this.plan="Basic";
		profiles = new ArrayList<Profiles>();
		maxProfiles = 2;
		loggedIn=false;
		currentDevice=null;
		currentProfile=null;
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getEmail() {
		return email;
	}
	
	@Override
	public String getDevice(int pos) {
		return devices.get(pos);
	}
	@Override
	public Iterator<String> deviceIterator() {
		return devices.iterator();
	}
	@Override
	public Iterator<Profiles> profilesIterator() {
		return profiles.iterator();
	}
	@Override
	public int getMaxDevices() {
		return maxDevices;
	}
	@Override
	public int getDevicesCounter() {
		return deviceCounter;
	}
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setChannelPlan(String plan) {
		this.plan = plan;
	}

	@Override
	public String getChannelPlan() {
		return plan;
	}
	@Override
	public void addProfile(String name, int ageRating) {
		profiles.add(new ProfilesClass(name,ageRating));
		profilesCounter++;
	}
	@Override
	public void addProfile(String name) {
		profiles.add(new ProfilesClass(name,18)); //18 padrao de idade adulta
		profilesCounter++;
	}
	@Override
	public int getMaxProfiles() {
		return maxProfiles;
	}
	@Override
	public int getProfilesCounter() {
		return profilesCounter;
	}
	@Override
	public void login(String device) {
		loggedIn=true;
		currentDevice=device;
	}
	@Override
	public void disconnect() {
		loggedIn=false;
		currentDevice=null;
		deviceCounter--;
		currentProfile = null;
	}
	@Override
	public boolean isLogged() {
		return loggedIn;
	}
	@Override
	public int searchDevice(String name) {
		int index=-1;
        boolean found=false;
        for(int i=0;i<deviceCounter&&!found;i++) {
            if(devices.get(i).equals(name)) {
                found=true;
                index=i;
            }
        }
        return index;
	}
	@Override
	public void addDevice(String name) {
		devices.add(name);
		deviceCounter++;
	}
	@Override
	public void removeDevice() {
		devices.remove(searchDevice(currentDevice));
		deviceCounter--;
	}
	@Override
	public String getCurrentDevice() {
		return currentDevice;
	}
	@Override
	public void logoff() {
		loggedIn=false;
		currentProfile = null;
	}
	@Override
	public void membership(String name) {
		if(name.equalsIgnoreCase("Standard")) {
			plan="Standard";
			maxDevices=2;
			maxProfiles=5;
		}
		else if(name.equalsIgnoreCase("Premium")) {
			plan="Premium";
			maxDevices=4;
			maxProfiles=5;
			
		}
		else{
			plan="Basic";
			maxDevices=1;
			maxProfiles=2;
		}
	}
	
	@Override
	public Iterator<Profiles> getProfiles(){
		return profiles.iterator();
	}
	
	@Override
	public boolean existProfile(String name) {
		return searchIndexProfiles(name) >= 0;
	}
	
	private int searchIndexProfiles(String name) {
		int index = -1;
		boolean found=false;
		for(int i=0;i<profilesCounter && !found;i++) {
			if(profiles.get(i).getName().equals(name)) {
				index = i;
				found = true;
			}
		}
		return index;
	}
	@Override
	public void selectProfile(String name) {
		currentProfile = profiles.get(searchIndexProfiles(name));
	}
	
	@Override
	public Profiles getCurrentProfile() {
		return currentProfile;
	}
	
	@Override
	public void watchProgram(Program prog) {
		currentProfile.watchProgram(prog);
	}
	
	@Override
	public int watchedProgramsCounter() {
		return currentProfile.watchedProgramsCounter();
	}
	@Override
	public boolean isChild() {
		return currentProfile.isChild();
	}
	
	@Override
	public Iterator<Program> recentProgramsIterator(){
		return  currentProfile.recentProgramsIterator();
	}
}
