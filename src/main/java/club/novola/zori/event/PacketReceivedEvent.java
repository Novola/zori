package club.novola.zori.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

// called before you receive a packet from the server, if canceled the packet won't do anything
@Cancelable
public class PacketReceivedEvent extends Event {
    private Packet packet;

    public PacketReceivedEvent(Packet packet){
        this.packet = packet;
    }

    public Packet getPacket(){
        return packet;
    }

	// called after you receive a packet from the server, can't be canceled
    public static class Post extends PacketReceivedEvent{
        public Post(Packet packet) {
            super(packet);
        }
    }
}