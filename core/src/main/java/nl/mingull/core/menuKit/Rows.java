package nl.mingull.core.menuKit;

public class Rows {
	public final int rows;

	private Rows(final int rows) {
		this.rows = rows;
	}

	public int getSlots() {
		return rows * 9;
	}

	public static final Rows ONE = new Rows(1);
	public static final Rows TWO = new Rows(2);
	public static final Rows THREE = new Rows(3);
	public static final Rows FOUR = new Rows(4);
	public static final Rows FIVE = new Rows(5);
	public static final Rows SIX = new Rows(6);
}
