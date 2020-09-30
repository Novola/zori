package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.BlockUtils;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class Parkour extends Hack{
	
	public Parkour() {
		super("Parkour", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Jump when reaching a block's edge.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Utils.isBlockEdge(Wrapper.INSTANCE.player())
				&& !Wrapper.INSTANCE.player().isSneaking()) 
			Wrapper.INSTANCE.player().jump();
		super.onClientTick(event);
	}
	
}
