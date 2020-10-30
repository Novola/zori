package club.novola.zori.gui.button.sub;

import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.gui.button.SubButton;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

public class BindButton extends SubButton {
    public BindButton(int x, int y, ModuleButton parent) {
        super(x, y, parent.getWidth() - 2, Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
    }

    private boolean listening = false;

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Gui.drawRect(getX() + 2, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, getColor(mouseX, mouseY));
        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow("Bind", getX() + 4, getY() + 2, -1);
        String val = "" + ChatFormatting.GRAY + (listening ? "Listening" : Keyboard.getKeyName(getParent().getModule().getBind()));
        Wrapper.getFontRenderer().drawStringWithShadow(val, getX() + getWidth() - Wrapper.getFontRenderer().getStringWidth(val), getY() + 2, -1);
    }

    private int getColor(int mouseX, int mouseY){
        Color color = new Color(50, 50, 50, 200);
        boolean hovered = mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1 && button == 0){
            listening = !listening;
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if(listening){
            if(keyCode != 0 && keyCode != Keyboard.KEY_ESCAPE){
                if(keyCode == Keyboard.KEY_DELETE) getParent().getModule().setBind(Keyboard.KEY_NONE);
                else getParent().getModule().setBind(keyCode);
            }
            listening = false;
        }
    }
}
