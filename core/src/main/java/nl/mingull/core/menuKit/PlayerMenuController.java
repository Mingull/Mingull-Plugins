package nl.mingull.core.menuKit;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.bukkit.entity.Player;

public class PlayerMenuController {
	private final Player owner;
	private final Map<String, Object> data = new HashMap<>();
	private final Stack<Menu> history = new Stack<>();

	public PlayerMenuController(Player owner) {
		this.owner = owner;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	public void setData(String key, Object value) {
		this.data.put(key, value);
	}

	public void setData(Enum<?> key, Object value) {
		this.data.put(key.toString(), value);
	}

	public Object getData(String key) {
		return this.data.get(key);
	}

	public Object getData(Enum<?> key) {
		return this.data.get(key.toString());
	}

	public <T> T getData(String key, Class<T> ref) {
		if (this.data.get(key) == null) {
			return null;
		}
		return ref.cast(this.data.get(key));
	}

	public <T> T getData(Enum<?> key, Class<T> ref) {
		if (this.data.get(key.toString()) == null) {
			return null;
		}
		return ref.cast(this.data.get(key.toString()));
	}

	/**
	 * @return The previous menu in the history
	 */
	public Menu prevMenu() {
		this.history.pop();
		return this.history.pop();
	}

	public void pushMenu(Menu menu) {
		this.history.push(menu);
	}
}
