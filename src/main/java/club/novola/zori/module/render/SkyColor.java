package club.novola.zori.module.render;

import club.novola.zori.Zori;
import club.novola.zori.module.Module;
import club.novola.zori.module.client.Colors;
import club.novola.zori.module.client.Settings;
import club.novola.zori.managers.ClientSettings;
import club.novola.zori.managers.RainbowManager;
import club.novola.zori.setting.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColor extends Module {

    private Setting<Integer> red = register("Red", 255, 0, 255);
    private Setting<Integer> green = register("Green", 255, 0, 255);
    private Setting<Integer> blue = register("Blue", 255, 0, 255);
    private Setting<Boolean> sync = register("Sync", false);

    public SkyColor() {
        super("SkyColor", Category.RENDER);
    }

    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed((float) red.getValue() / 255f);
        event.setBlue((float) blue.getValue() / 255f);
        event.setGreen((float) green.getValue() / 255f);
        if (sync.getValue()) ;
        event.equals(Zori.getInstance().clientSettings.getColor());
    }
}