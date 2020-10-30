package club.novola.zori.gui.button.sub;

import club.novola.zori.Zori;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.gui.button.SubButton;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;

public class BooleanButton extends SubButton {
    private Setting<Boolean> setting;
    public BooleanButton(Setting<Boolean> setting, int x, int y, ModuleButton parent){
        super(x, y, parent.getWidth() - 2, Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
        this.setting = setting;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(getX() + 2, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, getColor(mouseX, mouseY));
        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(setting.getName(), getX() + 4, getY() + 2, -1);
    }

    private int getColor(int mouseX, int mouseY){
        Color color = setting.getValue() ? Zori.getInstance().clientSettings.getColorr(200) : new Color(50, 50, 50, 200);
        boolean hovered = mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1;
        return hovered ? (setting.getValue() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1 && button == 0){
            setting.setValue(!setting.getValue());
        }
    }
}
