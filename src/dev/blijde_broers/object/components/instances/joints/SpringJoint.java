package dev.blijde_broers.object.components.instances.joints;

import java.awt.Graphics;

import dev.blijde_broers.misc.math.Transform;
import dev.blijde_broers.misc.math.Vector2;
import dev.blijde_broers.object.GameObject;
import dev.blijde_broers.object.components.ObjectComponent;
import dev.blijde_broers.object.components.instances.RigidBody;
import dev.blijde_broers.object.components.simulations.Simulatable;

public class SpringJoint extends ObjectComponent implements Simulatable {

	private RigidBody otherBody, thisBody;
	private Vector2 thisAttachment, otherAttachment;
	private float staticDistance, springConstant, maxForce = Float.POSITIVE_INFINITY;

	public SpringJoint(GameObject parent, Vector2 thisAttachment, RigidBody otherBody, Vector2 otherAttachment,
			float staticDistance, float springConstant, float maxForce) {
		super(parent);
		this.thisAttachment = thisAttachment;
		this.otherBody = otherBody;
		this.thisBody = parent.getComponentManager().getRigidBody();
		this.otherAttachment = otherAttachment;
		this.staticDistance = staticDistance;
		this.springConstant = springConstant;
		this.maxForce = maxForce;
	}

	public SpringJoint(GameObject parent, Vector2 thisAttachment, RigidBody otherBody, Vector2 otherAttachment,
			float staticDistance, float springConstant) {
		super(parent);
		this.thisAttachment = thisAttachment;
		this.otherBody = otherBody;
		this.thisBody = parent.getComponentManager().getRigidBody();
		this.otherAttachment = otherAttachment;
		this.staticDistance = staticDistance;
		this.springConstant = springConstant;
	}

	public SpringJoint(GameObject parent, Vector2 thisAttachment, RigidBody otherBody, Vector2 otherAttachment,
			float springConstant) {
		super(parent);
		this.thisAttachment = thisAttachment;
		this.otherBody = otherBody;
		this.thisBody = parent.getComponentManager().getRigidBody();
		this.otherAttachment = otherAttachment;
		this.staticDistance = getVectorFromThisToOther().getDist();
		this.springConstant = springConstant;
	}

	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub

	}
	
	private Vector2 getVectorFromThisToOther() {
		Transform thisTransform = thisBody.getParent().getTransform();
		Transform otherTransform = otherBody.getParent().getTransform();
		Vector2 thisAttachment = new Vector2(this.thisAttachment);
		Vector2 otherAttachment = new Vector2(this.otherAttachment);
		Vector2 v2FromThisToOther = new Vector2(
				thisTransform.mid.plus(thisAttachment.rotate(thisTransform.getRotation())),
				otherTransform.mid.plus(otherAttachment.rotate(otherTransform.getRotation())));
		return v2FromThisToOther;
	}

	@Override
	public void simulateTick(int simsPerTick) {
		Vector2 v2FromThisToOther = getVectorFromThisToOther();
		float dist = v2FromThisToOther.getDist();
		if (dist != staticDistance) {
			if (Math.abs(dist - staticDistance) * springConstant > maxForce) {
				int index = parent.getComponentManager().getObjectComponents().indexOf(this);
				parent.getComponentManager().getObjectComponents().remove(index);
			}
			float temp = (dist - staticDistance) / Math.abs(dist - staticDistance);
			thisBody.resoluteToImpulse(thisAttachment, v2FromThisToOther.multiply(temp).getDirection(),
					Math.abs(dist - staticDistance) * springConstant * 0.5f / simsPerTick);
			otherBody.resoluteToImpulse(otherAttachment, v2FromThisToOther.min().multiply(temp).getDirection(),
					Math.abs(dist - staticDistance) * springConstant * 0.5f / simsPerTick);
		}

	}

}
