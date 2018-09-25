package Exceptions;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description down grade exception, cannot switch channel plan to a lower one
 */
public class DownGradeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public DownGradeException() {
		super();
	}
}
