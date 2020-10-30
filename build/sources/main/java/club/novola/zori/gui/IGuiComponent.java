package club.novola.zori.gui;

import java.io.IOException;

public interface IGuiComponent {

    void draw(int mouseX, int mouseY);

    void mouseClicked(int mouseX, int mouseY, int button) throws IOException;

    void mouseReleased(int mouseX, int mouseY, int state);

    void keyTyped(char typedChar, int keyCode) throws IOException;

}
