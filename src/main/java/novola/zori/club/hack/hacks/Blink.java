package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Entity301;

import novola.zori.club.utils.system.Connection;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class Blink extends Hack{

	public Entity301 entity301 = null;
	
	public Blink() {
		super("Blink", HackCategory.PLAYER);
	}
	
	@Override
    public String getDescription() {
        return "Allows you to move without sending it to the server.";
    }

	@Override
	public boolean onPacket(Object packet, Side side) {
		return !(side == Connection.Side.OUT && (packet instanceof CPacketPlayer
                || packet instanceof CPacketPlayer.Position
                || packet instanceof CPacketPlayer.Rotation
                || packet instanceof CPacketPlayer.PositionRotation));
	}
	
	@Override
	public void onEnable() {
		if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.world() != null) {
            this.entity301 = new Entity301(Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            this.entity301.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.entity301.inventory = Wrapper.INSTANCE.inventory();
            this.entity301.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.entity301.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.entity301.rotationYawHead = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntity(this.entity301);
        }
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if (this.entity301 != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.world().removeEntity((Entity) this.entity301);
            this.entity301 = null;
        }
		super.onDisable();
	}
}
