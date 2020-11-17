package club.novola.zori.module.player;

import club.novola.zori.event.MessageReceivedEvent;
import club.novola.zori.event.PlayerSpawnEvent;
import club.novola.zori.event.RenderLabelEvent;
import club.novola.zori.module.Module;
import club.novola.zori.util.StringUtils;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

public class NameProtect extends Module {

    private Map<String, String> names;

    public NameProtect(){
        super("NameProtect", Category.PLAYER);
    }

    @SubscribeEvent
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage();

        if (!(names.containsKey(Wrapper.mc.player.getName())))
            getNewName(Wrapper.mc.player.getName());

        for (String name : names.keySet()) {
            String newName = getNewName(name);
            message = message.replace(name, newName);
        }

        event.setMessage(message);
    }

    @SubscribeEvent
    public void onRenderLivingLabel(RenderLabelEvent event) {
        if (!(event.getEntity() instanceof EntityOtherPlayerMP))
            return;

        if (event.getEntity() == null || event.getEntity().getName() == null)
            return;

        event.setLabel(getNewName(event.getEntity().getName()));
    }

    @SubscribeEvent
    public void onPlayerSpawn(PlayerSpawnEvent event) {
        getNewName(event.getEntity().getName());
    }

    public String getNewName(String name) {
        String newName = null;
        if (!(names.containsKey(name))) {
            names.put(name, StringUtils.randomString(10, true, false, false) + (name.equals(Wrapper.mc.player.getName()) ? " (YOU)" : ""));
        }

        newName = names.get(name);

        return newName;
    }
}
