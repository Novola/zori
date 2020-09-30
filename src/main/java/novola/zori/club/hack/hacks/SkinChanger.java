package novola.zori.club.hack.hacks;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.SkinChangerManager;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.SkinChangerManager;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;

public class SkinChanger extends Hack { // TODO Add cape
	
	//public BooleanValue skin;
	//public BooleanValue cape;
	
	public ResourceLocation defaultSkin;
	//public ResourceLocation defaultCape;
	
	public SkinChanger() {
		super("SkinChanger", HackCategory.VISUAL);
		
		//skin = new BooleanValue("Skin", true);
		//cape = new BooleanValue("Cape", false);
		
		//this.addValue(skin, cape);
	}
	
	@Override
	public String getDescription() {
		return "Changing your skin/cape. (BETA)";
	}
	
	@Override
	public void onEnable() {
		EntityPlayerSP player = Wrapper.INSTANCE.player();
		
		//if(skin.getValue()) {
			if(defaultSkin == null) defaultSkin = player.getLocationSkin();
			ResourceLocation location = SkinChangerManager.playerTextures.get(Type.SKIN);
			if(location != null && !SkinChangerManager.setTexture(Type.SKIN, player, location)) failed();
		//}
		
//		if(cape.getValue()) {
//			if(defaultCape == null) defaultCape = player.getLocationSkin();
//			ResourceLocation location = SkinChangerManager.playerTextures.get(Type.CAPE);
//			if(location != null && !SkinChangerManager.setTexture(Type.CAPE, player, location)) failed();
//		}
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if(defaultSkin != null 
				&& !SkinChangerManager.setTexture(Type.SKIN, Wrapper.INSTANCE.player(),
						defaultSkin)) failed();
//		if((defaultSkin != null && !SkinChangerManager.setTexture(Type.SKIN, Wrapper.INSTANCE.player(),
//				defaultSkin)) || (defaultCape != null && !SkinChangerManager.setTexture(Type.CAPE, Wrapper.INSTANCE.player(),
//						defaultCape))) failed();
		defaultSkin = null; 
		//defaultCape = null;
		super.onDisable();
	}
	void failed() { ChatUtils.error("SkinChanger: Set texture failed!"); }
	
}
