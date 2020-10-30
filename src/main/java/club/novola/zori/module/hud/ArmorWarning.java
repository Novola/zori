package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class ArmorWarning extends Module {
    public ArmorWarning() {
        super("ArmorWarning", Category.HUD);
        INSTANCE = this;
    }

    public static ArmorWarning INSTANCE;
}
