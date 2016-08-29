package me.davidjotta.voxel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.opengl.GL11.glViewport;

import me.davidjotta.voxel.engine.GameEngine;
import me.davidjotta.voxel.engine.IGameLogic;
import me.davidjotta.voxel.engine.Window;
import me.davidjotta.voxel.game.Renderer;

public class VoxelGame implements IGameLogic {
	
	private int direction = 0;
	private float color = 0.0f;
	private final Renderer renderer;
	
	public VoxelGame() {
		renderer = new Renderer();
	}

	@Override
	public void init() throws Exception {
		renderer.init();
	}

	@Override
	public void input(Window window) {
		if (window.isKeyPressed(GLFW_KEY_UP)) {
			direction = 1;
		} else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
			direction = -1;
		} else {
			direction = 0;
		}
	}

	@Override
	public void update(float interval) {
		color += direction * 0.01f;
		if (color > 1) {
			color = 1.0f;
		} else if (color < 0) {
			color = 0.0f;
		}
	}

	@Override
	public void render(Window window) {
		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}
		window.setClearColor(color, color, color, 0.0f);
		renderer.clear();
	}
	
	public static void main(String[] args) {
		try {
			boolean vSync = true;
			IGameLogic gameLogic = new VoxelGame();
			GameEngine gameEngine = new GameEngine("VOXEL", 640, 480, vSync, gameLogic);
			gameEngine.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
