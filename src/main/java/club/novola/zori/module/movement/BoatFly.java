package club.novola.zori.module.movement;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;

public class BoatFly extends Module {

    public BoatFly() {
        super("BoatFly", Category.MOVEMENT);
    }

    public void onUpdate() {
        if(Wrapper.mc.player.getRidingEntity() != null) {
            if(Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                Wrapper.mc.player.getRidingEntity().motionY = 0.1;
            }
        }
    }
}
