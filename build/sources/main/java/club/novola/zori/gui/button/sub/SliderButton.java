package club.novola.zori.gui.button.sub;

import club.novola.zori.Zori;
import club.novola.zori.gui.button.ModuleButton;
import club.novola.zori.gui.button.SubButton;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;

public class SliderButton<T> extends SubButton {
    SliderButton(int x, int y, ModuleButton parent, Setting<T> setting) {
        super(x, y, parent.getWidth() - 2, Wrapper.getFontRenderer().FONT_HEIGHT + 4, parent);
        this.setting = setting;
    }

    private Setting<T> setting;

    protected boolean dragging = false;

    protected int sliderWidth = 0;

    protected void updateSlider(int mouseX){
    }

    @Override
    public void draw(int mouseX, int mouseY){
        updateSlider(mouseX);

        //left margin
        Gui.drawRect(getX(), getY(), getX() + 2, getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        //background
        Gui.drawRect(getX() + 2 + sliderWidth, getY(), getX() + 2 + getWidth(), getY() + getHeight() - 1, getColor(mouseX, mouseY));
        //slider
        Gui.drawRect(getX() + 2, getY(), getX() + 2 + sliderWidth, getY() + getHeight() - 1, getSliderColor(mouseX, mouseY));
        //bottom
        Gui.drawRect(getX() + 2, getY() + getHeight() - 1, getX() + 2 + getWidth(), getY() + getHeight(), new Color(10, 10, 10, 200).getRGB());
        //setting name
        Wrapper.getFontRenderer().drawStringWithShadow(setting.getName(), getX() + 4, getY() + 2, -1);
        //setting value
        String val = "" + ChatFormatting.GRAY + setting.getValue();
        Wrapper.getFontRenderer().drawStringWithShadow(val, getX() + getWidth() - Wrapper.getFontRenderer().getStringWidth(val), getY() + 2, -1);
    }

    private int getColor(int mouseX, int mouseY){
        Color color = new Color(50, 50, 50, 200);
        boolean hovered = mouseX > getX() + 2 + sliderWidth && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1;
        return hovered ? color.brighter().brighter().getRGB() : color.getRGB();
    }

    private int getSliderColor(int mouseX, int mouseY){
        Color color = Zori.getInstance().clientSettings.getColorr(200);
        boolean hovered = mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + sliderWidth && mouseY < getY() + getHeight() - 1;
        return hovered ? color.darker().darker().getRGB() : color.getRGB();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
        if(mouseX > getX() + 2 && mouseY > getY() && mouseX < getX() + 2 + getWidth() && mouseY < getY() + getHeight() - 1 && button == 0){
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(dragging) dragging = false;
    }

    public static class IntSlider extends SliderButton{
        public IntSlider(Setting<Integer> setting, int x, int y, ModuleButton parent) {
            super(x, y, parent, setting);
            this.settingI = setting;
        }

        private Setting<Integer> settingI;

        @Override
        protected void updateSlider(int mouseX){
            //yes this math stuff is skidded
            double diff = Math.min(getWidth(), Math.max(0, mouseX - getX()));

            double min = settingI.getMin().doubleValue();
            double max = settingI.getMax().doubleValue();

            sliderWidth = (int) (getWidth() * (settingI.getValue() - min) / (max - min));

            if (dragging) {
                if (diff == 0) {
                    settingI.setValue(settingI.getMin());
                } else {
                    DecimalFormat format = new DecimalFormat("##");
                    String newValue = format.format(((diff / getWidth()) * (max - min) + min));
                    settingI.setValue(Integer.parseInt(newValue));
                }
            }
        }
    }

    public static class FloatSlider extends SliderButton{
        public FloatSlider(Setting<Float> setting, int x, int y, ModuleButton parent) {
            super(x, y, parent, setting);
            this.settingF = setting;
        }

        private Setting<Float> settingF;

        @Override
        protected void updateSlider(int mouseX){
            //yes this math stuff is skidded
            float diff = Math.min(getWidth(), Math.max(0, mouseX - getX()));

            float min = settingF.getMin();
            float max = settingF.getMax();

            sliderWidth = (int) (getWidth() * (settingF.getValue() - min) / (max - min));

            if (dragging) {
                if (diff == 0) {
                    settingF.setValue(settingF.getMin());
                } else {
                    DecimalFormat format = new DecimalFormat("##.0");
                    String newValue = format.format(((diff / getWidth()) * (max - min) + min));
                    settingF.setValue(Float.parseFloat(newValue));
                }
            }
        }
    }

    public static class DoubleSlider extends SliderButton{
        public DoubleSlider(Setting<Double> setting, int x, int y, ModuleButton parent) {
            super(x, y, parent, setting);
            this.settingD = setting;
        }

        private Setting<Double> settingD;

        @Override
        protected void updateSlider(int mouseX){
            //yes this math stuff is skidded
            float diff = Math.min(getWidth(), Math.max(0, mouseX - getX()));

            double min = settingD.getMin();
            double max = settingD.getMax();

            sliderWidth = (int) (getWidth() * (settingD.getValue() - min) / (max - min));

            if (dragging) {
                if (diff == 0) {
                    settingD.setValue(settingD.getMin());
                } else {
                    DecimalFormat format = new DecimalFormat("##.0");
                    String newValue = format.format(((diff / getWidth()) * (max - min) + min));
                    settingD.setValue(Double.parseDouble(newValue));
                }
            }
        }
    }
}
