package club.novola.zori.managers;

import club.novola.zori.gui.ClickGUI;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.hud.components.*;
import club.novola.zori.util.Wrapper;
import com.google.common.collect.Lists;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class HudComponentManager {
    private List<HudComponent> components;

    public HudComponentManager(){
        components = Lists.newArrayList(
                new WatermarkComponent(),
                new PvpInfoComponent(),
                new OffhandModeComponent(),
                new InventoryComponent(),
                new PlayersComponent(),
                new FPSComponent(),
                new TPSComponent(),
                new WelcomerComponent(),
                new ServerComponent(),
                new ArmorWarningComponent(),
				new PlayerViewComponent(),
                new TotemsComponent(),
				new ArmorHUDComponent(),
				new ArrayListComponent(),
				new LogoComponent()
				// register hud components here
        );
        MinecraftForge.EVENT_BUS.register(this);
    }

    public List<HudComponent> getComponents(){
        return components;
    }

    @Nullable
    public HudComponent getComponentByName(String name){
        for(HudComponent component : components){
            if(component.getName().equalsIgnoreCase(name))
                return component;
        }
        return null;
    }

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent.Post event){
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR) && !(Wrapper.mc.currentScreen instanceof ClickGUI)){
            for(HudComponent c : components){
                if(c.isInvisible()) continue;
                c.render();
            }
        }
    }

}
