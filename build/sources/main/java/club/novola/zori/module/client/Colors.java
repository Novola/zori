package club.novola.zori.module.client;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;

public class Colors extends Module {
    public Colors() {
        super("Colors", Category.CLIENT);
    }

    public Setting<Integer> red = register("Red", 255, 0, 255);
    public Setting<Integer> green = register("Green", 255, 0, 255);
    public Setting<Integer> blue = register("Blue", 255, 0, 255);
    public Setting<Boolean> rainbow = register("Rainbow", true);
    public Setting<Integer> rainbowSpeed = register("RainbowSpeed", 2, 1, 10);
}
