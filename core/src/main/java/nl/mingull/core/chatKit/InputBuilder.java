package nl.mingull.core.chatKit;

public class InputBuilder {
	public static class Builder {
		private String input;

		public Builder setInput(String input) {
			this.input = input;
			return this;
		}

		public InputBuilder build() {
			return new InputBuilder(this.input);
		}
	}

	private String input;

	private InputBuilder(String input) {
		this.input = input;
	}

	public String getInput() {
		return input;
	}
}
