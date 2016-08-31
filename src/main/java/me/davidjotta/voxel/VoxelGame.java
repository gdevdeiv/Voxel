package me.davidjotta.voxel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import org.joml.Vector2f;
import org.joml.Vector3f;

import me.davidjotta.voxel.engine.GameEngine;
import me.davidjotta.voxel.engine.GameItem;
import me.davidjotta.voxel.engine.IGameLogic;
import me.davidjotta.voxel.engine.MouseInput;
import me.davidjotta.voxel.engine.SceneLight;
import me.davidjotta.voxel.engine.Window;
import me.davidjotta.voxel.engine.graph.Camera;
import me.davidjotta.voxel.engine.graph.DirectionalLight;
import me.davidjotta.voxel.engine.graph.Material;
import me.davidjotta.voxel.engine.graph.Mesh;
import me.davidjotta.voxel.engine.graph.OBJLoader;
import me.davidjotta.voxel.engine.graph.PointLight;
import me.davidjotta.voxel.engine.graph.SpotLight;
import me.davidjotta.voxel.engine.graph.Texture;
import me.davidjotta.voxel.game.Hud;
import me.davidjotta.voxel.game.Renderer;

public class VoxelGame implements IGameLogic {
	
	private static final float MOUSE_SENSITIVITY = 0.4f;
	private final Vector3f cameraInc;
	private final Camera camera;
	private static final float CAMERA_POS_STEP = 0.5f;
	private final Renderer renderer;
	private GameItem[] gameItems;
	private Hud hud;
	private SceneLight sceneLight;
	private float lightAngle;
	private float spotAngle = 0;
	private float spotInc = 1;
	
	public VoxelGame() {
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f(0, 0, 0);
		lightAngle = -90;
	}

	@Override
	public void init(Window window) throws Exception {
		renderer.init(window);
		
		Mesh mesh = OBJLoader.loadMesh("/models/cube.obj");
        Texture texture = new Texture("/textures/grassblock.png");
        Material material = new Material(texture, 1f);
        mesh.setMaterial(material);
        GameItem gameItem = new GameItem(mesh, 0, 0, 0);
        gameItems = new GameItem[]{gameItem};
        
        sceneLight = new SceneLight();

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));

        // Point Light
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
        sceneLight.setPointLightList(new PointLight[]{pointLight});

        // Spot Light
        lightPosition = new Vector3f(0, 0.0f, 10f);
        pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
        att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
        pointLight.setAttenuation(att);
        Vector3f coneDir = new Vector3f(0, 0, -1);
        float cutoff = (float) Math.cos(Math.toRadians(140));
        SpotLight spotLight = new SpotLight(pointLight, coneDir, cutoff);
        sceneLight.setSpotLightList(new SpotLight[]{spotLight, new SpotLight(spotLight)});

        lightPosition = new Vector3f(-1, 0, 0);
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity));
        
        hud = new Hud("[X:" + camera.getPosition().x + ",Y:" + camera.getPosition().y + ",Z:" + camera.getPosition().z + "]");
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
        Vector2f rotVec = mouseInput.getDisplVec();
        camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        hud.setStatusText("[X:" + camera.getPosition().x + ",Y:" + camera.getPosition().y + ",Z:" + camera.getPosition().z + "]");
	}

	@Override
	public void render(Window window) {
		hud.updateSize(window);
		renderer.render(window, camera, gameItems, sceneLight, hud);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
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
