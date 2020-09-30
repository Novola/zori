package novola.zori.club.gui.click.theme.dark;

import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.gui.click.theme.Theme;
import novola.zori.club.wrappers.Wrapper;
import net.minecraftforge.client.model.obj.OBJModel.Texture;
import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.gui.click.theme.Theme;
import novola.zori.club.wrappers.Wrapper;

public class DarkTheme extends Theme {

    public DarkTheme() {
        super("GishCodeDark");
        this.fontRenderer = Wrapper.INSTANCE.fontRenderer();
        addRenderer(ComponentType.FRAME, new DarkFrame(this));
        addRenderer(ComponentType.BUTTON, new DarkButton(this));
        addRenderer(ComponentType.SLIDER, new DarkSlider(this));
        addRenderer(ComponentType.CHECK_BUTTON, new DarkCheckButton(this));
        addRenderer(ComponentType.EXPANDING_BUTTON, new DarkExpandingButton(this));
        addRenderer(ComponentType.TEXT, new DarkText(this));
        addRenderer(ComponentType.KEYBIND, new DarkKeybinds(this));
        addRenderer(ComponentType.DROPDOWN, new DarkDropDown(this));
        addRenderer(ComponentType.COMBO_BOX, new DarkComboBox(this));
    }
}
