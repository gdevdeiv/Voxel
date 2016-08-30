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

public class VoxelGame implements IGameLogic {
	
	private static final float MOUSE_SENSITIVITY = 0.2f;
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
        float[] positions = new float[] {
            // V0
            -0.5f, 0.5f, 0.5f,
            // V1
            -0.5f, -0.5f, 0.5f,
            // V2
            0.5f, -0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            -0.5f, 0.5f, -0.5f,
            // V5
            0.5f, 0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,
            // Top
            // V8: V4
            -0.5f, 0.5f, -0.5f,
            // V9: V5
            0.5f, 0.5f, -0.5f,
            // V10: V0
            -0.5f, 0.5f, 0.5f,
            // V11: V3
            0.5f, 0.5f, 0.5f,
            // Right
            // V12: V3
            0.5f, 0.5f, 0.5f,
            // V13: V2
            0.5f, -0.5f, 0.5f,
            // Left
            // V14: V0
            -0.5f, 0.5f, 0.5f,
            // V15: V1
            -0.5f, -0.5f, 0.5f,
            // Bottom
            // V16: V6
            -0.5f, -0.5f, -0.5f,
            // V17: V7
            0.5f, -0.5f, -0.5f,
            // V18: V1
            -0.5f, -0.5f, 0.5f,
            // V19: V2
            0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[] {
    		// Front
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            // Back
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            // Top
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,
            // Right
            0.0f, 0.0f,
            0.0f, 0.5f,
            // Left
            0.5f, 0.0f,
            0.5f, 0.5f,
            // Bottom
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
        };
        int[] indices = new int[] {
            // Front
            0, 1, 3, 3, 1, 2,
            // Top
            8, 10, 11, 9, 8, 11,
            // Right
            12, 13, 7, 5, 12, 7,
            // Left
            14, 15, 6, 4, 14, 6,
            // Bottom
            16, 18, 19, 17, 16, 19,
            // Back
            4, 6, 7, 5, 4, 7,
        };
        Texture texture = new Texture("/textures/grassblock.png");
        Mesh mesh = new Mesh(positions, textCoords, indices, texture);
        GameItem gameItem = new GameItem(mesh);
        gameItem.setPosition(0, 0, -2);
        gameItems = new GameItem[]{gameItem};
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
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
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
