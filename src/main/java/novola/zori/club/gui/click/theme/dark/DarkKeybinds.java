package novola.zori.club.gui.click.theme.dark;

import java.awt.Color;

import novola.zori.club.gui.click.base.Component;
import novola.zori.club.gui.click.base.ComponentRenderer;
import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.gui.click.theme.Theme;
import novola.zori.club.hack.hacks.ClickGui;
import novola.zori.club.utils.visual.ColorUtils;
import novola.zori.club.utils.visual.RenderUtils;
import org.lwjgl.input.Keyboard;

import novola.zori.club.gui.click.base.Component;
import novola.zori.club.gui.click.base.ComponentRenderer;
import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.gui.click.elements.KeybindMods;
import novola.zori.club.gui.click.theme.Theme;
import novola.zori.club.hack.hacks.ClickGui;
import novola.zori.club.utils.visual.ColorUtils;
import novola.zori.club.utils.visual.GLUtils;
import novola.zori.club.utils.visual.RenderUtils;

public class DarkKeybinds extends ComponentRenderer {

    public DarkKeybinds(Theme theme) {

        super(ComponentType.KEYBIND, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        KeybindMods keybind = (KeybindMods) component;
        
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 55) : ColorUtils.color(0, 0, 0, 55);
        int mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        
        theme.fontRenderer.drawString("Key", keybind.getX() + 4, keybind.getY() + 2, 
        		mainColorInv);
        
        int nameWidth = theme.fontRenderer.getStringWidth("Key") + 7;
        
        RenderUtils.drawRect(keybind.getX() + nameWidth, keybind.getY(), keybind.getX() + keybind.getDimension().width, keybind.getY() + 12,
        		mainColor);
        
        if(keybind.getMod().getKey() == -1) {
        	theme.fontRenderer.drawString(keybind.isEditing() ? "|" : "NONE", keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - theme.fontRenderer.getStringWidth("NONE") / 2, keybind.getY() + 2, keybind.isEditing() ? 
        			mainColorInv : 
        				ColorUtils.color(0.4f, 0.4f, 0.4f, 1.0f));
        }
        else
        {
        	theme.fontRenderer.drawString(keybind.isEditing() ? "|" : Keyboard.getKeyName(keybind.getMod().getKey()), keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - theme.fontRenderer.getStringWidth(Keyboard.getKeyName(keybind.getMod().getKey())) / 2, keybind.getY() + 2, keybind.isEditing() ? 
        			mainColorInv : 
        				mainColorInv);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }

}
