package nl.mingull.rankly.stats;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter @EqualsAndHashCode @ToString
public class Stat {
	private final String name;
	private int value;
	private StatChangeListener listener;

	public Stat(String name, int value) {
		this.name = name;
		setValue(value);
	}

	public void setValue(int value) {
		if (value < 0){
			throw new IllegalArgumentException("Stat value cannot be negative");
		}
		int oldValue = this.value;
		this.value = value;
		notifyListener(oldValue, value);
	}

	public void increment() {
		increment(1);
	}

	public void increment(int amount) {
		if (amount < 0){
			throw new IllegalArgumentException("Increment amount cannot be negative");
		}
		setValue(this.value + amount);
	}

	public void decrement() {
		decrement(1);
	}

	public void decrement(int amount) {
		if (amount < 0){
			throw new IllegalArgumentException("Decrement amount cannot be negative");
		}
		if (this.value - amount < 0){
			throw new IllegalArgumentException("Stat value cannot be negative");
		}
		setValue(this.value - amount);
	}

	public void setListener(StatChangeListener listener) {
		this.listener = listener;
	}

	private void notifyListener(int oldValue,int newValue) {
		if (listener != null && oldValue != newValue){
			listener.onStatChanged(this, oldValue, newValue);
		}
	}

	public interface StatChangeListener {
		void onStatChanged(Stat stat,int oldValue,int newValue);
	}
}