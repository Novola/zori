package novola.zori.club.hack.hacks;

import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.EnemyManager;
import novola.zori.club.wrappers.Wrapper;
import org.lwjgl.input.Mouse;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.EnemyManager;
import novola.zori.club.managers.FriendManager;
import novola.zori.club.managers.HackManager;
import novola.zori.club.managers.XRayManager;

import novola.zori.club.utils.Utils;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class InteractClick extends Hack{

	public InteractClick() {
		super("InteractClick", HackCategory.COMBAT);
	}
	
	@Override
	public String getDescription() {
		return "Left - Add to Enemys, Rigth - Add to Friends, Wheel - Remove from All.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
		if(object == null) {
			return;
		}
		if(object.typeOfHit == RayTraceResult.Type.ENTITY) {
			Entity entity = object.entityHit;
			if(entity instanceof EntityPlayer && !(entity instanceof EntityArmorStand) && !Wrapper.INSTANCE.player().isDead && Wrapper.INSTANCE.player().canEntityBeSeen(entity)){
				EntityPlayer player = (EntityPlayer)entity;
				String ID = Utils.getPlayerName(player);
				if(Mouse.isButtonDown(1) && Wrapper.INSTANCE.mc().currentScreen == null) 
				{
					FriendManager.addFriend(ID);
				}
				else if(Mouse.isButtonDown(0) && Wrapper.INSTANCE.mc().currentScreen == null) 
				{
					EnemyManager.addEnemy(ID);
				}
				else if(Mouse.isButtonDown(2) && Wrapper.INSTANCE.mc().currentScreen == null) 
				{
					EnemyManager.removeEnemy(ID);
					FriendManager.removeFriend(ID);
				}
			}
    	}
		super.onClientTick(event);
	}

}
