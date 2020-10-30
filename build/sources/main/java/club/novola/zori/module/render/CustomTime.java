package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;

public class CustomTime extends Module {
    private long time = 0;

    private Setting<Integer> gametime = register("Time", 18000, 0, 23992);

    public CustomTime() {
        super("CustomTime", Category.RENDER);
    }

    public void onUpdate() {
        Wrapper.mc.world.setWorldTime(gametime.getValue());
    }

    public void onEnable() {
        this.time = Wrapper.mc.world.getWorldTime();
        Wrapper.mc.world.setWorldTime(gametime.getValue());
    }

    public void onDisable() {
        Wrapper.mc.world.setWorldTime(time);
    }
}
