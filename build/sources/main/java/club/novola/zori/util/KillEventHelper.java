package club.novola.zori.util;

import club.novola.zori.event.PacketSendEvent;
import club.novola.zori.event.PlayerKillEvent;
import club.novola.zori.Zori;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.concurrent.ConcurrentHashMap;

// used to trigger the PlayerKillEvent for sound effects, can be used for AutoGG
public class KillEventHelper {
    public KillEventHelper(){
        targets = new ConcurrentHashMap<>();
        MinecraftForge.EVENT_BUS.register(this);
        INSTANCE = this;
    }

    public static KillEventHelper INSTANCE;

    private ConcurrentHashMap<EntityPlayer, Integer> targets;

	// add a target, expires after 20 client ticks (1 second)
    public void addTarget(EntityPlayer player){
        targets.put(player, 20);
    }

    public ConcurrentHashMap<EntityPlayer, Integer> getTargets(){
        return targets;
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendEvent event){
        if(Wrapper.getWorld() == null) return;
        if(event.getPacket() instanceof CPacketUseEntity){
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if(packet.getAction().equals(CPacketUseEntity.Action.ATTACK) && packet.getEntityFromWorld(Wrapper.getWorld()) != null){
                if(packet.getEntityFromWorld(Wrapper.getWorld()) instanceof EntityPlayer && Zori.getInstance().playerStatus.getStatus(packet.getEntityFromWorld(Wrapper.getWorld()).getName()) != 1){
                    addTarget((EntityPlayer) packet.getEntityFromWorld(Wrapper.getWorld()));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer && targets.containsKey(event.getEntityLiving())){
            targets.remove((EntityPlayer) event.getEntityLiving());
            MinecraftForge.EVENT_BUS.post(new PlayerKillEvent((EntityPlayer) event.getEntityLiving())); // trigger the custom event
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(Wrapper.getPlayer() == null || Wrapper.getPlayer().getHealth() <= 0 || Wrapper.getPlayer().isDead){
            targets = new ConcurrentHashMap<>(); // clear targets
            return;
        }
        ConcurrentHashMap<EntityPlayer, Integer> targetsCopy = new ConcurrentHashMap<>(); // make a copy of the list to prevent ConcurrentModificationException
        targets.forEach(targetsCopy::put);
        targetsCopy.forEach((player, ticks) -> {
            targets.remove(player, ticks); // remove target from list
            if(player.getHealth() <= 0 || player.isDead){
                MinecraftForge.EVENT_BUS.post(new PlayerKillEvent(player));
            } else {
                if(ticks > 0) targets.put(player, ticks - 1); // if the target is not expired add it back to the list and decrement the ticks to expire
            }
        });
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event){
        targets = new ConcurrentHashMap<>(); // clear targets
    }

    @SubscribeEvent
    public void onWorldUnLoad(WorldEvent.Unload event){
        targets = new ConcurrentHashMap<>(); // clear targets
    }

}
