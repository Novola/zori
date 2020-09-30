package novola.zori.club.hack.hacks;

import java.util.ArrayList;
import java.util.LinkedList;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.RobotUtils;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.ValidUtils;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.ValidUtils;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;

public class HitBox extends Hack{
	
	public NumberValue width;
	public NumberValue height;
	
	public HitBox() {
		super("HitBox", HackCategory.COMBAT);
		
		this.width = new NumberValue("Width", 1.0D, 0.6D, 5.0D);
		this.height = new NumberValue("Height", 2.2D, 1.8D, 5.0D);
		
		this.addValue(width, height);
	}
	
	@Override
	public String getDescription() {
		return "Change size hit box of entity.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		for(Object object : Utils.getEntityList()) {
			if(!(object instanceof EntityLivingBase)) continue;
			EntityLivingBase entity = (EntityLivingBase)object;
			if(!check(entity)) continue;
			Utils.setEntityBoundingBoxSize(entity, 
					(float)(width.getValue().floatValue()),
					(float)(height.getValue().floatValue()));
		}
		super.onClientTick(event);
	}
	
	@Override
	public void onDisable() {
		for(Object object : Utils.getEntityList()) {
			if(!(object instanceof EntityLivingBase)) continue;
			EntityLivingBase entity = (EntityLivingBase)object;
			EntitySize entitySize = this.getEntitySize(entity);
				Utils.setEntityBoundingBoxSize(entity,
						entitySize.width, entitySize.height);
		}
		super.onDisable();
	}
	
	// TODO This code is very bad. Shit!!!
	public EntitySize getEntitySize(Entity entity) {
		EntitySize entitySize = new EntitySize(0.6F, 1.8F);
		if(entity instanceof EntitySpider) 
			entitySize = new EntitySize(1.4F, 0.9F);
		if(entity instanceof EntityBat) 
			entitySize = new EntitySize(0.5F, 0.9F);
		if(entity instanceof EntityChicken) 
			entitySize = new EntitySize(0.5F, 0.9F);
		if(entity instanceof EntityCow) 
			entitySize = new EntitySize(0.9F, 1.4F);
		if(entity instanceof EntitySheep) 
			entitySize = new EntitySize(0.9F, 1.4F);
		if(entity instanceof EntityShulker) 
			entitySize = new EntitySize(1.0F, 1.0F);
		if(entity instanceof EntityEnderman) 
			entitySize = new EntitySize(0.6F, 2.9F);
		if(entity instanceof EntityGhast) 
			entitySize = new EntitySize(4.0F, 4.0F);
		if(entity instanceof EntityEndermite) 
			entitySize = new EntitySize(0.4F, 0.3F);
		if(entity instanceof EntityGiantZombie) 
			entitySize = new EntitySize(0.6F * 6.0F, 1.8F * 6.0F);
		if(entity instanceof EntityWolf) 
			entitySize = new EntitySize(0.6F, 0.85F);
		if(entity instanceof EntityGuardian) 
			entitySize = new EntitySize(0.85F, 0.85F);
		if(entity instanceof EntitySquid) 
			entitySize = new EntitySize(0.8F, 0.8F);
		if(entity instanceof EntityDragon) 
			entitySize = new EntitySize(16.0F, 8.0F);
		if(entity instanceof EntityRabbit) 
			entitySize = new EntitySize(0.4F, 0.5F);
		return entitySize;
	}
	
	public boolean check(EntityLivingBase entity) {
		if(entity instanceof EntityArmorStand) { return false; }
		if(ValidUtils.isValidEntity(entity)){ return false; }
		if(entity == Wrapper.INSTANCE.player()) { return false; }
		if(entity.isDead) { return false; }
		if(!ValidUtils.isFriendEnemy(entity)) { return false; }
		if(!ValidUtils.isTeam(entity)) { return false; }
		return true;
    }
	
	class EntitySize {
		public float width;
		public float height;
		
		public EntitySize(float width, float height) {
			this.width = width;
			this.height = height;
		}
	}
}
