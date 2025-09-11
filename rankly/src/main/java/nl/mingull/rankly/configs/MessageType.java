package nl.mingull.rankly.configs;

public enum MessageType {
	ENTITY_CREATED("entity-created"),
	ENTITY_NOT_FOUND("entity-not-found"),
	ENTITY_ALREADY_EXISTS("entity-already-exists"),
	NO_PERMISSION("no-permission"),
	INVALID_ARGUMENTS("invalid-arguments"),
	COMMAND_ONLY_FOR_PLAYERS("command-only-for-players"),
	PLAYER_STATS("player-stats");

	private final String path;

	MessageType(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}
}
