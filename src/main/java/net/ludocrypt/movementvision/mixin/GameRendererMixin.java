package net.ludocrypt.movementvision.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.ludocrypt.movementvision.MovementVision;
import net.ludocrypt.movementvision.config.MovementConfig;
import net.ludocrypt.movementvision.util.DeltaModifier;
import net.ludocrypt.movementvision.util.IncapacitatedSupport;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;

@Mixin(value = GameRenderer.class, priority = 1420)
public class GameRendererMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawEntityOutlinesFramebuffer()V", shift = Shift.AFTER))
	private void movementVision$render(float tickDelta, long nanoTime, boolean renderLevel, CallbackInfo info) {
		if (MovementConfig.getInstance().enabled) {
			if (MovementConfig.getInstance().alwaysOn) {
				DeltaModifier.delta = 0.0F;
				movementVision$render(tickDelta);
			}
			if (!MovementConfig.getInstance().alwaysOn && MovementVision.IS_INCAPACITATED_INSTALLED && MovementConfig.getInstance().incapacitatedSupport.enabled) {
				movementVision$render(tickDelta);
			} else {
				DeltaModifier.delta = 1.0F;
			}
		}
	}

	@Unique
	private void movementVision$render(float tickDelta) {
		MovementVision.previousBufferBuffer.beginWrite(true);
		client.getFramebuffer().draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
		MovementVision.previousBufferBuffer.endWrite();

		MovementVision.DIFFERENCE_MIX.findUniform1f("alpha").set(MovementConfig.getInstance().alpha);
		MovementVision.DIFFERENCE_MIX.render(tickDelta);

		MovementVision.BLUR_MIX.findUniform1f("blurWidth").set(MovementConfig.getInstance().blurRadius / (float) client.getWindow().getFramebufferWidth());
		MovementVision.BLUR_MIX.findUniform1f("blurHeight").set(MovementConfig.getInstance().blurRadius / (float) client.getWindow().getFramebufferHeight());

		MovementVision.TRAIL_MIX.findUniform3f("trail").set(MovementConfig.getInstance().trail, MovementConfig.getInstance().trail, MovementConfig.getInstance().trail);
		MovementVision.TRAIL_MIX.render(tickDelta);

		for (int i = 0; i < MovementConfig.getInstance().blurCount; i++) {
			MovementVision.BLUR_MIX.render(tickDelta);
		}

		MovementVision.trailBuffer.beginWrite(true);
		client.getFramebuffer().draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
		MovementVision.trailBuffer.endWrite();

		MovementVision.TINT.findUniform3f("tint").set(MovementConfig.getInstance().tint.r, MovementConfig.getInstance().tint.g, MovementConfig.getInstance().tint.b);
		MovementVision.TINT.findUniform1i("monotone").set(MovementConfig.getInstance().monotone ? 1 : 0);
		MovementVision.TINT.findUniform1f("brightness").set(MovementConfig.getInstance().brightness);
		MovementVision.TINT.findUniform1f("contrast").set(MovementConfig.getInstance().contrast);

		MovementVision.TINT.findUniform1f("delta").set(DeltaModifier.delta);
		if (MovementVision.IS_INCAPACITATED_INSTALLED) {
			IncapacitatedSupport.renderIncapacitated(tickDelta);
		}

		MovementVision.TINT.render(tickDelta);

		MovementVision.previousBuffer.beginWrite(true);
		MovementVision.previousBufferBuffer.draw(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
		MovementVision.previousBuffer.endWrite();
	}

}
