package me.davidjotta.voxel.engine;

import org.joml.Vector3f;

import me.davidjotta.voxel.engine.graph.Mesh;

public class GameItem {

	private final Mesh mesh;
	private final Vector3f position;
	private final Vector3f scale;
	private final Vector3f rotation;
	
	public GameItem(Mesh mesh) {
		this.mesh = mesh;
		position = new Vector3f(0, 0, 0);
		scale = new Vector3f(0.3f, 0.3f, 0.3f);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public void setScale(float x, float y, float z) {
		this.scale.x = x;
		this.scale.y = y;
		this.scale.z = z;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
}
