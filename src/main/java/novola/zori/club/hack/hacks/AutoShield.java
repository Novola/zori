package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.HackManager;
import novola.zori.club.utils.RobotUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.ValidUtils;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.HackManager;
import novola.zori.club.utils.RobotUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.wrappers.Wrapper;

public class AutoShield extends Hack{
	
	public AutoShield() {
		super("AutoShield", HackCategory.COMBAT);
	}
	
	@Override
	public String getDescription() {
		return "Manages your shield automatically.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(!Utils.screenCheck()) { this.blockByShield(false); }
		super.onClientTick(event);
	}
	
	@Override
	public void onDisable() {
		this.blockByShield(false);
		super.onDisable();
	}
	
	 public void blockByShield(boolean state) {
		if(Wrapper.INSTANCE.player().getHeldItemOffhand().getItem() != Items.SHIELD) return;
			RobotUtils.setMouse(1, state);
	}
	 
	 public static void block(boolean state) {
		AutoShield hack = (AutoShield) HackManager.getHack("AutoShield");
		if(hack.isToggled()) hack.blockByShield(state);
	}
}
