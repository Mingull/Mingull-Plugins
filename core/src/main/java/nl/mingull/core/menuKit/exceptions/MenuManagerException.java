package nl.mingull.core.menuKit.exceptions;

public class MenuManagerException extends Exception {
	public MenuManagerException() {
		super();
	}

	public MenuManagerException(String message) {
		super("[MenuManager] " + message);
	}

	public MenuManagerException(String message, Throwable cause) {
		super("[MenuManager] " + message, cause);
	}

	public MenuManagerException(Throwable cause) {
		super(cause);
	}
}
