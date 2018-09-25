package Programs;

public class SeriesClass extends ProgramsAbstractClass implements Series {
	private int epsPerSeason;
	public SeriesClass(String title, String creatorName, int duration, int ageRating, int releaseYear, String genre,String[] castMembers,int epsPerSeason) {
		super(title, creatorName, duration, ageRating, releaseYear, genre, castMembers);
		this.epsPerSeason = epsPerSeason;
	}

	@Override
	public int getEpsPerSeason() {
		return epsPerSeason;
	}

}
