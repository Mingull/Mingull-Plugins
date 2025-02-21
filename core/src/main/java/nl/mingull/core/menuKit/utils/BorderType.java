package nl.mingull.core.menuKit.utils;

public enum BorderType {
	FULL("full"), TOP("top"), BOTTOM("bottom"), TOP_BOTTOM("top_bottom"), LEFT("left"), RIGHT(
			"right"), LEFT_RIGHT("left_right"), NONE("none");

	private String type;

	BorderType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static BorderType fromString(String text) {
		for (BorderType b : BorderType.values()) {
			if (b.type.equalsIgnoreCase(text)) {
				return b;
			}
		}
		return null;
	}

}
