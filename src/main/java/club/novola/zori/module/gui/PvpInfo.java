package club.novola.zori.module.gui;

import club.novola.zori.module.Module;

public class PvpInfo extends Module {
    public PvpInfo() {
        super("PvpInfo", Category.GUI);
        INSTANCE = this;
    }

    public static PvpInfo INSTANCE;
}
