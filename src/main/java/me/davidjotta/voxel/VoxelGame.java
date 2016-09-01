package me.davidjotta.voxel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector2f;
import org.joml.Vector3f;

import me.davidjotta.voxel.engine.GameEngine;
import me.davidjotta.voxel.engine.IGameLogic;
import me.davidjotta.voxel.engine.MouseInput;
import me.davidjotta.voxel.engine.Window;
import me.davidjotta.voxel.engine.World;
import me.davidjotta.voxel.engine.graph.Camera;
import me.davidjotta.voxel.engine.lib.Reference;
import me.davidjotta.voxel.game.Hud;
import me.davidjotta.voxel.game.Renderer;
import me.davidjotta.voxel.game.block.Block;

public class VoxelGame implements IGameLogic {
	
	private final Vector3f cameraInc;
	private final Camera camera;
	private final Renderer renderer;
	private Hud hud;
	private World world;
	
	public VoxelGame() throws Exception {
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f(0, 0, 0);
		world = new World();
		hud = new Hud();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);
		world.generate();
		world.initLight();
		hud.init();
	}

	@Override
	public void input(Window window, MouseInput mouseInput) {
		cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
        	cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
        	cameraInc.y = 1;
        }
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		camera.movePosition(cameraInc.x * Reference.CAMERA_POS_STEP, cameraInc.y * Reference.CAMERA_POS_STEP, cameraInc.z * Reference.CAMERA_POS_STEP);
        Vector2f rotVec = mouseInput.getDisplVec();
        camera.moveRotation(rotVec.x * Reference.MOUSE_SENSITIVITY, rotVec.y * Reference.MOUSE_SENSITIVITY, 0);
        hud.setStatusText("[X:" + camera.getPosition().x + ",Y:" + camera.getPosition().y + ",Z:" + camera.getPosition().z + "]");
	}

	@Override
	public void render(Window window) {
		hud.updateSize(window);
		renderer.render(window, camera, world.getBlocks(), world.getLight(), hud);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
        for (Block block : world.getBlocks()) {
            block.getMesh().cleanUp();
        }
        hud.cleanup();
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
