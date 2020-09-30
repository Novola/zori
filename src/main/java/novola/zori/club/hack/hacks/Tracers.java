package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.EnemyManager;
import novola.zori.club.managers.FriendManager;
import novola.zori.club.managers.HackManager;

import novola.zori.club.utils.Utils;
import novola.zori.club.utils.ValidUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.EnemyManager;
import novola.zori.club.managers.FriendManager;
import novola.zori.club.managers.HackManager;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.ValidUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.wrappers.Wrapper;

public class Tracers extends Hack{
    
	public Tracers() {
		super("Tracers", HackCategory.VISUAL);
	}
	
	@Override
    public String getDescription() {
        return "Traces a line to the players.";
    }
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		for (Object object : Utils.getEntityList()) {
			if(object instanceof EntityLivingBase  && !(object instanceof EntityArmorStand)) {
				EntityLivingBase entity = (EntityLivingBase)object;
	    		  this.render(entity, event.getPartialTicks());
			}
		}
		super.onRenderWorldLast(event);
	}
	
	void render(EntityLivingBase entity, float ticks) {
    	if(ValidUtils.isValidEntity(entity) || entity == Wrapper.INSTANCE.player()) {
			return;
    	}
    	if(entity instanceof EntityPlayer) {
    		EntityPlayer player = (EntityPlayer)entity;
    		String ID = Utils.getPlayerName(player);
    		if(EnemyManager.enemysList.contains(ID)) {
    			RenderUtils.drawTracer(entity, 0.8f, 0.3f, 0.0f, 1.0f, ticks);
    			return;
    		}
    		if(FriendManager.friendsList.contains(ID)) {
    			RenderUtils.drawTracer(entity, 0.0f, 0.7f, 1.0f, 1.0f, ticks);
    			return;
    		}
    	}
    	if(HackManager.getHack("Targets").isToggledValue("Murder")) {
    		if(Utils.isMurder(entity)) {
    			RenderUtils.drawTracer(entity, 1.0f, 0.0f, 0.8f, 1.0f, ticks);
        		return;
    		}
    		if(Utils.isDetect(entity)) {
    			RenderUtils.drawTracer(entity, 0.0f, 0.0f, 1.0f, 0.5f, ticks);
        		return;
    		}
		} 
    	if(entity.isInvisible()) {
			RenderUtils.drawTracer(entity, 0.0f, 0.0f, 0.0f, 0.5f, ticks);
			return;
    	}
    	if(entity.hurtTime > 0) {
    		RenderUtils.drawTracer(entity, 1.0f, 0.0f, 0.0f, 1.0f, ticks);
    		return;
    	}
    	RenderUtils.drawTracer(entity, 1.0f, 1.0f, 1.0f, 0.5f, ticks);
    }
}
