package club.novola.zori.gui.button.sub;

import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.gui.button.SubButton;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EnumButton extends SubButton {
    public EnumButton(Setting<Enum> setting, int x, int y, ModuleButton parent) {
        super(x, y, parent.getWidth(), Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
        this.setting = setting;
    }

    private Setting<Enum> setting;

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(getX() + 2, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, getColor(mouseX, mouseY));
        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(setting.getName(), getX() + 4, getY() + 2, -1);
        String val = "" + ChatFormatting.GRAY + setting.getValue().name();
        int valX = getX() + getWidth() - Wrapper.getFontRenderer().getStringWidth(val) - 1;
        if(getX() + Wrapper.getFontRenderer().getStringWidth(setting.getName()) + 4 > valX) valX = getX() + Wrapper.getFontRenderer().getStringWidth(setting.getName()) + 5;
        Wrapper.getFontRenderer().drawStringWithShadow(val, valX, getY() + 2, -1);
    }

    private int getColor(int mouseX, int mouseY){
        Color color = new Color(50, 50, 50, 200);
        boolean hovered = mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1 && button == 0){
            List<Enum> list = Arrays.asList(getEnum().getEnumConstants());
            int index = list.indexOf(setting.getValue());
            if(index + 1 < list.size()) setting.setValue(list.get(index + 1));
            else setting.setValue(list.get(0));
        }
    }

    private Class<Enum> getEnum(){
        return  setting.getValue().getDeclaringClass();
    }
}
