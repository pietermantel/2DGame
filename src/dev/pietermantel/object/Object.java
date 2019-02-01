package dev.pietermantel.object;

public abstract class Object {
	protected int x, y, id;
	protected Type type;
	
	public Object(int x, int y, int id, Type type) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.type = type;
	}
}
