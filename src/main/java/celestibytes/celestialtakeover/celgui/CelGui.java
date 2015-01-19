package celestibytes.celestialtakeover.celgui;

public class CelGui {
	protected CelGuiIcon icon;
	/** Size doesn't include the borders */
	protected int x, y, width, height;
	protected boolean minimized;
	// TODO: clamped x, clamped y

	public void update(float partTick) {
		
	}
	
	/** Called whenever the Minecraft window is resized or the gui is initially opened. */
	protected void updateGuiRendering(boolean opened, int scrWidth, int scrHeight) {
		if(opened) {
			x = (scrWidth / 2) - (width / 2);
			y = (scrHeight / 2) - (height / 2);
			
		} else {
			
		}
	}
	
	// TODO: public X getPreferredSize() {}
	
}
