package club.novola.zori.event;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PlayerSpawnEvent extends Event {

    private EntityOtherPlayerMP entity;

    public PlayerSpawnEvent(EntityOtherPlayerMP entity) {
        this.entity = entity;
    }

    public EntityOtherPlayerMP getEntity() {
        return entity;
    }

    public void setEntity(EntityOtherPlayerMP entity) {
        this.entity = entity;
    }

}
