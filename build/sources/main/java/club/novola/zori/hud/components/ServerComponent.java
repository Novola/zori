package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.hud.Server;
import club.novola.zori.util.Wrapper;

public class ServerComponent extends HudComponent<Server> {
    public ServerComponent() {
        super("Server", 2, 2, Server.INSTANCE);
    }

    @Override
    public void renderInGui(int mouseX, int mouseY) {
        super.renderInGui(mouseX, mouseY);
            Wrapper.getFontRenderer().drawStringWithShadow("Server", x, y, -1);
    }

    @Override
    public void render() {
        super.render();
        Wrapper.getFontRenderer().drawStringWithShadow("Server: " + Wrapper.mc.getCurrentServerData().serverIP, x, y, Zori.getInstance().clientSettings.getColor());
        width = Wrapper.getFontRenderer().getStringWidth(Zori.getInstance().toString());
    }
}