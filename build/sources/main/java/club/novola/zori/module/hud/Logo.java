package club.novola.zori.module.hud;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;

public class Logo extends Module {

    public Setting<Integer> imageWidth = register("ImageWidth", 100, 0, 1000);
    public Setting<Integer> imageHeight = register("ImageHeight", 100, 0, 1000);
    public Setting<Boolean> phoboLogo = register("PhoboLogo", true);

    public Logo(){
        super("Logo", Category.HUD);
        INSTANCE = this;
    }

    public static Logo INSTANCE;
}
