package club.novola.zori.module.hud;

import club.novola.zori.module.Module;
import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {

    public static boolean enabledGui = false;

    public ClickGuiModule() {
        super("ClickGUI", Category.HUD);
        setBind(Keyboard.KEY_P);
    }

    @Override
    public void onEnable(){
        this.enabledGui = true;

        if(Wrapper.mc.currentScreen != null)
            Wrapper.mc.displayGuiScreen(null);

        Wrapper.mc.displayGuiScreen(Zori.getInstance().clickGUI);

        disable();
    }
}
