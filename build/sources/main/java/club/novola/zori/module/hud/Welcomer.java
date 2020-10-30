package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", Category.HUD);
        INSTANCE = this;
    }

    public static Welcomer INSTANCE;
}
