package nl.mingull.core.menuKit.exceptions;

public class MenuManagerNotCreatedException extends Exception {
	private static final long serialVersionUID = 1L;

	public MenuManagerNotCreatedException() {
		super("MenuManager has not been created yet");
	}

}
