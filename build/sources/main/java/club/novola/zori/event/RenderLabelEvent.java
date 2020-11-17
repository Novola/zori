package club.novola.zori.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class RenderLabelEvent extends Event {

    private Entity entity;
    private String label;

    public RenderLabelEvent(Entity entity, String label) {
        this.entity = entity;
        this.label = label;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
