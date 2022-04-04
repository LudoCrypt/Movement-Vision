package net.ludocrypt.movementvision;

import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.ludocrypt.movementvision.config.MovementConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.util.Identifier;

public class MovementVision implements ClientModInitializer {

	public static final boolean IS_INCAPACITATED_INSTALLED = FabricLoader.getInstance().isModLoaded("incapacitated");

	private static final MinecraftClient client = MinecraftClient.getInstance();
	public static Framebuffer previousBufferBuffer;
	public static Framebuffer previousBuffer;
	public static Framebuffer trailBuffer;

	public static final ManagedShaderEffect DIFFERENCE_MIX = ShaderEffectManager.getInstance().manage(new Identifier("movementvision", "shaders/post/difference_mix.json"), shader -> {
		previousBufferBuffer.resize(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), MinecraftClient.IS_SYSTEM_MAC);
		previousBuffer.resize(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight(), MinecraftClient.IS_SYSTEM_MAC);
		shader.setSamplerUniform("PrevSampler", previousBuffer);
	});

	public static final ManagedShaderEffect BLUR_MIX = ShaderEffectManager.getInstance().manage(new Identifier("movementvision", "shaders/post/blur_mix.json"));

	public static final ManagedShaderEffect TRAIL_MIX = ShaderEffectManager.getInstance().manage(new Identifier("movementvision", "shaders/post/trail_mix.json"), shader -> {
		shader.setSamplerUniform("PrevSampler", trailBuffer);
	});

	public static final ManagedShaderEffect TINT = ShaderEffectManager.getInstance().manage(new Identifier("movementvision", "shaders/post/tint.json"), shader -> {
		shader.setSamplerUniform("PrevSampler", previousBufferBuffer);
	});

	@Override
	public void onInitializeClient() {
		MovementConfig.init();
	}

}
