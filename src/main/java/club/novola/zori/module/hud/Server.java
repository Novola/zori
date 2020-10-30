package club.novola.zori.module.hud;

import club.novola.zori.module.Module;

public class Server extends Module {
    public Server() {
        super("Server", Category.HUD);
        INSTANCE = this;
    }

    public static Server INSTANCE;
}
