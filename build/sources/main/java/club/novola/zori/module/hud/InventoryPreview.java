package club.novola.zori.module.hud;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class InventoryPreview extends Module {
    public InventoryPreview() {
        super("Inventory", Category.HUD);
        INSTANCE = this;
    }

    public static InventoryPreview INSTANCE;

    public Setting<Background> background = register("Background", Background.TRANS);

    public enum Background{
        NONE, CLEAR, NORMAL, TRANS
    }
}
