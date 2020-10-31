package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.Entity;

public class PlayerGlow extends Module {

    public PlayerGlow() {
        super("PlayerGlow", Category.RENDER);
    }

    public void onUpdate() {
        for (Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (!entity.isGlowing()) {
                entity.setGlowing(true);

            }
        }
    }

    public void onDisable() {
        for (Entity entity : Wrapper.mc.world.loadedEntityList) {
            if (entity.isGlowing()) {
                entity.setGlowing(false);
            }
        }
        super.onDisable();
    }
}
