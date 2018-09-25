package Exceptions;
/**
 * 
 * @author 53415 (Guilherme Mazzei) && 52676 (Pedro Silva) <br>
 * @description account already exist exception
 */
public class AccountExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public AccountExistsException() {
		super();
	}
}
