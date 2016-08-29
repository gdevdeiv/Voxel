package me.davidjotta.voxel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import org.joml.Vector3f;

import me.davidjotta.voxel.engine.GameEngine;
import me.davidjotta.voxel.engine.GameItem;
import me.davidjotta.voxel.engine.IGameLogic;
import me.davidjotta.voxel.engine.Window;
import me.davidjotta.voxel.engine.graph.Mesh;
import me.davidjotta.voxel.engine.graph.Texture;
import me.davidjotta.voxel.game.Renderer;

public class VoxelGame implements IGameLogic {
	
	private int displxInc = 0;
    private int displyInc = 0;
    private int displzInc = 0;
    private int scaleInc = 0;
	private final Renderer renderer;
	private GameItem[] gameItems;
	
	public VoxelGame() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);
		// Create the Mesh
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
            
            // For text coords in top face
            // V8: V4 repeated
            -0.5f, 0.5f, -0.5f,
            // V9: V5 repeated
            0.5f, 0.5f, -0.5f,
            // V10: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V11: V3 repeated
            0.5f, 0.5f, 0.5f,

            // For text coords in right face
            // V12: V3 repeated
            0.5f, 0.5f, 0.5f,
            // V13: V2 repeated
            0.5f, -0.5f, 0.5f,

            // For text coords in left face
            // V14: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V15: V1 repeated
            -0.5f, -0.5f, 0.5f,

            // For text coords in bottom face
            // V16: V6 repeated
            -0.5f, -0.5f, -0.5f,
            // V17: V7 repeated
            0.5f, -0.5f, -0.5f,
            // V18: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // V19: V2 repeated
            0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            
            // For text coords in top face
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,

            // For text coords in right face
            0.0f, 0.0f,
            0.0f, 0.5f,

            // For text coords in left face
            0.5f, 0.0f,
            0.5f, 0.5f,

            // For text coords in bottom face
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
        };
        int[] indices = new int[]{
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            8, 10, 11, 9, 8, 11,
            // Right face
            12, 13, 7, 5, 12, 7,
            // Left face
            14, 15, 6, 4, 14, 6,
            // Bottom face
            16, 18, 19, 17, 16, 19,
            // Back face
            4, 6, 7, 5, 4, 7,};
        Texture texture = new Texture("/textures/grassblock.png");
        Mesh mesh = new Mesh(positions, textCoords, indices, texture);
        GameItem gameItem = new GameItem(mesh);
        gameItem.setPosition(0, 0, -2);
        gameItems = new GameItem[]{gameItem};
	}

	@Override
	public void input(Window window) {
		displyInc = 0;
        displxInc = 0;
        displzInc = 0;
        scaleInc = 0;
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            displyInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            displyInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            displxInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            displxInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_A)) {
            displzInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            displzInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_Z)) {
            scaleInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            scaleInc = 1;
        }
	}

	@Override
	public void update(float interval) {
		for (GameItem gameItem : gameItems) {
            // Update position
            Vector3f itemPos = gameItem.getPosition();
            float posx = itemPos.x + displxInc * 0.01f;
            float posy = itemPos.y + displyInc * 0.01f;
            float posz = itemPos.z + displzInc * 0.01f;
            gameItem.setPosition(posx, posy, posz);

            // Update scale
            Vector3f scale = gameItem.getScale();
            scale.x += scaleInc * 0.05f;
            scale.y += scaleInc * 0.05f;
            scale.z += scaleInc * 0.05f;
            if (scale.x < 0) {
                scale.x = 0;
            }
            if (scale.y < 0) {
                scale.y = 0;
            }
            if (scale.z < 0) {
                scale.z = 0;
            }
            gameItem.setScale(scale.x, scale.y, scale.z);

            // Update rotation angle
            float rotation = gameItem.getRotation().x + 1.5f;
            if (rotation > 360) {
                rotation = 0;
            }
            gameItem.setRotation(rotation, rotation, rotation);
        }
	}

	@Override
	public void render(Window window) {
		renderer.render(window, gameItems);
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
