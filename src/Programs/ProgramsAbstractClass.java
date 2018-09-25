package Programs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

abstract class ProgramsAbstractClass implements Program {
	private String title,creatorName,genre;
	private List<String> castMembers;
	private int castMembersCounter;
	private int duration,ageRating,releaseYear,ratingCounter;
	private float rating;
	private SortedMap<String,Integer> userRatings;

	public ProgramsAbstractClass(String title,String creatorName,int duration,int ageRating,int releaseYear,String genre,String[] castMembers) {
		this.title = title;
		this.creatorName = creatorName;
		this.duration = duration;
		this.ageRating = ageRating;
		this.releaseYear = releaseYear;
		this.genre = genre;
		this.castMembers = new ArrayList<String>(castMembers.length);
		this.castMembersCounter=0;
		this.ratingCounter=0;
		userRatings = new TreeMap<String,Integer>();
		for(int i=0;i<castMembers.length;i++) {
			this.castMembers.add(castMembers[i]);
			castMembersCounter++;	
		}
	}
	
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getCreator() {
		return creatorName;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public abstract int getEpsPerSeason();

	@Override
	public int getAgeRating() {
		return ageRating;
	}

	@Override
	public int getReleaseYear() {
		return releaseYear;
	}

	@Override
	public String getGenre() {
		return genre;
	}

	@Override
	public Iterator<String> iteratorMembers() {
		return castMembers.iterator();
	}

	@Override
	public float getRating() {
		return rating/ratingCounter;
	}
	
	@Override
	public void rateProgram(String nameProfile,int rating) {
		ratingCounter++;
		userRatings.put(nameProfile, rating);
		this.rating += rating;
	}
	
	@Override
	public Integer getProfileRating(String profileName) {
		return userRatings.get(profileName);
	}
	@Override
	public int getCastMembersCounter() {
		return castMembersCounter;
	}
}
