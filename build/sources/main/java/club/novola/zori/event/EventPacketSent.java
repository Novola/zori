package club.novola.zori.event;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventPacketSent extends Event {
    private boolean cancel;
    private Packet packet;

    public EventPacketSent(Packet packet)
    {
        this.packet = packet;
    }

    public Packet getPacket()
    {
        return this.packet;
    }

    public boolean isCancelled()
    {
        return this.cancel;
    }

    public void setCancelled(boolean cancel)
    {
        this.cancel = cancel;
    }

    public void setPacket(Packet packet)
    {
        this.packet = packet;
    }
}

