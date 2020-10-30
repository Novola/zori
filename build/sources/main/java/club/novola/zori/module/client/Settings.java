package club.novola.zori.module.client;

import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;

public class Settings extends Module {
    public Settings() {
        super("Settings", Category.CLIENT);
    }

    public Setting<String> commandPrefix = register("Prefix", ",");
}
