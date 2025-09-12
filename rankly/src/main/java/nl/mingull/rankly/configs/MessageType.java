package nl.mingull.rankly.configs;

public enum MessageType {
	ENTITY_CREATED("entity-created"),
	ENTITY_NOT_FOUND("entity-not-found"),
	ENTITY_ALREADY_EXISTS("entity-already-exists"),
	NO_PERMISSION("no-permission"),
	INVALID_ARGUMENTS("invalid-arguments"),
	COMMAND_ONLY_FOR_PLAYERS("command-only-for-players"),
	PLAYER_STATS("player-stats", true);

	private final String path;
	private final boolean isList;

	MessageType(String path) {
		this(path, false);
	}

	MessageType(String path, boolean isList) {
		this.path = path;
		this.isList = isList;
	}

	public String getPath() {
		return this.path;
	}

	public boolean isList() {
		return isList;
	}
}
