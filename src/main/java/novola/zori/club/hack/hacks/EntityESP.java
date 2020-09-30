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
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class EntityESP extends Hack{
	
	public EntityESP() {
		super("EntityESP", HackCategory.VISUAL);
	}
	
	@Override
    public String getDescription() {
        return "Allows you to see all of the entities around you.";
    }
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		for (Object object : Utils.getEntityList()) {
			if(object instanceof EntityLivingBase && !(object instanceof EntityArmorStand)) {
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
    			RenderUtils.drawESP(entity, 0.8f, 0.3f, 0.0f, 1.0f, ticks);
    			return;
    		}
    		if(FriendManager.friendsList.contains(ID)) {
    			RenderUtils.drawESP(entity, 0.0f, 0.7f, 1.0f, 1.0f, ticks);
    			return;
    		}
    	}
    	if(HackManager.getHack("Targets").isToggledValue("Murder")) {
    		if(Utils.isMurder(entity)) {
    			RenderUtils.drawESP(entity, 1.0f, 0.0f, 0.8f, 1.0f, ticks);
    			return;
    		}
    		if(Utils.isDetect(entity)) {
    			RenderUtils.drawESP(entity, 0.0f, 0.0f, 1.0f, 1.0f, ticks);
    			return;
    		}
		}
    	if(entity.isInvisible()) {
			RenderUtils.drawESP(entity, 0.0f, 0.0f, 0.0f, 1.0f, ticks);
			return;
    	}
    	if(entity.hurtTime > 0) {
    		RenderUtils.drawESP(entity, 1.0f, 0.0f, 0.0f, 1.0f, ticks);
    		return;
    	}
    	RenderUtils.drawESP(entity, 1.0f, 1.0f, 1.0f, 1.0f, ticks);
    }
}
