package novola.zori.club.gui.click.elements;

import novola.zori.club.gui.click.base.Component;
import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.wrappers.Wrapper;
import org.lwjgl.input.Keyboard;

import novola.zori.club.gui.click.base.Component;
import novola.zori.club.gui.click.base.ComponentType;
import novola.zori.club.hack.Hack;
import novola.zori.club.wrappers.Wrapper;

public class KeybindMods extends Component {

    private Hack mod;

    private boolean editing;

    public KeybindMods(int xPos, int yPos, int width, int height, Component component, Hack mod) {

        super(xPos, yPos, width, height, ComponentType.KEYBIND, component, "");
        this.mod = mod;
    }

    @Override
    public void onUpdate() {
        if (Keyboard.getEventKeyState()) {
            if (editing) {
                if (Keyboard.getEventKey() == Keyboard.KEY_DELETE)
                    mod.setKey(-1);
                else
                    mod.setKey(Keyboard.getEventKey());
                editing = false;
            }
        }
    }


    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (x > this.getX() + Wrapper.INSTANCE.fontRenderer().getStringWidth("Key") + 6 && x < this.getX() + this.getDimension().width && y > this.getY() && y < this.getY() + this.getDimension().height) {
            editing = !editing;
        }
    }

    public Hack getMod() {
        return mod;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
}
