package novola.zori.club.hack.hacks;

import java.lang.reflect.Field;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.BlockUtils;
import novola.zori.club.utils.PlayerControllerUtils;
import novola.zori.club.utils.system.Mapping;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.BlockUtils;
import novola.zori.club.utils.PlayerControllerUtils;
import novola.zori.club.wrappers.Wrapper;

public class FastBreak extends Hack{
	
	public FastBreak() {
		super("FastBreak", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to break blocks faster.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		PlayerControllerUtils.setBlockHitDelay(0);
		super.onClientTick(event);
	}
	
    @Override
    public void onLeftClickBlock(LeftClickBlock event) {
    	float progress = PlayerControllerUtils.getCurBlockDamageMP() + BlockUtils.getHardness(event.getPos());
    	if(progress >= 1) return;
    	Wrapper.INSTANCE.sendPacket(new CPacketPlayerDigging(
    			CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(),
    			Wrapper.INSTANCE.mc().objectMouseOver.sideHit));
    	super.onLeftClickBlock(event);
    }
	
}
