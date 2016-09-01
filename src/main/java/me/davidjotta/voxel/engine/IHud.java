package me.davidjotta.voxel.engine;

import java.util.List;

import me.davidjotta.voxel.game.block.Block;

public interface IHud {

	List<Block> getGameItems();
	
	default void cleanup() {
		for (Block block : getGameItems()) {
			block.getMesh().cleanUp();
		}
	}
}
