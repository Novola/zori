package club.novola.zori.module.render;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", Category.RENDER);
    }

    private Setting<Boolean> armorBar = register("ArmorBar", false);
    private Setting<Boolean> rain = register("Rain", true);
    private Setting<Boolean> antifog = register("AntiFog", true);
    private Setting<Boolean> nobob = register("NoBob", true);

    @SubscribeEvent
    public void preRenderGameOverlay(RenderGameOverlayEvent.Pre event){
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ARMOR) && armorBar.getValue()){
            event.setCanceled(true);
        }
    }
    public void onUpdate(){
        if(Wrapper.mc.world == null){
            return;
        }else {
            if (rain.getValue()) {
                Wrapper.INSTANCE.getWorld().setRainStrength(0.0f);
            }
            if (nobob.getValue()) {
                Wrapper.mc.gameSettings.viewBobbing = false;
            }
        }
    }

    public void onEnable() {
        if(nobob.getValue()){
            Wrapper.mc.gameSettings.viewBobbing = false;
        }
    }

    public void onDisable() {
            Wrapper.mc.gameSettings.viewBobbing = true;
    }

}
