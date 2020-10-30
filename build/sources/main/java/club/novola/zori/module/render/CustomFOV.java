package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import net.minecraft.client.Minecraft;

public class CustomFOV extends Module {

    Minecraft mc = Minecraft.getMinecraft();

    public float defaultFov;

    private Setting<Integer> fov = register("FOV", 130, 0, 170);

    public CustomFOV() {
        super("CustomFOV", Category.RENDER);
    }

    public void onUpdate() {
        mc.gameSettings.fovSetting = fov.getValue().floatValue();
    }

    public void onEnable() {
        this.defaultFov = mc.gameSettings.fovSetting;
    }

    public void onDisable() {
        mc.gameSettings.fovSetting = this.defaultFov;
    }

}
