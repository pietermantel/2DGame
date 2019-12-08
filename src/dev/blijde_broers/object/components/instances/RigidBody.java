package dev.blijde_broers.object.components.instances;

import java.awt.Graphics;

import dev.blijde_broers.misc.Texture;
import dev.blijde_broers.misc.collisionComponentParts.IntersectionReturn;
import dev.blijde_broers.misc.math.Line;
import dev.blijde_broers.misc.math.Point;
import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.components.ObjectComponent;

public class RigidBody extends ObjectComponent {

	private Vector2 posVel;
	public float rotVel = 0;
	public boolean xTied = false, yTied = false, rotTied = false;
	public float posResistance = 0, rotResistance = 0, mass = 1;
	public boolean doCollisionResolution = false;
	public boolean gravity = false;

	private Transform transform;
	private CollisionComponent collisionComponent;

	public static int timeStep = 50;
	public static Vector2 gravitationalForce = new Vector2(0, .05f);

	private Line renderImpulse;

	public RigidBody(GameObject parent, Vector2 posVel, float rotVel, boolean xTied, boolean yTied, boolean rotTied,
			float posResistance, float rotResistance, float mass, boolean gravity, boolean doCollisionResolution) {
		super(parent);
		this.posVel = posVel;
		this.rotVel = rotVel;
		this.xTied = xTied;
		this.yTied = yTied;
		this.rotTied = rotTied;
		this.posResistance = posResistance;
		this.rotResistance = rotResistance;
		this.mass = mass;
		this.gravity = gravity;
		transform = parent.getTransform();
		this.doCollisionResolution = doCollisionResolution;
		// if (doCollisionResolution)
		collisionComponent = parent.getComponentManager().getCollisionComponent();
	}

	public RigidBody(GameObject parent, Vector2 posVel, float rotVel, float posResistance, float rotResistance,
			float mass, boolean gravity, boolean doCollisionResolution) {
		super(parent);
		this.posVel = posVel;
		this.rotVel = rotVel;
		this.posResistance = posResistance;
		this.rotResistance = rotResistance;
		this.mass = mass;
		this.gravity = gravity;
		transform = parent.getTransform();
		this.doCollisionResolution = doCollisionResolution;
		// if (doCollisionResolution)
		collisionComponent = parent.getComponentManager().getCollisionComponent();
	}

	public RigidBody(GameObject parent, boolean doCollisionResolution) {
		super(parent);
		this.posVel = new Vector2();
		transform = parent.getTransform();
		this.doCollisionResolution = doCollisionResolution;
		// if (doCollisionResolution)
		collisionComponent = parent.getComponentManager().getCollisionComponent();
	}

	@Override
	public void tick() {
		// if(Math.abs(posVel.x) < (0.001f / mass)) posVel.x = 0f;
		// if(Math.abs(posVel.y) < (0.001f / mass)) posVel.y = 0f;
		if (Float.isFinite(mass)) {
			if (gravity) {
				addPosForce(gravitationalForce.multiply(mass));
			}
			posVel.multiplyThis(1 - (posResistance / mass));
			rotVel *= 1 - (rotResistance / mass);
			Vector2 posVel = new Vector2(this.posVel);
			float rotVel = this.rotVel;
			if (xTied) {
				posVel.x = 0;
			}
			if (yTied) {
				posVel.y = 0;
			}
			if (rotTied) {
				rotVel = 0;
			}
			if (doCollisionResolution) {
				posVel.divideThis(timeStep);
				rotVel /= timeStep;
				for (int i = 0; i < timeStep; i++) {
					if(Float.isNaN(posVel.x)) posVel.x = 0;
					if(Float.isNaN(posVel.y)) posVel.y = 0;
					if(Float.isNaN(transform.mid.x)) transform.mid.x = 0;
					if(Float.isNaN(transform.mid.y)) transform.mid.y = 0;
					transform.mid.increment(posVel);
					transform.rotate(rotVel);
					while (collisionComponent.intersects()) {
						IntersectionReturn[] intersections = collisionComponent.intersection();
						for(int j = 0; j < intersections.length; j++) {
							resoluteToCollision(intersections[j]);
						}
						// break;
						transform.mid.increment(posVel);
						transform.rotate(rotVel);
					}
				}
			} else {
				transform.mid.increment(posVel);
				transform.rotate(rotVel);
			}
		}

	}

	@Override
	public void render(Graphics g) {
		if (renderImpulse != null) {
			Vector2 temp = new Vector2(renderImpulse.v1.asPoint(), renderImpulse.v2.asPoint());
			double rot = temp.getDirection();
			temp = new Vector2(temp.getDist(), 10);
			Transform tr = new Transform(
					renderImpulse.v1.plus(new Vector2(renderImpulse.v1, renderImpulse.v2).divide(2)), temp);
			tr.rotate(rot);
			TextureComponent.renderImage(g, Texture.dot.img, tr);
			TextureComponent.renderImage(g, Texture.dot.img,
					new Transform(new Vector2(renderImpulse.v2), new Vector2(15, 15)));
		}
	}

