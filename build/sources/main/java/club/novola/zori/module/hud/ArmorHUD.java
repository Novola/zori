package club.novola.zori.module.hud;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;

public class ArmorHUD extends Module {
    public ArmorHUD() {
        super("ArmorHUD", Category.HUD);
        INSTANCE = this;
    }

    public Setting<Boolean> damageA = register("Damage", true);
    public Setting<Boolean> extraInfo = register("ExtraInfo", false);

    public static ArmorHUD INSTANCE;
}
