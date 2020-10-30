package club.novola.zori.module.movement;

import org.lwjgl.input.Keyboard;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;

import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

// TODO: Make the module disable on disconnect (p sure it fucks the game if not iirc)

public class NoVoid extends Module {
    public NoVoid() {
        super("NoVoid", Category.MOVEMENT);
    }


    @Override
    public void onUpdate() {
        double yLevel = Wrapper.getPlayer().posY;
        if (yLevel <= .5) {
            Wrapper.getPlayer().moveVertical = 10;
            Wrapper.getPlayer().jump();
        }
    }

    @Override
    public void onDisable() {
        Wrapper.getPlayer().moveVertical = 0;
    }
}