package celestibytes.celestialtakeover.temp;

import org.lwjgl.input.Keyboard;

import celestibytes.celestialtakeover.celgui.MCCelGui;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.MouseEvent;

public class ItemClickHandler {
	
	private long lastPress = 0;
	
	@SubscribeEvent
	public void handle(MouseEvent event) {
		long nowPress = System.currentTimeMillis();
		if(nowPress - lastPress > 2000 && Keyboard.isKeyDown(Keyboard.KEY_Y)) {
			FMLClientHandler.instance().getClient().displayGuiScreen(new MCCelGui());
			lastPress = nowPress;
		}
	}
}
