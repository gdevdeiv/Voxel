package me.davidjotta.voxel.game.block;

import org.joml.Vector3f;

import me.davidjotta.voxel.engine.graph.Mesh;

public class Block {

	private Mesh mesh;
	private final Vector3f position;
	private final Vector3f scale;
	private final Vector3f rotation;
	
	public Block() {
		position = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Block(Mesh mesh) {
		this.mesh = mesh;
		position = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Block(float pX, float pY, float pZ) {
		position = new Vector3f(pX, pY, pZ);
		scale = new Vector3f(1, 1, 1);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Block(Mesh mesh, float pX, float pY, float pZ) {
		this.mesh = mesh;
		position = new Vector3f(pX, pY, pZ);
		scale = new Vector3f(1, 1, 1);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Block(float pX, float pY, float pZ, float sX, float sY, float sZ) {
		position = new Vector3f(pX, pY, pZ);
		scale = new Vector3f(sX, sY, sZ);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Block(Mesh mesh, float pX, float pY, float pZ, float sX, float sY, float sZ) {
		this.mesh = mesh;
		position = new Vector3f(pX, pY, pZ);
		scale = new Vector3f(sX, sY, sZ);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Block(float pX, float pY, float pZ, float sX, float sY, float sZ, float rX, float rY, float rZ) {
		position = new Vector3f(pX, pY, pZ);
		scale = new Vector3f(sX, sY, sZ);
		rotation = new Vector3f(rX, rY, rZ);
	}
	
	public Block(Mesh mesh, float pX, float pY, float pZ, float sX, float sY, float sZ, float rX, float rY, float rZ) {
		this.mesh = mesh;
		position = new Vector3f(pX, pY, pZ);
		scale = new Vector3f(sX, sY, sZ);
		rotation = new Vector3f(rX, rY, rZ);
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
	
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}
}
