package club.novola.zori.gui;

import club.novola.zori.Zori;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Panel implements IGuiComponent {

    private Module.Category category;
    private int x;
    private int y;
    private int width;
    private int height;
    private int titleHeight;
    private boolean extended = true;

    private boolean dragging = false;
    private int dragX = 0;
    private int dragY = 0;

    private List<ModuleButton> modules;

    public Panel(Module.Category category, int x, int y) {
        this.category = category;
        this.x = x;
        this.y = y;
        modules = new ArrayList<>();
        this.titleHeight = Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        int buttonY =  y + titleHeight;
        width = 90;
        for(Module m : Zori.getInstance().moduleManager.getModulesInCategory(category)){
            ModuleButton button = new ModuleButton(m, this.x, buttonY, this);
            modules.add(button);
            buttonY += button.getHeight();
            height += button.getHeight();
        }
        height = buttonY;
    }

    @Override
    public void draw(int mouseX, int mouseY){
        int color = Zori.getInstance().clientSettings.getColor();

        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        Gui.drawRect(x - 2, y - 2, x + width + 2, y + titleHeight - 1, color);
        Gui.drawRect(x, y + titleHeight - 1, x + width, y + titleHeight, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(category.name(), x + 1, y + 2, -1);
        if(!extended) {
            Gui.drawRect(x - 2, y + titleHeight - 1, x + width + 2, y + titleHeight + 2, color);
            return;
        }

        height = 0;
        for(ModuleButton b : modules){
            if(b.getX() != x) b.setX(x);
            if(b.getY() != y + titleHeight + height) b.setY(y + titleHeight + height);
            b.draw(mouseX, mouseY);
            height += b.getHeight();
        }

        Gui.drawRect(x - 2, y + titleHeight - 1, x, y + titleHeight + height + 2, color);
        Gui.drawRect(x + width, y + titleHeight - 1, x + width + 2, y + titleHeight + height + 2, color);
        Gui.drawRect(x, y + titleHeight + height, x + width, y + titleHeight + height + 2, color);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + titleHeight){
            if(button == 0) {
                dragX = x - mouseX;
                dragY = y - mouseY;
                dragging = true;
            } else if(button == 1){
                extended = !extended;
            }
            return;
        }

        for(ModuleButton b : modules){
            b.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(dragging) dragging = false;

        for(ModuleButton b : modules){
            b.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {

        for(ModuleButton b : modules){
            b.keyTyped(typedChar, keyCode);
        }
    }

    public Module.Category getCategory() {
        return category;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isExtended() {
        return extended;
    }

    public List<ModuleButton> getModules() {
        return modules;
    }
}
