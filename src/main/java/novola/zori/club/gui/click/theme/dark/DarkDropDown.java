package novola.zori.club.gui.click.theme.dark;

import java.awt.Color;

import novola.zori.club.gui.click.base.Component;
import novola.zori.club.gui.click.base.ComponentRenderer;
import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.gui.click.elements.Dropdown;
import novola.zori.club.gui.click.theme.Theme;
import novola.zori.club.hack.hacks.ClickGui;
import novola.zori.club.gui.click.base.Component;
import novola.zori.club.gui.click.base.ComponentRenderer;
import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.gui.click.theme.Theme;
import novola.zori.club.hack.hacks.ClickGui;


public class DarkDropDown extends ComponentRenderer {

    public DarkDropDown(Theme theme) {

        super(ComponentType.DROPDOWN, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Dropdown dropdown = (Dropdown) component;
        String text = dropdown.getText();

        theme.fontRenderer.drawString(text, dropdown.getX() + 5, dropdown.getY() + (dropdown.getDropdownHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4), 
        		ClickGui.getColor());

        if (dropdown.isMaximized()) {
            dropdown.renderChildren(mouseX, mouseY);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
