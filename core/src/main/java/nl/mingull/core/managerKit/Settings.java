package nl.mingull.core.managerKit;

import java.util.HashMap;
import java.util.Map;

public class Settings {
	// settings should have a list of settings
	private final Map<String, Object> settings;

	public Settings() {
		settings = new HashMap<>();
	}

	// settings should have a method to get a setting
	public Object getSetting(String key) {
		return settings.get(key);
	}

	// settings should have a method to set a setting
	public void setSetting(String key, Object value) {
		settings.put(key, value);
	}
}
