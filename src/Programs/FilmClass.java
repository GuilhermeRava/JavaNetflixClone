package Programs;

public class FilmClass extends ProgramsAbstractClass implements Film {

	/**
	 * FilmClass Constructor
	 * @param title
	 * @param creatorName
	 * @param duration
	 * @param ageRating
	 * @param releaseYear
	 * @param genre
	 * @param castMembers
	 */
	public FilmClass(String title, String creatorName, int duration, int ageRating, int releaseYear, String genre,String[] castMembers) {
		super(title, creatorName, duration, ageRating, releaseYear, genre, castMembers);
	}

	@Override
	public int getEpsPerSeason() {
		return 0;
	}
	

}
