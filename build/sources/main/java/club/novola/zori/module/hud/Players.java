package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Players extends Module {
    public Players() {
        super("Players", Category.HUD);
        INSTANCE = this;
    }

    public static Players INSTANCE;

    public Setting<Align> align = register("Align", Align.RIGHT);

    public enum Align{
        LEFT, RIGHT
    }
}
