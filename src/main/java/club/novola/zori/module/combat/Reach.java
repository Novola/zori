package club.novola.zori.module.combat;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Reach extends Module {
    public Reach() {
        super("Reach", Category.COMBAT);
    }

    public Setting<Float> distance = register("Distance", 5.5f, 0.0f, 10.0f); // make sure the compiler doesnt think the values are doubles
}
