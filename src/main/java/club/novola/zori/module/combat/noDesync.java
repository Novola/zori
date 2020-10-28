package club.novola.zori.module.combat;

import club.novola.zori.event.PacketReceivedEvent;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// skidded from seppuku
public class noDesync extends Module {
    public noDesync() {
        super("noDesync", Category.COMBAT);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketReceivedEvent event){
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                try {
                    for (Entity e : Wrapper.getWorld().loadedEntityList) {
                        if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                                e.setDead();
                        }
                    }
                } catch(Exception e){e.printStackTrace();}
            }
        }
    }
}
