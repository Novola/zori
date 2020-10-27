package club.novola.zori.managers;

import club.novola.zori.Zori;
import club.novola.zori.module.client.Colors;
import club.novola.zori.module.client.Settings;

import java.awt.*;

// just a wrapper to get values from the client modules
public class ClientSettings {

    public Colors colors;
    public Settings settings;

    public ClientSettings(){
        colors = (Colors) Zori.getInstance().moduleManager.getModuleByName("Colors");
        settings = (Settings) Zori.getInstance().moduleManager.getModuleByName("Settings");
    }

    public Color getColorr(int alpha){
        if(colors.rainbow.getValue()) return Zori.getInstance().rainbow.getColor(alpha);
        return new Color(colors.red.getValue(), colors.green.getValue(), colors.blue.getValue(), alpha);
    }

    public int getColor(){
        if(colors.rainbow.getValue()) return Zori.getInstance().rainbow.getHex();
        return new Color(colors.red.getValue(), colors.green.getValue(), colors.blue.getValue()).getRGB();
    }

    public int getColor(int alpha){
        if(colors.rainbow.getValue()) return Zori.getInstance().rainbow.getColor(alpha).getRGB();
        return new Color(colors.red.getValue(), colors.green.getValue(), colors.blue.getValue(), alpha).getRGB();
    }

    public String getPrefix(){
        return settings.commandPrefix.getValue();
    }
}
