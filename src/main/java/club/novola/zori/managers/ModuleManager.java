package club.novola.zori.managers;

import club.novola.zori.module.client.*;
import club.novola.zori.module.client.Capes;
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
import java.util.ArrayList;

public class ModuleManager {
    private static HashMap<String, Module> modules;
    private static List<Module> enabledModules;

    public ModuleManager(){
        enabledModules = new ArrayList<>();
        modules = new HashMap<>();

		// CLIENT
        new Colors();
        new Settings();
        new Capes();
        new Arraylist();

        // COMBAT
        new AutoTrap();
        new Aura();
        new noDesync();
        new AutoCrystal();
        new Surround();
        new SecretClose();
        new Reach();
        new AutoCrystalTwo();
        new Offhand();

        // HUD
        new FPS();
        new Welcomer();
        new ArmorWarning();
        new TPS();
        new Players();
        new InventoryPreview();
        new ClickGuiModule();
        new OffhandMode();
        new Server();
        new PvpInfo();
        new Watermark();
        new PlayerViewer();
        new Totems();
        new ArmorHUD();
        new Logo();

        // MISC
        new Chat();
        new DiscordRpcModule();
        new FakePlayer();
        new SoundEffects();
        new GreenText();
        new AirPlace();
        new BlockPlaceTest();
        new DonkeyFinder();
        new W0M3NMode();

        // MOVEMENT
        new BoatFly();
        new Step();
        new HoleStep();
        new ReverseStep();
        new Sprint();
        new Jesus();

        // PLAYER
        new ChatSuffix();
        new FastXP();
        new NoSlowBypass();
        new NoVoid();
        new AutoRespawn();

        // RENDER
        new OffhandSwing();
        new SwingAnim();
        new HoleESP();
        new NoRender();
        new BlockHighlight();
        new VoidESP();
        new SkyColor();
        new CustomTime();
        new CustomFOV();
        new PlayerGlow();
        new ShaderLoader();
        new GUIBlur();
        new PlayerESP();

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

    public static List<Module> getEnabledModules(){
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
