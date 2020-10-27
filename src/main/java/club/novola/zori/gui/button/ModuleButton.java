package club.novola.zori.gui.button;

import club.novola.zori.Zori;
import club.novola.zori.gui.button.sub.EnumButton;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import club.novola.zori.gui.IGuiComponent;
import club.novola.zori.gui.Panel;
import club.novola.zori.gui.button.sub.BindButton;
import club.novola.zori.gui.button.sub.BooleanButton;
import club.novola.zori.gui.button.sub.SliderButton;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton implements IGuiComponent {
    private Panel panel;
    private Module module;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean extended;

    private List<SubButton> buttons;

    public ModuleButton(Module module, int x, int y, Panel panel) {
        this.panel = panel;
        this.module = module;
        this.x = x;
        this.y = y;
        width = panel.getWidth();
        height = Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        extended = false;
        buttons = new ArrayList<>();
        int subButtonY = 0;
        for(Setting s : Zori.getInstance().settingManager.getSettingsForMod(module)){
            SubButton button = null;
            if(s.getValue() instanceof Boolean){
                button = new BooleanButton(s, x, y + height + subButtonY, this);
            } else if(s.getValue() instanceof Float){
                button = new SliderButton.FloatSlider(s, x, y + height + subButtonY, this);
            } else if(s.getValue() instanceof Double){
                button = new SliderButton.DoubleSlider(s, x, y + height + subButtonY, this);
            } else if(s.getValue() instanceof Integer){
                button = new SliderButton.IntSlider(s, x, y + height + subButtonY, this);
            } else if(s.getValue() instanceof Enum){
                button = new EnumButton(s, x, y + height + subButtonY, this);
            }
            // TODO: color and enum
            if(button != null) {
                buttons.add(button);
                subButtonY += button.getHeight();
            }
        }
        BindButton bindButton = new BindButton(x, y + height + subButtonY, this);
        buttons.add(bindButton);
        //subButtonY += bindButton.getHeight();
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        Gui.drawRect(x, y, x + width, y + Wrapper.getFontRenderer().FONT_HEIGHT + 3, getColor(mouseX, mouseY));
        Gui.drawRect(x, y + Wrapper.getFontRenderer().FONT_HEIGHT + 3, x + width, y + Wrapper.getFontRenderer().FONT_HEIGHT + 4, new Color(10, 10, 10, 200).getRGB());
        Wrapper.getFontRenderer().drawStringWithShadow(module.getName(), x + 2, y + 2, -1);

        height = Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        if(extended){
            for(SubButton b : buttons){
                if(b.getX() != x) b.setX(x);
                if(b.getY() != y + height) b.setY(y + height);
                b.draw(mouseX, mouseY);
                height += b.getHeight();
            }
        }
    }

    private int getColor(int mouseX, int mouseY){
        Color color = module.isEnabled() ? Zori.getInstance().clientSettings.getColorr(200) : new Color(50, 50, 50, 200);
        boolean hovered = mouseX > x && mouseY > y && mouseX < x + width && mouseY < y + Wrapper.getFontRenderer().FONT_HEIGHT + 4;
        return hovered ? (module.isEnabled() ? color.darker().darker().getRGB() : color.brighter().brighter().getRGB()) : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + Wrapper.getFontRenderer().FONT_HEIGHT + 4){
            if(button == 0){
                module.toggle();
            } else if(button == 1){
                extended = !extended;
            }
            return;
        }

        if(extended){
            for(SubButton b : buttons){
                b.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(extended){
            for(SubButton b : buttons){
                b.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        if(extended){
            for(SubButton b : buttons){
                b.keyTyped(typedChar, keyCode);
            }
        }
    }

    public Panel getPanel(){
        return panel;
    }

    public Module getModule() {
        return module;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }
    public void setX(int x){
        this.x = x;
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
}
