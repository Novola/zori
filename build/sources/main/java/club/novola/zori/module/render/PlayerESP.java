package club.novola.zori.module.render;

import club.novola.zori.module.Module;
import club.novola.zori.util.EntityUtils;
import club.novola.zori.util.RenderUtils;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

public class PlayerESP extends Module {
    public PlayerESP() {
        super("PlayerESP", Category.RENDER);
    }

    @Override
    public void onRender3D(){
        for(EntityPlayer player : Wrapper.getWorld().playerEntities){
            if(player != Wrapper.getPlayer()){
                Color c = EntityUtils.INSTANCE.getColoredHPB(player);
                RenderUtils.INSTANCE.drawBoundingBox(player.getRenderBoundingBox(), c.getRed() / 255, c.getGreen() / 255, c.getBlue() / 255, 1f, 1f);
                //RenderUtils.INSTANCE.drawBox(player.getPosition(), c.getRed() / 255, c.getGreen() / 255, c.getBlue() / 255, 0.22f);
            }
        }
    }
}
