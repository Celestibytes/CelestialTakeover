package celestibytes.celestialtakeover;

import celestibytes.celestialtakeover.proxy.Proxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid=CelestialTakeover.Ref.MOD_ID, name=CelestialTakeover.Ref.MOD_NAME)
public class CelestialTakeover {
	
	@SidedProxy(clientSide=CelestialTakeover.Ref.PROXY_CLIENT, serverSide=CelestialTakeover.Ref.PROXY_SERVER)
	public static Proxy proxy;
	
	public static final class Ref {
		public static final String MOD_ID = "celestialtakeover";
		public static final String MOD_NAME = "Celestial Takeover";
		
		public static final String PROXY_CLIENT = "celestibytes.celestialtakeover.proxy.ClientProxy";
		public static final String PROXY_SERVER = "celestibytes.celestialtakeover.proxy.ServerProxy";
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.registerEventHandlers();
	}
}
