package celestibytes.celestialtakeover.celgui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import celestibytes.celestialtakeover.Resources;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class CelGuiHandler {
	
	private Map<Class<? extends CelGui>, CelGuiIcon> guiIcons;
	
	private LinkedList<CelGui> openGuis;
	
	private ResourceLocation guiDecor;
	
	private CelGui debugGui;
	
	private int decorSize = 4;
	
	private CelGui draggedGui;
	private CelGui resizedGui;
	
	private CelGuiIcon icon_unknown = new CelGuiIcon(Resources.guiDecor, 0f, 8f / 16f, 1f / 16f, 9f / 16f);
	
//
//	INIT
//
	
	public CelGuiHandler() {
		guiIcons = new HashMap<Class<? extends CelGui>, CelGuiIcon>();
	}
	
	public void init(ResourceLocation guiDecor) {
		this.guiDecor = guiDecor;
		debugGui = new CelGui();
		debugGui.x = 16;
		debugGui.y = 16;
		debugGui.width = 128;
		debugGui.height = 128;
		
		// Handle in openGui
		CelGuiIcon ico = getGuiIconForGui(debugGui);
		if(ico != null) {
			debugGui.icon = ico;
		}
	}
	
//
// 	(GUI) HANDLING
//
	
	public void openGui(CelGui gui) {
		
	}
	
	public void closeGui(CelGui gui) {
		
	}
	
	public void mouseButton(int mx, int my, boolean down, int button) {
		if(!down) {
			if(resizedGui != null) {
				if(resizedGui.width == decorSize * 2 && resizedGui.height == 0) {
					debugGui.minimized = true;
				}
			}
			draggedGui = null;
			resizedGui = null;
		} else {
			if(button == 0) {
				if(canDrag(debugGui, mx, my)) {
					draggedGui = debugGui;
				} else if(canResize(debugGui, mx, my)) {
					resizedGui = debugGui;
				} else {
					if((mx >= 2 && mx < 2 + decorSize * 3) && (my >= 2 && my < 2 + decorSize * 3)) {
						debugGui.minimized = false;
						debugGui.width = 64;
						debugGui.height = 64;
					}
				}
				
			}
		}
	}
	
	public void mouseDrag(int dx, int dy, int button) {
		if(button == 0) {
			if(draggedGui != null) {
				draggedGui.x += dx;
				draggedGui.y += dy;
			} else if(resizedGui != null) {
				resizedGui.width += dx;
				resizedGui.height += dy;
				
				if(resizedGui.width < decorSize * 2) {
					resizedGui.width = decorSize * 2;
				}
				if(resizedGui.height < 0) {
					resizedGui.height = 0;
				}
			}
		}
	}
	
	public void screenSizeChange(int scrWidth, int scrHeight) {
		// TODO: keep guis inside the screen region
	}
	
	/**
	 * @return null if gui.icon is valid icon
	 */
	public CelGuiIcon getGuiIconForGui(CelGui gui) {
		if(gui.icon == null) {
			if(guiIcons.containsKey(gui.getClass())) {
				return guiIcons.get(gui.getClass());
			}
			
			return icon_unknown;
		} else {
			if(isIconValid(gui.icon)) {
				return null;
			}
		}
		return icon_unknown;
	}
	
//
//	GUI RENDERING
//
	
	private int minimXIdx = 0, minimYIdx = 0;
	
	/** Used to render all opened guis except for the minimized ones */
	public void renderGUIS() {
		minimXIdx = 0;
		minimYIdx = 0;
		renderGui(debugGui, debugGui.minimized);
	}
	
	/** Used to render minimized guis' icons and pinned windows. TODO: this will also be used to render health, hotbar, etc. */
	public void renderHUD() {
		minimXIdx = 0;
		minimYIdx = 0;
		renderGui(debugGui, true);
	}
	
	/** Render a single gui, whether it's minimized or not */
	private void renderGui(CelGui gui, boolean minimized) {
		Tessellator tes = Tessellator.instance;
		
		if(minimized) {
			tes.startDrawingQuads();
			
			renderGuiIcon(gui, tes, 2 + (minimXIdx * decorSize * 3 + 2), 2 + (minimYIdx * decorSize * 3 + 2), decorSize * 3, decorSize * 3);
			
			tes.draw();
			minimYIdx++;
		} else {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			tes.startDrawingQuads();
			tes.setColorOpaque_F(0.3f, 0.3f, 0.3f);
			tes.addVertex(gui.x, gui.y + gui.height, zLevel);
			tes.addVertex(gui.x + gui.width, gui.y + gui.height, zLevel);
			tes.addVertex(gui.x + gui.width, gui.y, zLevel);
			tes.addVertex(gui.x, gui.y, zLevel);
			tes.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			tes.startDrawingQuads();
			tes.setColorOpaque_F(1f, 1f, 1f);
			renderGuiDecor(gui, tes);
			tes.draw();
		}
	}
	
//
//	COMPONENT RENDERING
//
	
	/** Gui decoration textures must be in the same image file, except the icons */
	private void renderGuiDecor(CelGui gui, Tessellator tes) {
		decorSize = 4;
		
		// Borders
		// Left
		tesTexRect(tes, gui.x-decorSize, gui.y, decorSize, gui.height, 0f, iconHeight, iconWidth, iconHeight*2f);
		
		// Right
		tesTexRect(tes, gui.x+gui.width, gui.y, decorSize, gui.height, 0f, iconHeight*2f, iconWidth, iconHeight*3f);
		
		// Top
		tesTexRect(tes, gui.x+decorSize, gui.y-(decorSize*2), gui.width-(decorSize*2), decorSize*2, 0f, iconHeight*3f, iconWidth, iconHeight*4f);
		
		// Bottom
		tesTexRect(tes, gui.x, gui.y+gui.height, gui.width, decorSize, 0f, iconHeight*4f, iconWidth, iconHeight*5f);
		
		// Corners
		// Top-Left // Icon
//		tesTexRect(tes, gui.x-decorSize, gui.y-(decorSize*2), decorSize*2, decorSize*2, iconWidth, iconHeight, iconWidth*2f, iconHeight*2f);
		renderGuiIcon(gui, tes, gui.x-decorSize, gui.y-(decorSize*2), decorSize*2, decorSize*2);
		
		// Top-Right
		tesTexRect(tes, gui.x+gui.width-decorSize, gui.y-(decorSize*2), decorSize*2, decorSize*2, iconWidth*2f, iconHeight, iconWidth*3f, iconHeight*2f);
		
		// Bottom-Right
		tesTexRect(tes, gui.x+gui.width, gui.y+gui.height, decorSize, decorSize, iconWidth*2f, iconHeight*2f, iconWidth*3f, iconHeight*3f);
		
		// Bottom-Left
		tesTexRect(tes, gui.x-decorSize, gui.y+gui.height, decorSize, decorSize, iconWidth, iconHeight*2f, iconWidth*2f, iconHeight*3f);
	}
	
	private void renderGuiIcon(CelGui gui, Tessellator tes, int x, int y, int width, int height) {
		boolean diffTexture = guiDecor.equals(gui.icon.res);
		if(diffTexture) {
			FMLClientHandler.instance().getClient().getTextureManager().bindTexture(gui.icon.res);
		}
		
		tesTexRect(tes, x, y, width, height, gui.icon.u1, gui.icon.v1, gui.icon.u2, gui.icon.v2);
		
		if(diffTexture) {
			FMLClientHandler.instance().getClient().getTextureManager().bindTexture(guiDecor);
		}
		
		tesTexRect(tes, x, y, width, height, 0f, 0f, iconWidth, iconHeight);
	}
	
//
//	BASIC RENDERING COMPONENTS
//
	
//	RENDER ORDER:
// 	bottom-left
//	bottom-right
//	top-right
//	top-left
	
	private double zLevel = 0d;
	private int texWidth = 256, texHeight = 256;
	
	private float iconWidth = 1f / 16f;
	private float iconHeight = 1f / 16f;
	
	private void setTextureSize(int texWidth, int texHeight) {
		this.texWidth = texHeight;
		this.texHeight = texHeight;
	}
	
	/** Tessellate textured rectangle */
	private void tesTexRect(Tessellator tes, int x, int y, int width, int height, int texX, int texY) {
		tes.addVertexWithUV(x, y+height, zLevel, texX/(double)texWidth, texY+height/(double)texHeight);
		tes.addVertexWithUV(x+width, y+height, zLevel, texX+width/(double)texWidth, texY+height/(double)texHeight);
		tes.addVertexWithUV(x+width, y, zLevel, texX+width/(double)texWidth, texY/(double)texHeight);
		tes.addVertexWithUV(x, y, zLevel, texX/(double)texWidth, texY/(double)texHeight);
	}
	
	/** Tessellate textured rectangle */
	private void tesTexRect(Tessellator tes, int x, int y, int width, int height, float u1, float v1, float u2, float v2) {
		tes.addVertexWithUV(x, y+height, zLevel, u1, v2);
		tes.addVertexWithUV(x+width, y+height, zLevel, u2, v2);
		tes.addVertexWithUV(x+width, y, zLevel, u2, v1);
		tes.addVertexWithUV(x, y, zLevel, u1, v1);
	}
	
//
//	UTIL
//
	
	private boolean isIconValid(CelGuiIcon icon) {
		return icon.res != null;
	}
	
	@SuppressWarnings("unused")
	private float getUMultip(int texWidth) {
		return (float)texWidth / 256f;
	}
	
	@SuppressWarnings("unused")
	private float getVMultip(int texHeight) {
		return (float)texHeight / 256f;
	}
	
	@SuppressWarnings("unused")
	private float getIconU(int index) {
		return (index % 16) / (float)texWidth;
	}
	
	@SuppressWarnings("unused")
	private float getIconV(int index) {
		return (float)(Math.floor(index / 16)) / (float)texWidth;
	}
	
	private boolean canDrag(CelGui gui, int mx, int my) {
		return (mx >= gui.x - decorSize && mx < gui.x + gui.width + decorSize) && (my >= gui.y - (decorSize * 2) && my < gui.y);
	}
	
	private boolean canResize(CelGui gui, int mx, int my) {
		return (mx >= gui.x + gui.width && mx < gui.x + gui.width + decorSize) && (my >= gui.y + gui.height && my < gui.y + gui.height + decorSize);
	}
	
}
