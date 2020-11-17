package club.novola.zori.module.hud;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;

public class Welcomer extends Module {

    public Setting<Mode> welcomeMode = register("Mode", Welcomer.Mode.NORMAL);

    public Welcomer() {
        super("Welcomer", Category.HUD);
        INSTANCE = this;
    }

    public static Welcomer INSTANCE;

    public enum Mode{
        NORMAL, DOX, FAKENAME
    }
}
