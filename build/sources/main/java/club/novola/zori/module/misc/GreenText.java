package club.novola.zori.module.misc;

import club.novola.zori.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GreenText extends Module {

    public GreenText() {
        super("GreenText", Category.MISC);
    }

    String GREATERTHAN = ">";

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(".")
                || event.getMessage().startsWith(",") || event.getMessage().startsWith("-")
                || event.getMessage().startsWith("$") || event.getMessage().startsWith("*")) return;
        event.setMessage(GREATERTHAN + event.getMessage()); // Adds > to the start of the sentence.
    }
}

