package club.novola.zori.module.client;

import club.novola.zori.module.Module;

public class Capes extends Module {

    public static Boolean enabled = false;

    public Capes(){
        super("Capes", Category.CLIENT);
    }

    public void onEnable(){
        enabled = true;
    }

    public void onDisable(){
        enabled = false;
    }
}
