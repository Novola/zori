package club.novola.zori.module.player;

import club.novola.zori.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

//idk why this wasnt made quicker but hey
public class ChatSuffix extends Module {

    public ChatSuffix() {
        super("ChatSuffix", Category.PLAYER);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        String ZoriChat = " \u23d0 \u1d22\u1d0f\u0280\u026a"; //Chat Suffix
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(".")
                || event.getMessage().startsWith(",") || event.getMessage().startsWith("-")
                || event.getMessage().startsWith("$") || event.getMessage().startsWith("*")) return;
        event.setMessage(event.getMessage() + ZoriChat); // Adds the suffix to the end of the message
    }
}