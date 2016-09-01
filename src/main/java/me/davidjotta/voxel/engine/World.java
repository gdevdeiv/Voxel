package me.davidjotta.voxel.engine;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import me.davidjotta.voxel.engine.graph.DirectionalLight;
import me.davidjotta.voxel.engine.graph.Material;
import me.davidjotta.voxel.engine.graph.Mesh;
import me.davidjotta.voxel.engine.graph.OBJLoader;
import me.davidjotta.voxel.engine.graph.Texture;
import me.davidjotta.voxel.engine.lib.OpenSimplexNoise;
import me.davidjotta.voxel.game.block.Block;
import me.davidjotta.voxel.game.block.BlockGrass;

public class World {
	
	private long seed;
	private OpenSimplexNoise noise;
	private List<Block> blocks = new ArrayList<Block>();
	private SceneLight sceneLight;
	
	public World() {
		this.seed = 0;
		this.noise = new OpenSimplexNoise(seed);
	}
	
	public World(long seed) {
		this.seed = seed;
		this.noise = new OpenSimplexNoise(seed);
	}
	
	public void generate() throws Exception {
		Mesh mesh = OBJLoader.loadMesh("/models/cube.obj");
        Texture texture = new Texture("/textures/grassblock.png");
        Material material = new Material(texture, 1f);
        mesh.setMaterial(material);
		for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
        		for (int z = 0; z < 16; z++) {
        			double val = noise.eval(x, y, z);
        			if (val > 0) {
        				BlockGrass block = new BlockGrass(mesh, x, y, z, 0.5f, 0.5f, 0.5f);
        				blocks.add(block);
        			}
        		}
        	}
        }
	}
	
	public void initLight() {
		sceneLight = new SceneLight();
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setDirectionalLight(new DirectionalLight(new Vector3f(1, 1, 1), new Vector3f(0, 0, 1), 1.0f));
	}
	
	public SceneLight getLight() {
		return sceneLight;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}
}
