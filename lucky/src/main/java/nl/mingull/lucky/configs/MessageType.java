package nl.mingull.lucky.configs;

public enum MessageType {
	COMMAND_ONLY_FOR_PLAYERS("command-only-for-players");
	private final String path;

	MessageType(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}
}
