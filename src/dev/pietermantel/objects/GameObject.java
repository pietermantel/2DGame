package dev.pietermantel.objects;

public abstract class GameObject {
	protected int x, y, id;
	protected Type type;
	
	public GameObject(int x, int y, int id, Type type) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.type = type;
	}
}
