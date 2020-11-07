package club.novola.zori.module.client;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Settings extends Module {
    public Settings() {
        super("Settings", Category.CLIENT);
    }

    public static Boolean togglemsgs = false;

    public Setting<Boolean> toggleMsg = register("ToggleMsgs", false);
    public Setting<String> commandPrefix = register("Prefix", ",");

    public void onUpdate() {
        togglemsgs = toggleMsg.getValue();
    }
}
