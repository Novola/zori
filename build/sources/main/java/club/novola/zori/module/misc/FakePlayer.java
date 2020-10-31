package club.novola.zori.module.misc;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

public class FakePlayer extends Module {

    public FakePlayer() {
        super("FakePlayer", Category.RENDER);
    }

    private EntityOtherPlayerMP fake_player;

    public void onUpdate(){
        if(Wrapper.mc.world == null){
            this.disable();
        }
    }

    public void onEnable() {
        fake_player = new EntityOtherPlayerMP(Wrapper.mc.world, new GameProfile(UUID.fromString("5b6a5015-a6eb-405d-bee6-a0a3e0514129"), "poop"));
        fake_player.copyLocationAndAnglesFrom(Wrapper.mc.player);
        fake_player.rotationYawHead = Wrapper.mc.player.rotationYawHead;
        Wrapper.mc.world.addEntityToWorld(-100, fake_player);
    }

    public void onDisable() {
        try {
            Wrapper.mc.world.removeEntity(fake_player);
        } catch (Exception ignored) {}
    }
}
