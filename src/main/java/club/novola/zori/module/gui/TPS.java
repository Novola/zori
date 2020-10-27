package club.novola.zori.module.gui;

import club.novola.zori.module.Module;

public class TPS extends Module {
    public TPS() {
        super("TPS", Category.GUI);
        INSTANCE = this;
    }

    public static TPS INSTANCE;
}