	private void resoluteToCollision(IntersectionReturn intersection) {
		Point p = intersection.point;
		RigidBody otherRB = intersection.cc.getParent().getComponentManager().getRigidBody();
		while (collisionComponent.intersects()) {
			for (int i = 0; i < 1; i++) {
				transform.mid.increment(posVel.min());
				transform.rotate(-rotVel);
			}
		}
		Vector2 pAsVector2 = p.asVector2();
		Vector2 vThis = pAsVector2.plus(transform.mid.min());
		Vector2 vOther = pAsVector2.plus(otherRB.transform.mid.min());
		double rotVThisDir = vThis.getDirection() + (Math.toRadians(90) * (rotVel / Math.abs(rotVel)));
		double rotVOtherDir = vOther.getDirection()
				+ (Math.toRadians(90) * (otherRB.rotVel / Math.abs(otherRB.rotVel)));
		if (Double.isNaN(rotVThisDir))
			rotVThisDir = 0;
		if (Double.isNaN(rotVOtherDir))
			rotVOtherDir = 0;
		Vector2 thisRotV = new Vector2(rotVel * vThis.getDist(), 0).setDirection(rotVThisDir);
		Vector2 otherRotV = new Vector2(otherRB.rotVel * vOther.getDist(), 0).setDirection(rotVOtherDir);
		Vector2 thisImpulse = new Vector2(posVel, otherRB.posVel).plus(new Vector2(thisRotV, otherRotV));
		Vector2 otherImpulse = new Vector2(otherRB.posVel, posVel).plus(new Vector2(otherRotV, thisRotV));
		double thisImpulseDir = thisImpulse.getDirection();
		double otherImpulseDir = otherImpulse.getDirection();
		double thisImpulseEnergy = (1d / 2d) * mass * Math.pow(thisImpulse.getDist(), 2);
		// + calcRotE(mass, rotVel);
		double otherImpulseEnergy = (1d / 2d) * otherRB.mass * Math.pow(otherImpulse.getDist(), 2);
		// + calcRotE(otherRB.mass, otherRB.rotVel);
		// System.out.println();
		// System.out.println(otherImpulse);
		if (Float.isInfinite(otherRB.mass)) {
			otherImpulseEnergy = thisImpulseEnergy;
		}
		resoluteToImpact(vThis, thisImpulseDir, thisImpulseEnergy);
		// if (otherRB.doCollisionResolution)
		otherRB.resoluteToImpact(vOther, otherImpulseDir, otherImpulseEnergy);
//		System.out.println();
//		System.out.println(pAsVector2);
//		System.out.println(posVel);
//		System.out.println(transform.mid);
//		System.out.println(parent.toString());
//		System.out.println(vThis);
//		System.out.println(thisImpulseDir);
//		System.out.println(thisImpulse);
//		System.out.println();
//		System.out.println(otherRB.parent.toString());
//		System.out.println(vOther);
//		System.out.println(otherImpulseDir);
//		System.out.println(otherImpulse);
		renderImpulse = new Line(p.asVector2().plus(thisImpulse.multiply(10f)), p.asVector2());
		otherRB.renderImpulse = new Line(pAsVector2.plus(otherImpulse.multiply(10f)), p.asVector2());
	}

	private double calcRotE(double mass, double rotVel) {
		// double r = collisionComponent.getCollisionTemplate().getRadius();
		return (1d / 2d) * mass * Math.pow(rotVel, 2);
	}

	private double ErToVr(double E) {
		double r = collisionComponent.getCollisionTemplate().getRadius();
		// double r = 10;
		double omega = Math.sqrt((4 * E) / ((mass / 10) * Math.pow(r, 2)));
		double rotVel = (1 / Math.PI) * Math.sqrt(E / ((mass / 10) * Math.pow(r, 2)));
		return rotVel * omega;
	}

	private double getEtoR(Vector2 impulseFrom, double direction) {
		double maxHeight = collisionComponent.getCollisionTemplate().getRadius();
		Vector2 tempImpulseFrom = new Vector2(impulseFrom);
		tempImpulseFrom.rotate(-direction);
		double EtoR = tempImpulseFrom.y / (maxHeight * 2);
		// System.out.println(EtoR);
		// System.out.println(tempImpulseFrom.y);
		return EtoR;
	}

	public void resoluteToImpact(Vector2 impulseFrom, double impulseDirection, double impulseEnergy) {
		double EtoR = getEtoR(impulseFrom, impulseDirection);
		double EtoP = (1 - Math.abs(EtoR));
		double rotVel = 0;
		if (EtoR != 0) {
			rotVel = ErToVr(Math.abs(impulseEnergy * EtoR)) * (-EtoR / Math.abs(EtoR));
		}

		this.rotVel += rotVel;
//		System.out.println(parent.toString());
//		System.out.println();
//		System.out.println(impulseFrom);
//		System.out.println(impulseDirection);
//		System.out.println(impulseEnergy);
//		System.out.println(EtoR);
//		System.out.println(EtoP);
//		System.out.println(rotVel);
//		System.out.println(posVel);
		addPosForce(impulseDirection, EtoP * impulseEnergy);
	}

	public void addPosForce(double direction, double energy) {
		double v = Math.sqrt((energy * 2)) / mass;
		Vector2 posVel = new Vector2((float) v, 0f).setDirection(direction);
		this.posVel.increment(posVel);
	}

	public void addPosForce(Vector2 force) {
		double energy = force.getDist();
		double direction = force.getDirection();
		double v = Math.sqrt(energy * 2 / mass);
		Vector2 posVel = new Vector2((float) v, 0f).setDirection(direction);
		this.posVel.increment(posVel);
	}

	public void addRotForce(double force) {
		rotVel += force / mass;
	}

	public Vector2 getPosVel() {
		return posVel;
	}

}
