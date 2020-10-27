package club.novola.zori.gui.button;

import club.novola.zori.gui.IGuiComponent;

import java.io.IOException;

public class SubButton implements IGuiComponent {
    private int x;
    private int y;
    private int width;
    private int height;
    private ModuleButton parent;

    public SubButton(int x, int y, int width, int height, ModuleButton parent){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = parent;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public ModuleButton getParent() {
        return parent;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
    }
}
