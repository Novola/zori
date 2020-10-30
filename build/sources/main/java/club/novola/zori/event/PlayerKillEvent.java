package club.novola.zori.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

// called when the player kills another player
public class PlayerKillEvent extends Event {
    private final EntityPlayer player;
    public PlayerKillEvent(EntityPlayer player){
        this.player = player;
    }

    public EntityPlayer getPlayer(){
        return player;
    }
}
