package me.davidjotta.voxel.game;

import java.awt.Font;

import org.joml.Vector3f;

import me.davidjotta.voxel.engine.GameItem;
import me.davidjotta.voxel.engine.IHud;
import me.davidjotta.voxel.engine.Window;
import me.davidjotta.voxel.engine.graph.FontTexture;

public class Hud implements IHud {

	private static final Font FONT = new Font("Arial", Font.PLAIN, 20);
    private static final String CHARSET = "ISO-8859-1";
    private final GameItem[] gameItems;
    private final DebugItem statusTextItem;

    public Hud(String statusText) throws Exception {
        FontTexture fontTexture = new FontTexture(FONT, CHARSET);
        this.statusTextItem = new DebugItem(statusText, fontTexture);
        this.statusTextItem.getMesh().getMaterial().setColour(new Vector3f(1, 1, 1));
        gameItems = new GameItem[]{statusTextItem};
    }

    public void setStatusText(String statusText) {
        this.statusTextItem.setText(statusText);
    }

    @Override
    public GameItem[] getGameItems() {
        return gameItems;
    }
   
    public void updateSize(Window window) {
        this.statusTextItem.setPosition(10f, window.getHeight() - 50f, 0);
    }
}
