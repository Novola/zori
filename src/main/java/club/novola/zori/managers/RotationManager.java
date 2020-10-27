package club.novola.zori.managers;

import club.novola.zori.event.PacketSendEvent;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RotationManager {

    private float yaw = 0;
    private float pitch = 0;
    private boolean shouldRotate = false;

    public RotationManager(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onSendPacket(PacketSendEvent event){
        if(event.getPacket() instanceof CPacketPlayer){
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            if(shouldRotate){
                packet.yaw = yaw;
                packet.pitch = pitch;
            }
        }
    }

	// set yaw and pitch of packets you send, don't forget to call reset() otherwise you'll get desynced
    public void rotate(double x, double y, double z){
        if(Wrapper.getPlayer() == null) return;
        Double[] v = calculateLookAt(x, y, z, Wrapper.getPlayer());
        shouldRotate = true;
        yaw = v[0].floatValue();
        pitch = v[1].floatValue();
    }

	// resets yaw and pitch
    public void reset(){
        shouldRotate = false;
        if(Wrapper.getPlayer() == null) return;
        yaw = Wrapper.getPlayer().rotationYaw;
        pitch = Wrapper.getPlayer().rotationPitch;
    }

    /**
     * @author 086
     */
    private Double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        //to degree
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;
        yaw += 90.0;
        return new Double[]{yaw, pitch};
    }
}
