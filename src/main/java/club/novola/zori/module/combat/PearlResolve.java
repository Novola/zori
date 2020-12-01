package club.novola.zori.module.combat;

import club.novola.zori.command.Command;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class PearlResolve extends Module {
    public Boolean enabled = false;
    public PearlResolve() {
        super("PearlResolve", Category.COMBAT);
    }

    ConcurrentHashMap<UUID, Integer> uuid = new ConcurrentHashMap<>();

    @Override
    public void onEnable(){
        enabled = true;
    }

    @Override
    public void onUpdate() {
        for (Entity entity : Wrapper.getWorld().loadedEntityList) {
            if (entity instanceof EntityEnderPearl) {
                EntityPlayer closest = null;
                for (EntityPlayer p : Wrapper.getWorld().playerEntities) {
                    if (closest == null || entity.getDistance(p) < entity.getDistance(closest)) {
                        closest = p;
                    }
                }
                if (closest != null && closest.getDistance(entity) < 2 && !uuid.containsKey(entity.getUniqueID()) && !closest.getName().equalsIgnoreCase(Wrapper.getPlayer().getName())) {
                    uuid.put(entity.getUniqueID(), 200);
                    Command.sendClientMessage(closest.getName() + " pearled " + getTitle(entity.getHorizontalFacing().getName()) + "!", false);
                }
            }
        }
        this.uuid.forEach((name, timeout) -> {
            if (timeout <= 0) {
                this.uuid.remove(name);
            } else {
                this.uuid.put(name, timeout - 1);
            }
        });
    }

    public String getTitle(String in) {
        if (in.equalsIgnoreCase("west")) {
            return "east";
        } else if (in.equalsIgnoreCase("east")) {
            return "west";
        } else {
            return in;
        }
    }

    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (enabled) {
            this.disable();
        }
    }
}