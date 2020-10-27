package club.novola.zori.module.gui;

import club.novola.zori.module.Module;

public class Watermark extends Module {
    public Watermark() {
        super("Watermark", Category.GUI);
        INSTANCE = this;
    }

    public static Watermark INSTANCE;
}
