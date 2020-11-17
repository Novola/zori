package club.novola.zori.module.misc;

import club.novola.zori.command.Command;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;

public class DonkeyFinder extends Module {

    private int timer;

    public DonkeyFinder(){
        super("DonkeyFinder", Category.MISC);
    }

    public void onUpdate() {
        if (Wrapper.mc.player != null && Wrapper.mc.world != null) {
            timer++;
            for (final Entity e : Wrapper.mc.world.loadedEntityList) {
                if (e instanceof EntityDonkey && timer >= 100) {
                    Command.sendClientMessage(ChatFormatting.GREEN + " Found Donkey! X:" + (int) e.posX + " Z:" + (int) e.posZ, true);
                    timer = -150;
                }
                if (e instanceof EntityMule && timer >= 100) {
                    Command.sendClientMessage(ChatFormatting.GREEN + " Found Mule! X:" + (int) e.posX + " Z:" + (int) e.posZ, true);
                    timer = -150;
                }
                if (e instanceof EntityLlama && timer >= 100) {
                    Command.sendClientMessage(ChatFormatting.GREEN + " Found Llama! X:" + (int) e.posX + " Z:" + (int) e.posZ, true);
                    timer = -150;
                }
            }
        }
    }
}
