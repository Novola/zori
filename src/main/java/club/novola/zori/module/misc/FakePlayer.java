package club.novola.zori.module.misc;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.UUID;

public class FakePlayer extends Module {

    public Boolean enabled = false;

    public FakePlayer() {
        super("FakePlayer", Category.MISC);
    }

    private EntityOtherPlayerMP fake_player;

    public void onUpdate(){
        if(Wrapper.mc.world == null || Wrapper.mc.player == null){
            this.disable();
        }
    }

    public void onEnable() {
        fake_player = new EntityOtherPlayerMP(Wrapper.mc.world, new GameProfile(UUID.fromString("5b6a5015-a6eb-405d-bee6-a0a3e0514129"), "poop"));
        fake_player.copyLocationAndAnglesFrom(Wrapper.mc.player);
        fake_player.rotationYawHead = Wrapper.mc.player.rotationYawHead;
        Wrapper.mc.world.addEntityToWorld(-100, fake_player);
        enabled = true;
    }

    public void onDisable() {
        try {
            Wrapper.mc.world.removeEntity(fake_player);
        } catch (Exception ignored) {}
    }

    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if(enabled){
            this.disable();
        }
    }
}
