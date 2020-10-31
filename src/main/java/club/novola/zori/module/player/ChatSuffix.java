package club.novola.zori.module.player;

import club.novola.zori.command.Command;
import club.novola.zori.event.PacketSendEvent;
import club.novola.zori.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;

public class ChatSuffix extends Module {

    public ChatSuffix() {
        super("ChatSuffix (WIP)", Category.PLAYER);
    }

    public void packet(final PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketChatMessage) {
            if (((CPacketChatMessage)event.getPacket()).getMessage().startsWith("/") ||
                    ((CPacketChatMessage)event.getPacket()).getMessage().startsWith(",") ||
                    ((CPacketChatMessage)event.getPacket()).getMessage().startsWith(".")||
                    ((CPacketChatMessage)event.getPacket()).getMessage().startsWith("-")||
                    ((CPacketChatMessage)event.getPacket()).getMessage().startsWith("$")) {
                return;
            }
            final String old = ((CPacketChatMessage)event.getPacket()).getMessage();
            final String suffix = " \u23d0 ";
            String s = old + suffix;
            if (s.length() > 255) {
                return;
            }
        }
    }
}
