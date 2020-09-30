package novola.zori.club.hack.hacks;

import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.SkinChangerManager;
import novola.zori.club.wrappers.Wrapper;
import org.lwjgl.input.Mouse;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.EnemyManager;
import novola.zori.club.managers.FriendManager;
import novola.zori.club.managers.SkinChangerManager;
import novola.zori.club.utils.RobotUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.utils.system.Connection.Side;

public class SkinStealer extends Hack{
	
	public EntityPlayer currentPlayer;
	
	public SkinStealer() {
		super("SkinStealer", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Left click on player - steal skin.";
	}
	
	@Override
	public void onDisable() {
		currentPlayer = null;
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
		if(object == null) return;
		if(object.typeOfHit == RayTraceResult.Type.ENTITY) {
			Entity entity = object.entityHit;
			if(entity instanceof EntityPlayer 
					&& !(entity instanceof EntityArmorStand) 
					&& !Wrapper.INSTANCE.player().isDead 
					&& Wrapper.INSTANCE.player().canEntityBeSeen(entity))
			{
				EntityPlayer player = (EntityPlayer)entity;
				if(Mouse.isButtonDown(0) 
						&& Wrapper.INSTANCE.mc().currentScreen == null 
						&& player != currentPlayer 
						&& player.getGameProfile() != null) 
				{
					SkinChangerManager.addTexture(Type.SKIN, player.getGameProfile().getName());
					currentPlayer = player;
				}

			}
    	}
		super.onClientTick(event);
	}
}
