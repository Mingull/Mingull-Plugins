package nl.mingull.crates.enums;

public enum Permission {
	COMMAND_META("meta"),
	COMMAND_CREATE("create"),
	CRATE_COOLDOWN("cooldown."),
	CRATE_COOLDOWN_ADMIN("cooldown."),
	CRATE_INTERACT("interact."),
	CRATE_INTERACT_ADMIN("interact.*"),
	CRATE_UPDATE_NOTIFICATION("update");

	private final String prefix = "mingullcrates.";
	private String key;

	Permission(String key) {
		this.key = prefix + key;
	}

	public String getKey() {
		return key;
	}
}
