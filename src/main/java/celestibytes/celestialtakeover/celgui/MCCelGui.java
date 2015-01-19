package celestibytes.celestialtakeover.celgui;

import celestibytes.celestialtakeover.CelestialTakeover;
import celestibytes.celestialtakeover.Resources;
import net.minecraft.client.gui.GuiScreen;

public class MCCelGui extends GuiScreen {
	
	private int lastMX = 0, lastMY = 0;
	private int lastWidth = 0, lastHeight = 0;
	private CelGuiHandler guiHand;
	
	public MCCelGui() {
		guiHand = CelestialTakeover.proxy.getGuiHandler();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void drawBackground(int par1_i) {
		guiHand.renderGUIS();
	}
	
	@Override
	public void drawScreen(int mx, int my, float partTick) {
		super.drawScreen(mx, my, partTick);
		if(width != lastWidth || height != lastHeight) {
			lastWidth = width;
			lastHeight = height;
			guiHand.screenSizeChange(lastWidth, lastHeight);
		}
		try {
			this.mc.getTextureManager().bindTexture(Resources.guiDecor);
			guiHand.renderGUIS();
//			guiHand.renderHUD();
		} catch(Throwable t) {
			System.err.println("EXCEPTION");
		}
	}
	
	// which -1: mouse was moved, which 0 or 1: mouse button up
	@Override
	protected void mouseMovedOrUp(int mx, int my, int which) {
		guiHand.mouseButton(mx, my, false, 0);
		
		super.mouseMovedOrUp(mx, my, which);
	}
	
	@Override
	protected void mouseClickMove(int mx, int my, int lastClickedButton, long timeSinceLastClick) {
		guiHand.mouseDrag(mx-lastMX, my-lastMY, lastClickedButton);
		lastMX = mx;
		lastMY = my;
		
		super.mouseClickMove(mx, my, lastClickedButton, timeSinceLastClick);
	}
	
	@Override
	protected void mouseClicked(int mx, int my, int button) {
		lastMX = mx;
		lastMY = my;
		guiHand.mouseButton(mx, my, true, button);
		
		super.mouseClicked(mx, my, button);
	}
	
}
