package net.ludocrypt.movementvision.util;

import com.cartoonishvillain.incapacitated.components.ComponentStarter;
import com.cartoonishvillain.incapacitated.components.PlayerComponent;

import net.ludocrypt.movementvision.config.MovementConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class IncapacitatedSupport {

	public static void renderIncapacitated(float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		PlayerComponent incapacitatedComponent = (PlayerComponent) ComponentStarter.PLAYERCOMPONENTINSTANCE.get(client.player);
		if (incapacitatedComponent.getIsIncapacitated()) {
			DeltaModifier.delta += MovementConfig.getInstance().incapacitatedSupport.brighten ? 0.01F : -0.01F;
			if (MovementConfig.getInstance().incapacitatedSupport.clamp) {
				DeltaModifier.delta = MathHelper.clamp(DeltaModifier.delta, 0.0F, MovementConfig.getInstance().incapacitatedSupport.brighten ? 2.0F : 1.0F);
			}
		} else {
			DeltaModifier.delta = 1.0F;
		}
	}

}
