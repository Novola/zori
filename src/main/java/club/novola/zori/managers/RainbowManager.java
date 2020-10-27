package club.novola.zori.managers;

import club.novola.zori.Zori;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.Color;

public class RainbowManager {
    private float hue;
    private int hex;

    public RainbowManager(){
        hue = 0f;
        hex = -1;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        hue += Zori.getInstance().clientSettings.colors.rainbowSpeed.getValue() / 2000f;
        hex = Color.getHSBColor(hue, 1f, 1f).getRGB();
    }

    public int getHex(){
        return hex;
    }

    public Color getColor(){
        return new Color(hex);
    }

    public Color getColor(int alpha){
        Color c = new Color(hex);
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }
}