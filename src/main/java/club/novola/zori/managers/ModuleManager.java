package club.novola.zori.managers;

import club.novola.zori.module.client.*;
import club.novola.zori.module.combat.*;
import club.novola.zori.module.hud.*;
import club.novola.zori.module.misc.*;
import club.novola.zori.module.misc.ReverseStep;
import club.novola.zori.module.movement.*;
import club.novola.zori.module.player.*;
import club.novola.zori.module.render.*;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.*;

public class ModuleManager {
    private static HashMap<String, Module> modules;
    private static List<Module> enabledModules;

    public ModuleManager(){
        enabledModules = new ArrayList<>();
        modules = new HashMap<>();

		// register modules here
        new AutoTrap();
        new Aura();
        new noDesync();
        new FastUse();
        new Step();
        new NoSlowBypass();
        new NoVoid();
        new Chat();
        new OffhandSwing();
        new FPS();
        new Welcomer();
        new SwingAnim();
        new HoleESP();
        new ArmorWarning();
        new OffhandCrystal();
        new OffhandGap();
        new AutoTotem();
        new Surround();
        new SecretClose();
        new SoundEffects();
        new NoRender();
        new TPS();
        new DiscordRpcModule();
        new Players();
        new BlockHighlight();
        new Reach();
        new AutoCrystal();
        new InventoryPreview();
        new HoleStep();
        new ReverseStep();
        new OffhandMode();
        new Server();
        new PvpInfo();
        new Watermark();
        new ClickGuiModule();
        new Sprint();
        new Colors();
        new Settings();
        new FakePlayer();
        new ChatSuffix();
        new VoidESP();
        new SkyColor();
        new NoSlowBypass();
        new CustomTime();
        new Jesus();
        new CustomFOV();
        new PlayerViewer();
        new Totems();
        new ArmorHUD();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void register(Module module, String name){
        modules.put(name.toLowerCase(), module);
    }

    @Nullable
    public Module getModuleByName(String name){
        return modules.getOrDefault(name.toLowerCase(), null);
    }

    public ChatFormatting getEnabledColor(String module){
        return getModuleByName(module).isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED;
    }

    public HashMap<String, Module> getModules(){
        return modules;
    }

    public List<Module> getEnabledModules(){
        return enabledModules;
    }

    public List<Module> getModulesInCategory(Module.Category category){
        List<Module> list = new ArrayList<>();
        modules.forEach((name, module) ->{
            if(module.getCategory().equals(category))
                list.add(module);
        });
        return list;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        for(Module m : enabledModules){
            m.onUpdate();
        }
    }

    @SubscribeEvent
    public void onRenderGameOverLay(RenderGameOverlayEvent event){
        if(event instanceof RenderGameOverlayEvent.Pre) {
            for(Module m : enabledModules){
                m.preRender2D();
            }
        } else if(event instanceof RenderGameOverlayEvent.Post){
            if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)){
                for(Module m : enabledModules){
                    m.postRender2D();
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event){
        for(Module m : enabledModules){
            m.onRender3D();
        }
    }

    @SubscribeEvent
    public void keyPressed(InputEvent.KeyInputEvent event){
        if(Keyboard.getEventKey() != 0){
            modules.forEach((name, m) ->{
                if(m.getBind() == Keyboard.getEventKey()) {
                    switch (m.getBindBehaviour()) {
                        case TOGGLE:
                            if (Keyboard.getEventKeyState()) m.toggle();
                            break;
                        case HOLD:
                            m.toggle();
                            break;
                    }
                }
            });
        }
    }
}
