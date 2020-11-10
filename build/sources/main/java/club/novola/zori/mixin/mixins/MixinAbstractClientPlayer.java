package club.novola.zori.mixin.mixins;

import club.novola.zori.capes.CapeUtil;
import club.novola.zori.module.client.Capes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer {
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();

    private Minecraft minecraft;

    public MixinAbstractClientPlayer() {
        this.minecraft = Minecraft.getMinecraft();
    }

    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        if (Capes.enabled) {
            final NetworkPlayerInfo info = this.getPlayerInfo();
            if (info != null && CapeUtil.isCapeUser(info.getGameProfile().getName())) {
                if(info.getGameProfile().getName().equalsIgnoreCase("BrownZombie") || info.getGameProfile().getName().equalsIgnoreCase("Novola")){
                    callbackInfoReturnable.setReturnValue(CapeUtil.capeStuff.get(1));
                }else{
                    callbackInfoReturnable.setReturnValue(CapeUtil.capeStuff.get(0));
                }
             }
        }
    }
}
