package club.novola.zori.module.gui;

import club.novola.zori.module.Module;
import club.novola.zori.Zori;
import club.novola.zori.util.Wrapper;
import org.lwjgl.input.Keyboard;

public class ClickGuiModule extends Module {
    public ClickGuiModule() {
        super("ClickGUI", Category.GUI);
        setBind(Keyboard.KEY_P);
    }

    @Override
    public void onEnable(){
        if(Wrapper.mc.currentScreen != null)
            Wrapper.mc.displayGuiScreen(null);

        Wrapper.mc.displayGuiScreen(Zori.getInstance().clickGUI);

        disable();
    }
}
