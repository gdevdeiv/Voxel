package me.davidjotta.voxel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import org.joml.Vector2f;
import org.joml.Vector3f;

import me.davidjotta.voxel.engine.GameEngine;
import me.davidjotta.voxel.engine.GameItem;
import me.davidjotta.voxel.engine.IGameLogic;
import me.davidjotta.voxel.engine.MouseInput;
import me.davidjotta.voxel.engine.Window;
import me.davidjotta.voxel.engine.graph.Camera;
import me.davidjotta.voxel.engine.graph.Mesh;
import me.davidjotta.voxel.engine.graph.Texture;
import me.davidjotta.voxel.game.Renderer;
import me.davidjotta.voxel.game.block.BlockGrass;

public class VoxelGame implements IGameLogic {
	
	private static final float MOUSE_SENSITIVITY = 0.4f;
	private final Vector3f cameraInc;
	private final Camera camera;
	private static final float CAMERA_POS_STEP = 0.5f;
	private final Renderer renderer;
	private GameItem[] gameItems;
	
	public VoxelGame() {
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f(0, 0, 0);
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);
		Texture itemTexture = new Texture("/textures/grassblock.png");
		Mesh itemMesh = new Mesh(
			BlockGrass.getPositions(),
			BlockGrass.getTextCoords(),
			BlockGrass.getIndices(),
			itemTexture
		);
		GameItem item = new BlockGrass(itemMesh, 0, 0, 2);
        gameItems = new GameItem[] {item};
        for (GameItem gameItem : gameItems) {
        	System.out.println(gameItem.getPosition().x);
        }
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
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		for (GameItem gameItem : gameItems) {
            gameItem.tick();
        }
		camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
		if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
	}

	@Override
	public void render(Window window) {
		renderer.render(window, camera, gameItems);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
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
