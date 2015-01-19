package celestibytes.celestialtakeover.proxy;

import net.minecraftforge.common.MinecraftForge;
import celestibytes.celestialtakeover.Resources;
import celestibytes.celestialtakeover.celgui.CelGuiHandler;
import celestibytes.celestialtakeover.temp.ItemClickHandler;

public class ClientProxy extends Proxy {
	
	private CelGuiHandler guiHandler;
	
	@Override
	public CelGuiHandler getGuiHandler() {
		if(guiHandler == null) {
			guiHandler = new CelGuiHandler();
			guiHandler.init(Resources.guiDecor);
		}
		
		return guiHandler;
	}
	
	@Override
	public void registerEventHandlers() {
		System.out.println("register!");
		MinecraftForge.EVENT_BUS.register(new ItemClickHandler());
	}
}
