package net.ludocrypt.movementvision.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.ludocrypt.movementvision.MovementVision;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gl.WindowFramebuffer;
import net.minecraft.client.util.Window;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Shadow
	@Final
	private Window window;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void movementVision$init(RunArgs args, CallbackInfo ci) {
		MovementVision.previousBufferBuffer = new WindowFramebuffer(this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
		MovementVision.previousBufferBuffer.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MovementVision.previousBufferBuffer.clear(MinecraftClient.IS_SYSTEM_MAC);

		MovementVision.previousBuffer = new WindowFramebuffer(this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
		MovementVision.previousBuffer.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MovementVision.previousBuffer.clear(MinecraftClient.IS_SYSTEM_MAC);

		MovementVision.trailBuffer = new WindowFramebuffer(this.window.getFramebufferWidth(), this.window.getFramebufferHeight());
		MovementVision.trailBuffer.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		MovementVision.trailBuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
	}

}
