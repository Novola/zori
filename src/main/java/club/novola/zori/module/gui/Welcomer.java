package club.novola.zori.module.gui;

import club.novola.zori.module.Module;

public class Welcomer extends Module {
    public Welcomer() {
        super("Welcomer", Category.GUI);
        INSTANCE = this;
    }

    public static Welcomer INSTANCE;
}
