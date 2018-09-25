package Users;

import java.util.Iterator;

import Programs.Program;

/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description User interface
 */
public interface User {
	/**
	 * returns the name of the user account
	 * @return name user account - String
	 */
	String getName();
	/**
	 * returns the email of the user account
	 * @return email of the user account - String
	 */
	String getEmail();
	/**
	 * returns the password of the account
	 * @return password of account - String
	 */
	String getPassword();
	/**
	 * return the device name in the position defined by the parameter
	 * @param pos - index to determine which device name to return
	 * @return device name - String
	 */
	String getDevice(int pos);
	/**
	 * returns the max number of devices that the account can have
	 * @return quantity of max devices - Int
	 */
	int getMaxDevices();
	/**
	 * Returns the max number of profiles that the account can have
	 * @return uantity of max profiles - Int
	 */
	int getMaxProfiles();
	/**
	 * returns the quantity of created profiles of the current account
	 * @return quantity of created profiles - Int
	 */
	int getProfilesCounter();
	/**
	 * returns the quantity of created Devices of the current account
	 * @return quantity of created Devices - Int
	 */
	int getDevicesCounter();
	/**
	 * change the user account plan
	 * @param plan to change to - String 
	 */
	void setChannelPlan(String plan);
	/**
	 * returns the name of the current plan
	 * @return name of current plan - String
	 */
	String getChannelPlan();
	/**
	 * create a CHILDREN profile to the current user account
	 * @param name - name of the profile
	 * @param ageRating - age rating for this children profile
	 */
	void addProfile(String name,int ageRating);
	/**
	 * create a NORMAL profile to the current user account
	 * @param name - name of the profile
	 */
	void addProfile(String name);
	/**
	 * verify if the user account is logged
	 * @return true if the user account is logged
	 */
	boolean isLogged();
	/**
	 * verify if the device exist in the user account
	 * @param name - name of the device
	 * @return -1 if doesnt exist, Int >= 0 if the device exists
	 */
	int searchDevice(String name);
	/**
	 * add a device to the current user account
	 * @param name - name of the device
	 */
	void addDevice(String name);
	/**
	 * returns the name of the current Device
	 * @return name of the current device
	 */
	String getCurrentDevice();
	/**
	 * login the user to the device passed by parameter
	 * @param device - name of the device
	 * @pre searchDevice(device) >= 0
	 */
	void login(String device);
	/**
	 * disconnect the current user account
	 */
	void disconnect();
	/**
	 * remove the current connected device from the user account
	 */
	void removeDevice();
	/**
	 * logoff the current user
	 */
	void logoff();
	/**
	 * change the channel plan of the current user
	 * @param name - plan name
	 */
	void membership(String name);
	/**
	 * Iterator of profiles
	 * @return iterador<Profiles>
	 */
	Iterator<Profiles> getProfiles();
	/**
	 * verify if the profile exist 
	 * @param name - name of the profile
	 * @return true if exists, false otherwise
	 */
	boolean existProfile(String name);
	/**
	 * select the profile to switch to.
	 * @param name - profile name
	 * @pre existProfile(name)
	 */
	void selectProfile(String name);
	/**
	 * return the Profile that is currently logged
	 * @return Profile - Profiles
	 */
	Profiles getCurrentProfile();
	/**
	 * insert the program passed by parameter to the recent watched programs vector
	 * @param prog - SeriesClass || FilmClass
	 */
	void watchProgram(Program prog);
	/**
	 * returns the watched programs of the current profile
	 * @return number of watched programs - Int
	 */
	int watchedProgramsCounter();
	/**
	 * verify if the current profile is of type children
	 * @return returns true if the current profile is children, false otherwise
	 */
	boolean isChild();
	/**
	 * Iterator for the current profile recent watched programs
	 * @return iterator<Program>
	 */
	Iterator<Program> recentProgramsIterator();
	/**
	 * Iterator for the current user account devices.
	 * @return iterator<String>
	 */
	Iterator<String> deviceIterator();
	/**
	 * Iterator for the current user account Profiles
	 * @return iterator<Profiles>
	 */
	Iterator<Profiles> profilesIterator();
}
