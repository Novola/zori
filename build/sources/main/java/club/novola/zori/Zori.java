package club.novola.zori;

import club.novola.zori.gui.ClickGUI;
import club.novola.zori.managers.*;
import club.novola.zori.util.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(modid = Zori.MODID, name = Zori.MODNAME, version = Zori.MODVER)
public class Zori {
    public static final String MODID = "zori";
    public static final String MODNAME = "Zori";
    public static final String MODVER = "v0.0.1-BETA";

    public Logger log = LogManager.getLogger(MODNAME);

    private static Zori instance;

    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public RainbowManager rainbow;
    public ClientSettings clientSettings;
    public SettingManager settingManager;
    public PlayerStatus playerStatus;
    public HudComponentManager hudComponentManager;
    public ClickGUI clickGUI;
    public RotationManager rotationManager;

    public Zori() {
        instance = this;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
		// initialize everything - note the order of this matters in some cases e.g. you have to init modules before hud components
        new DiscordRpc();
        playerStatus = new PlayerStatus();
        rainbow = new RainbowManager();
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        clientSettings = new ClientSettings();
        commandManager = new CommandManager();
        hudComponentManager = new HudComponentManager();
        clickGUI = new ClickGUI();
        rotationManager = new RotationManager();
        new RenderUtils();
        new EntityUtils();
        new KillEventHelper();
        Config config = new Config();

		// load config
        try {
            config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

		// add a shutdown hook to save config
        Runtime.getRuntime().addShutdownHook(new Thread(MODNAME + " shutdown hook"){
            @Override
            public void run(){
                try {
                    config.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        log.info(toString() + " initialized");
    }

	// used to access non-static fields and methods
    public static Zori getInstance(){
        return instance;
    }

	// you can use Zori.getInstance().toString(); to get "Zori 2.0"
    @Override
    public String toString(){
        return MODNAME + " " + MODVER;
    }
}
