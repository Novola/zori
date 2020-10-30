package club.novola.zori.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

import java.text.DecimalFormat;

public class EntityUtils {

    public EntityUtils(){
        INSTANCE = this;
    }

    public static EntityUtils INSTANCE;

    public Vec3d getInterpolatedPos(Entity entity, double ticks){
        double d1 = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * ticks);
        double d2 = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * ticks);
        double d3 = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * ticks);
        return new Vec3d(d1, d2, d3);
    }

    public Vec3d getInterpolatedPos(Entity entity){
        double d1 = entity.lastTickPosX + ((entity.posX - entity.lastTickPosX) * (double)Wrapper.mc.getRenderPartialTicks());
        double d2 = entity.lastTickPosY + ((entity.posY - entity.lastTickPosY) * (double)Wrapper.mc.getRenderPartialTicks());
        double d3 = entity.lastTickPosZ + ((entity.posZ - entity.lastTickPosZ) * (double)Wrapper.mc.getRenderPartialTicks());
        return new Vec3d(d1, d2, d3);
    }

    public Vec3d getInterpolateOffset(Entity entity){
        double d1 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)Wrapper.mc.getRenderPartialTicks();
        double d2 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)Wrapper.mc.getRenderPartialTicks();
        double d3 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)Wrapper.mc.getRenderPartialTicks();
        return new Vec3d(-d1, -d2, -d3);
    }

    public String getColoredHP(EntityPlayer player){ // same color as future client so it matches nametags and stuff
        int hp = (int) (player.getHealth() + player.getAbsorptionAmount());
        ChatFormatting cf = ChatFormatting.DARK_RED; // 0 - 5 = dark red
        if(hp >= 6 && hp <= 8) cf = ChatFormatting.RED; // 6 - 8 = red
        else if(hp >= 9 && hp <= 12) cf = ChatFormatting.GOLD; // 9 - 12 = gold
        else if(hp >= 13 && hp <= 16) cf = ChatFormatting.YELLOW; // 13 - 16 = yellow
        else if(hp >= 17 && hp <= 19) cf = ChatFormatting.DARK_GREEN; // 16 - 19 = dark green
        else if(hp >= 20) cf = ChatFormatting.GREEN; // 20+ = green
        return cf + new DecimalFormat("00").format(hp); // change the decimal format to get a decimal number e.g. "0.##"
    }
}
