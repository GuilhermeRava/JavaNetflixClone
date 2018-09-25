package Comparators;
import Programs.*;
import java.util.Comparator;

/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description Our comparator to make the programs first ordered by rating, then by alphabetical order.
 */
public class ComparatorRating implements Comparator<Program>{
	@Override
	public int compare(Program o1, Program o2) {
		if(o1.getRating() < o2.getRating()) {
			return 1; 
		}
		else if(o1.getRating() == o2.getRating()) {
			int i=0;
			if(o1.getTitle().compareTo(o2.getTitle()) > 0) {
				i=1;
			}
			else if(o1.getTitle().compareTo(o2.getTitle()) < 0){
				i=-1;
			}
			return i;
		}
		else {
			return -1;
		}
	}
	
}
