package nl.mingull.core.chatKit;

public class ChatBuilder {
	private String input;

	private ChatBuilder(String input) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}
}
