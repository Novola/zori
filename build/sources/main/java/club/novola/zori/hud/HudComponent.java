package club.novola.zori.hud;

import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class HudComponent<T extends Module> {

    private final String name;
    protected int x;
    protected int y;
    protected int width = 10;
    protected int height = Wrapper.getFontRenderer().FONT_HEIGHT;
    protected final T module;

    private boolean dragging = false;
    private int dragX = 0;
    private int dragY = 0;

	// needs a parent module, name is used for HudComponentManager.getComponentByName()
    public HudComponent(String name, int x, int y, T module) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.module = module;
    }

	// render when the clickgui is opened
    public void renderInGui(int mouseX, int mouseY) {
        if(isInvisible()) return;

        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }
	
		// draw background
        Gui.drawRect(x, y, x + width, y + height, 0xaa333333);
        render();
    }

	// render when the clickgui is not opened
    public void render() {
        if(Wrapper.getPlayer() != null) {
            int screenWidth = new ScaledResolution(Wrapper.mc).getScaledWidth(); // this causes the components to fit on screen whenever you resize the window, maybe you can instead save to ScaledResolution to a variable when the client is initialized instead
            int screenHeight = new ScaledResolution(Wrapper.mc).getScaledHeight();

            if (width < 0) {
                if (x > screenWidth) x = screenWidth;
                if (x + width < 0) x = -width;
            } else {
                if (x < 0) x = 0;
                if (x + width > screenWidth) x = screenWidth - width;
            }

            if (y < 0) y = 0;
            if (y + height > screenHeight) y = screenHeight - height;
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if(isInvisible()) return;

        if(width < 0){
            if(button == 0 && mouseX < x && mouseX > x + width && mouseY > y && mouseY < y + height){
                dragX = x - mouseX;
                dragY = y - mouseY;
                dragging = true;
            }
        } else {
            if(button == 0 && mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height){
                dragX = x - mouseX;
                dragY = y - mouseY;
                dragging = true;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(isInvisible()) return;

        if (state == 0) dragging = false;
    }

    public void onGuiClosed(){
        dragging = false;
    }

    public boolean isInvisible(){
        return !module.isEnabled();
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
