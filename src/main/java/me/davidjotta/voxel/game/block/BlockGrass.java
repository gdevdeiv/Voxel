package me.davidjotta.voxel.game.block;

import me.davidjotta.voxel.engine.graph.Mesh;

public class BlockGrass extends Block {

	private static final float[] positions = new float[] {
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
	
	public static float[] getPositions() {
		return positions;
	}
	
	private static final float[] textCoords = new float[] {
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
	
	public static float[] getTextCoords() {
		return textCoords;
	}
	
	private static final int[] indices = new int[] {
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
    
	public static int[] getIndices() {
		return indices;
	}
	
	public BlockGrass(float pX, float pY, float pZ) {
		this(pX, pY, pZ, 1, 1, 1);
	}
	
	public BlockGrass(Mesh mesh, float pX, float pY, float pZ) {
		this(mesh, pX, pY, pZ, 1, 1, 1);
	}
	
	public BlockGrass(float pX, float pY, float pZ, float sX, float sY, float sZ) {
		super(pX, pY, pZ, sX, sY, sZ);
	}
	
	public BlockGrass(Mesh mesh, float pX, float pY, float pZ, float sX, float sY, float sZ) {
		super(mesh, pX, pY, pZ, sX, sY, sZ);
	}
}
