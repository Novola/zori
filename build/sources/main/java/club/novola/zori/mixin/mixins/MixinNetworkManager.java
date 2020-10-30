package club.novola.zori.mixin.mixins;

import club.novola.zori.event.PacketSendEvent;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler< Packet<? >> {
    // uncomment this to get the PacketReceived event, might affect performance a bit
    /*
    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    private void prePacketReceived(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo ci){
        PacketReceivedEvent event = new PacketReceivedEvent(p_channelRead0_2_);
        MinecraftForge.EVENT_BUS.post(event);
        if(event.isCanceled()) ci.cancel();
    }

    @Inject(method = "channelRead0", at = @At("TAIL"), cancellable = true)
    private void postPacketReceived(ChannelHandlerContext p_channelRead0_1_, Packet<?> p_channelRead0_2_, CallbackInfo ci){
        PacketReceivedEvent.Post event = new PacketReceivedEvent.Post(p_channelRead0_2_);
        MinecraftForge.EVENT_BUS.post(event);
    }
     */

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void prePacketSent(Packet<?> packetIn, CallbackInfo ci){
        PacketSendEvent event = new PacketSendEvent(packetIn);
        MinecraftForge.EVENT_BUS.post(event);
        if(event.isCanceled()) ci.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("TAIL"), cancellable = true)
    private void postPacketSent(Packet<?> packetIn, CallbackInfo ci){
        PacketSendEvent.Post event = new PacketSendEvent.Post(packetIn);
        MinecraftForge.EVENT_BUS.post(event);
    }

    // TODO AntiKick module
    /*
    @Inject(method = "exceptionCaught", at = @At("HEAD"), cancellable = true)
    private void antiKick(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_, CallbackInfo ci){
        if (!(p_exceptionCaught_2_ instanceof TimeoutException) && Zori.getInstance().moduleManager.getModuleByName("AntiKick").isEnabled()) {
            ci.cancel();
        }
    }
     */
}
