package novola.zori.club.hack.hacks;

import java.lang.reflect.Field;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.EnemyManager;
import novola.zori.club.managers.FriendManager;
import novola.zori.club.managers.HackManager;

import novola.zori.club.utils.Utils;
import novola.zori.club.utils.ValidUtils;
import novola.zori.club.utils.system.Mapping;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class BowAimBot extends Hack{

    public BooleanValue walls;
    public NumberValue yaw;
    public NumberValue FOV;
    
    public EntityLivingBase target;
    public float rangeAimVelocity = 0;
    
	public BowAimBot() {
		super("BowAimBot", HackCategory.COMBAT);
		walls = new BooleanValue("ThroughWalls", false);
		yaw = new NumberValue("Yaw", 22.0D, 0D, 50D);
		FOV = new NumberValue("FOV", 90D, 1D, 180D);
		this.addValue(walls, yaw, FOV);
	}
	
	@Override
	public String getDescription() {
		return "Automatically aims your bow at the closest entity.";
	}
	
	@Override
	public void onDisable() {
		this.target = null;
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		ItemStack itemStack = Wrapper.INSTANCE.inventory().getCurrentItem();
		if(itemStack == null || !(itemStack.getItem() instanceof ItemBow)) 
			return;
		
		if(!Wrapper.INSTANCE.mcSettings().keyBindUseItem.isKeyDown()) 
			return;
		
		this.target = this.getClosestEntity();
		if(this.target == null) 
			return;
		
		int rangeCharge = Wrapper.INSTANCE.player().getItemInUseCount();
		
		rangeAimVelocity = rangeCharge / 20;
		rangeAimVelocity = (rangeAimVelocity * rangeAimVelocity + rangeAimVelocity * 2) / 3;
		rangeAimVelocity = 1;
		
		if(rangeAimVelocity > 1) 
			rangeAimVelocity = 1;
		
		double posX = this.target.posX - Wrapper.INSTANCE.player().posX;
		double posY = this.target.posY + this.target.getEyeHeight() - 0.15 - Wrapper.INSTANCE.player().posY - Wrapper.INSTANCE.player().getEyeHeight();
		double posZ = this.target.posZ - Wrapper.INSTANCE.player().posZ;
		double y2 = Math.sqrt(posX * posX + posZ * posZ);
		float g = 0.006F;
		float tmp = (float) (rangeAimVelocity * rangeAimVelocity * rangeAimVelocity * rangeAimVelocity - g * (g * (y2 * y2) + 2 * posY * (rangeAimVelocity * rangeAimVelocity)));
		float pitch = (float) -Math.toDegrees(Math.atan((rangeAimVelocity * rangeAimVelocity - Math.sqrt(tmp)) / (g * y2)));
		
		//Wrapper.INSTANCE.player().rotationYaw = Utils.getRotationsNeeded(this.target)[0];
		Utils.assistFaceEntity(this.target, this.yaw.getValue().floatValue(), 0);
		Wrapper.INSTANCE.player().rotationPitch = pitch;
		
		super.onClientTick(event);
	}
	
	public boolean check(EntityLivingBase entity) {
		if(entity instanceof EntityArmorStand) { return false; }
		if(ValidUtils.isValidEntity(entity)){ return false; }
		if(!ValidUtils.isNoScreen()) { return false; }
		if(entity == Wrapper.INSTANCE.player()) { return false; }
		if(entity.isDead) { return false; }
		if(ValidUtils.isBot(entity)) { return false; }
		if(!ValidUtils.isFriendEnemy(entity)) { return false; }
    	if(!ValidUtils.isInvisible(entity)) { return false; }
    	if(!ValidUtils.isInAttackFOV(entity, FOV.getValue().intValue())) { return false; }
		if(!ValidUtils.isTeam(entity)) { return false; }
    	if(!ValidUtils.pingCheck(entity)) { return false; }
		if(!this.walls.getValue()) { if(!Wrapper.INSTANCE.player().canEntityBeSeen(entity)) { return false; } }
		return true;
    }
    
	EntityLivingBase getClosestEntity(){
		EntityLivingBase closestEntity = null;
 		for (Object o : Utils.getEntityList()) {
 			if(o instanceof EntityLivingBase && !(o instanceof EntityArmorStand)) {
 				EntityLivingBase entity = (EntityLivingBase)o;
 				if(check(entity)) {
 					if(closestEntity == null || Wrapper.INSTANCE.player().getDistance(entity) < Wrapper.INSTANCE.player().getDistance(closestEntity)) {
 						closestEntity = entity;
 					}
 				}
 			}
 		}
 		return closestEntity;
 	}

}
