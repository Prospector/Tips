package net.darkhax.tipsmod.common.mixin;

import net.darkhax.tipsmod.common.impl.client.TipManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureManager.class)
public class MixinTextureManager {

    // This may seem like the wrong place to mixin but there is a reason for this.
    // Multiloader projects that still support Forge can not use any of the new
    // features from FabricMixin, including the ability to inject at non-return
    // instructions in constructors. This means we can not inject into the
    // Minecraft class before resource have already been loaded. To get around
    // this limitation we are piggybacking on the TextureManager. If anyone has
    // a problem with this, feel free to submit a PR.
    @Inject(method = "<init>(Lnet/minecraft/server/packs/resources/ResourceManager;)V", at = @At("RETURN"))
    public void onInit(ResourceManager resourceManager, CallbackInfo ci) {
        if (resourceManager instanceof ReloadableResourceManager reloadable) {
            reloadable.registerReloadListener(TipManager.INSTANCE);
        }
    }
}