package celestibytes.celestialtakeover.celgui;

import celestibytes.celestialtakeover.util.Rect;
import net.minecraft.util.ResourceLocation;

public class CelGuiIcon {
	
	protected ResourceLocation res;
	protected float u1, v1, u2, v2;
	
	public CelGuiIcon(ResourceLocation res, float u1, float v1, float u2, float v2) {
		this.res = res;
		this.u1 = u1;
		this.v1 = v1;
		this.u2 = u2;
		this.v2 = v2;
	}
	
	public ResourceLocation getResLoc() {
		return res;
	}
	
	public float getU1() {
		return u1;
	}
	
	public float getV1() {
		return v1;
	}
	
	public float getU2() {
		return u2;
	}
	
	public float getV2() {
		return v2;
	}
	
}
