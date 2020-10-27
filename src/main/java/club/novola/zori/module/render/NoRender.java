package club.novola.zori.module.render;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender extends Module {
    public NoRender() {
        super("NoRender", Category.RENDER);
    }

    private Setting<Boolean> armorBar = register("ArmorBar", true);

    @SubscribeEvent
    public void preRenderGameOverlay(RenderGameOverlayEvent.Pre event){
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ARMOR) && armorBar.getValue()){
            event.setCanceled(true);
        }
    }
}
