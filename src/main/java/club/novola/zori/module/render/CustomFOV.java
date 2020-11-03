package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.module.movement.Step;
import club.novola.zori.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomFOV extends Module {

    Minecraft mc = Minecraft.getMinecraft();

    public float defaultFov;

    private Setting<Mode> mode = register("Mode", CustomFOV.Mode.FOV);
    private Setting<Integer> fov = register("FOV", 130, 0, 170);

    public CustomFOV() {
        super("CustomFOV", Category.RENDER);
    }

    @SubscribeEvent
    public void fovOn(final EntityViewRenderEvent.FOVModifier e) {
        if (mode.getValue().equals(CustomFOV.Mode.VMC)) {
            e.setFOV((float)this.fov.getValue());
        }
    }

    public void onUpdate() {
        if(mode.getValue().equals(CustomFOV.Mode.FOV)) {
            mc.gameSettings.fovSetting = fov.getValue().floatValue();
        }
    }

    public void onEnable() {
        this.defaultFov = mc.gameSettings.fovSetting;
    }

    public void onDisable() {
        mc.gameSettings.fovSetting = this.defaultFov;
    }

    public enum Mode{
        FOV, VMC
    }

}
