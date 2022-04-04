package net.ludocrypt.movementvision.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "movement_vision")
public class MovementConfig implements ConfigData {

	public boolean enabled = true;

	public boolean alwaysOn = false;

	public float blurRadius = 1.0F;

	public float trail = 0.95F;

	public float alpha = 0.2F;

	public int blurCount = 5;

	public float brightness = -0.2F;

	public float contrast = 2.0F;

	public boolean monotone = true;

	@ConfigEntry.Gui.CollapsibleObject()
	public VectorTint tint = new VectorTint(0.4784F, 0.5686F, 0.5098F);

	@ConfigEntry.Gui.CollapsibleObject()
	public IncapacitatedCompat incapacitatedSupport = new IncapacitatedCompat();

	public static void init() {
		AutoConfig.register(MovementConfig.class, GsonConfigSerializer::new);
	}

	public static MovementConfig getInstance() {
		return AutoConfig.getConfigHolder(MovementConfig.class).getConfig();
	}

	public static class VectorTint {

		public float r, g, b;

		public VectorTint(float r, float g, float b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

	}

	public static class IncapacitatedCompat {

		public boolean enabled = true;

		public boolean brighten = false;

		public boolean clamp = true;

	}

}
