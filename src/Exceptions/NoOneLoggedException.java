package Exceptions;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description there is no user currently logged in
 */
public class NoOneLoggedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public NoOneLoggedException() {
		super();
	}
}
