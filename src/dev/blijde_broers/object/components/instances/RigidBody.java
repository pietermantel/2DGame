package dev.blijde_broers.object.components.instances;

import java.awt.Graphics;

import dev.blijde_broers.misc.collisionComponentParts.IntersectionReturn;
import dev.blijde_broers.misc.math.Math2D;
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

	private Transform transform;
	private CollisionComponent collisionComponent;

	public static int timeStep = 5;

	public RigidBody(GameObject parent, Vector2 posVel, float rotVel, boolean xTied, boolean yTied, boolean rotTied,
			float posResistance, float rotResistance, float mass, boolean doCollisionResolution) {
		super(parent);
		this.posVel = posVel;
		this.rotVel = rotVel;
		this.xTied = xTied;
		this.yTied = yTied;
		this.rotTied = rotTied;
		this.posResistance = posResistance;
		this.rotResistance = rotResistance;
		this.mass = mass;
		transform = parent.getTransform();
		this.doCollisionResolution = doCollisionResolution;
		if (doCollisionResolution)
			collisionComponent = parent.getComponentManager().getCollisionComponent();
	}

	public RigidBody(GameObject parent, Vector2 posVel, float rotVel, float posResistance, float rotResistance,
			float mass, boolean doCollisionResolution) {
		super(parent);
		this.posVel = posVel;
		this.rotVel = rotVel;
		this.posResistance = posResistance;
		this.rotResistance = rotResistance;
		this.mass = mass;
		transform = parent.getTransform();
		this.doCollisionResolution = doCollisionResolution;
		if (doCollisionResolution)
			collisionComponent = parent.getComponentManager().getCollisionComponent();
	}

	public RigidBody(GameObject parent, boolean doCollisionResolution) {
		super(parent);
		this.posVel = new Vector2();
		transform = parent.getTransform();
		this.doCollisionResolution = doCollisionResolution;
		if (doCollisionResolution)
			collisionComponent = parent.getComponentManager().getCollisionComponent();
	}

	@Override
	public void tick() {
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
				transform.mid.increment(posVel);
				transform.rotate(rotVel);
				if (collisionComponent.intersects()) {
					//// while (collisionComponent.intersects()) {
					// transform.mid.increment(posVel.min());
					// transform.rotate(-rotVel);
					//// }
					// this.posVel = new Vector2();
					// this.rotVel = 0;
					// break;
					IntersectionReturn intersection = collisionComponent.intersection();
					Point p = intersection.point;
					RigidBody otherRB = intersection.cc.getParent().getComponentManager().getRigidBody();
					Vector2 vRelativeForThis = p.asVector2().add(transform.mid.min()).rotate(-transform.getRotation());
					Vector2 vRelativeForOther = p.asVector2().add(otherRB.transform.mid.min())
							.rotate(-otherRB.transform.getRotation());
					double ImpulsedrRelativeForThis = new Vector2(p, otherRB.transform.mid.asPoint()).min()
							.getDirection();
					double ImpulsedrRelativeForOther = new Vector2(p, transform.mid.asPoint()).min().getDirection();
//					double ImpulsedrRelativeForThis = (otherRB.posVel.getDirection() + new Vector2(p, otherRB.transform.mid.asPoint()).min().getDirection()) / 2;
//					double ImpulsedrRelativeForOther = (posVel.getDirection() + new Vector2(p, transform.mid.asPoint()).min().getDirection()) / 2;
					double totalMass = otherRB.mass + mass;
					double EfromThis = (Math2D.dist(new Point(), getPosVel().asPoint()) * mass
							+ rotVel * Math2D.dist(new Point(), vRelativeForThis.asPoint()) * mass);
					double EfromOther = (Math2D.dist(new Point(), otherRB.getPosVel().asPoint()) * otherRB.mass
							+ otherRB.rotVel * Math2D.dist(new Point(), vRelativeForOther.asPoint()) * otherRB.mass);
					double totalEnergy = 0;
					if (!Double.isNaN(EfromThis)) {
						totalEnergy += EfromThis;
					}
					if (!Double.isNaN(EfromOther)) {
						totalEnergy += EfromOther;
					}
					double EforThis = 0.5d;
					double EforOther = 0.5d;
					if (mass != Double.POSITIVE_INFINITY && otherRB.mass != Double.POSITIVE_INFINITY) {
						EforThis = totalEnergy * (1 - (mass / totalMass));
						EforOther = totalEnergy * (1 - (otherRB.mass / totalMass));
					} else {
						if (mass == Double.POSITIVE_INFINITY && otherRB.mass != Double.POSITIVE_INFINITY) {
							EforOther = totalEnergy;
						} else if (otherRB.mass == Double.POSITIVE_INFINITY && mass != Double.POSITIVE_INFINITY) {
							EforThis = totalEnergy;
						} else if (mass == Double.POSITIVE_INFINITY && otherRB.mass == Double.POSITIVE_INFINITY) {
							EforThis = 0;
							EforOther = 0;
						}
					}
					if (!Double.isNaN(EforOther) && EforOther != Double.POSITIVE_INFINITY)
						otherRB.resoluteToImpact(vRelativeForOther, ImpulsedrRelativeForOther, EforOther / timeStep);
					if (!Double.isNaN(EforThis) && EforThis != Double.POSITIVE_INFINITY)
						resoluteToImpact(vRelativeForThis, ImpulsedrRelativeForThis, EforThis / timeStep);
//					break;
				}
			}
		} else {
			transform.mid.increment(posVel);
			transform.rotate(rotVel);
		}

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}

	private double ErToVr(double E) {
		double r = collisionComponent.getCollisionTemplate().getRadius();
		double omega = Math.sqrt((4 * E) / (mass / 10 * Math.pow(r, 2)));
		double rotVel = (1 / Math.PI) * Math.sqrt(E / (mass / 10 * Math.pow(r, 2)));
		return rotVel * omega;
	}

	private double getEtoR(Vector2 impulseFrom, double direction) {
		double maxHeight = collisionComponent.getCollisionTemplate().getRadius();
		Vector2 tempImpulseFrom = new Vector2(impulseFrom);
		tempImpulseFrom.rotate(-direction);
		double EtoR = tempImpulseFrom.y / maxHeight;
		return EtoR;
	}

	public void resoluteToImpact(Vector2 impulseFrom, double impulseDirection, double impulseEnergy) {
		double EtoR = getEtoR(impulseFrom, impulseDirection);
		double EtoP = (1 - Math.abs(EtoR));
		double rotVel = 0;
		if (EtoR != 0) {
			rotVel = ErToVr(Math.abs(impulseEnergy * EtoR)) * (EtoR / Math.abs(EtoR));
		}
		Vector2 posVel = new Vector2(1, 0).setDirection(impulseDirection).multiply((float) (EtoP * impulseEnergy));

		this.rotVel += rotVel;
		// System.out.println();
		// System.out.println(average);
		// System.out.println(totalEnergy);
		// System.out.println(EtoR);
		// System.out.println(EtoP);
		// System.out.println(rotVel);
		// System.out.println(posVel);
		addPosForce(posVel);
		// System.out.println("resolute");
	}

	public void addPosForce(Vector2 force) {
		posVel.increment(force.divide(mass));
	}

	public void addRotForce(double force) {
		rotVel += force / mass;
	}

	public Vector2 getPosVel() {
		return posVel;
	}

}
