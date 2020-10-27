package club.novola.zori.module.gui;

import club.novola.zori.module.Module;

public class Server extends Module {
    public Server() {
        super("Server", Category.GUI);
        INSTANCE = this;
    }

    public static Server INSTANCE;
}
