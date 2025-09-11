package nl.mingull.skywars.configs;

public enum MessageType {
	ENTITY_CREATED("entity-created"),
	ENTITY_NOT_FOUND("entity-not-found"),
	ENTITY_ALREADY_EXISTS("entity-already-exists"),
	GAME_NOT_JOINABLE("game-not-joinable"),
	GAME_FULL("game-full"),
	GAME_JOIN("game-join"),
	GAME_LEAVE("game-leave"),
	NO_PERMISSION("no-permission"),
	INVALID_ARGUMENTS("invalid-arguments"),
	COMMAND_ONLY_FOR_PLAYERS("command-only-for-players");

	private final String path;

	MessageType(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}
}
