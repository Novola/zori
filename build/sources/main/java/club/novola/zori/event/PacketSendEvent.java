package club.novola.zori.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

// called before you send a packet to the server, if canceled the packet won't send
@Cancelable
public class PacketSendEvent extends Event {
    private Packet packet;

    public PacketSendEvent(Packet packet){
        this.packet = packet;
    }

    public Packet getPacket(){
        return packet;
    }

	// called after you send a packet to the server, can't be canceled
    public static class Post extends PacketSendEvent{
        public Post(Packet packet) {
            super(packet);
        }
    }
}
