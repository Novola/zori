package club.novola.zori.event;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class MessageReceivedEvent extends Event {

    private String message;

    public MessageReceivedEvent(String message, boolean fromMinecraft) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String setMessage(String message) {
        return this.message = message;
    }

}
