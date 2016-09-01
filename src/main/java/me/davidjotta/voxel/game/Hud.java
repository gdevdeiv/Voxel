package me.davidjotta.voxel.game;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import me.davidjotta.voxel.engine.IHud;
import me.davidjotta.voxel.engine.Window;
import me.davidjotta.voxel.engine.graph.FontTexture;
import me.davidjotta.voxel.game.block.Block;

public class Hud implements IHud {

	private static final Font FONT = new Font("Arial", Font.PLAIN, 20);
    private static final String CHARSET = "ISO-8859-1";
    private final List<Block> blocks = new ArrayList<Block>();
    private DebugItem statusTextItem;
    private String statusText;
    
    public Hud() throws Exception {
    	this("");
    }

    public Hud(String statusText) throws Exception {
        this.statusText = statusText;
    }
    
    public void init() throws Exception {
    	FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        this.statusTextItem = new DebugItem(statusText, fontTexture);
        this.statusTextItem.getMesh().getMaterial().setColour(new Vector3f(1, 1, 1));
        blocks.add(statusTextItem);
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    @Override
    public List<Block> getGameItems() {
        return blocks;
    }
   
    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
    }
}
