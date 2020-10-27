package club.novola.zori.module.gui;

import club.novola.zori.module.Module;

public class FPS extends Module {
    public FPS() {
        super("FPS", Category.GUI);
        INSTANCE = this;
    }

    public static FPS INSTANCE;
}
