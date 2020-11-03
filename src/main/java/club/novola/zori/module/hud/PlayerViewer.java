package club.novola.zori.module.hud;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;

public class PlayerViewer extends Module {
    public PlayerViewer() {
        super("PlayerViewer", Category.HUD);
        INSTANCE = this;
    }

    public Setting<Integer> scale = register("Scale", 30, 1, 100);

    public static PlayerViewer INSTANCE;
}
