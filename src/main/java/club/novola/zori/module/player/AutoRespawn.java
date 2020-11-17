package club.novola.zori.module.player;

import club.novola.zori.command.Command;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AutoRespawn extends Module {
    private Setting<Boolean> deathCoords = register("DeathCoords", false);
    private Setting<Boolean> respawn = register("Respawn", true);
    private Setting<Boolean> autoCope = register("AutoCope (WIP)", false);

    public AutoRespawn() {
        super("AutoRespawn", Category.PLAYER);
    }

    public void onUpdate(){
        if (Wrapper.mc.currentScreen instanceof GuiGameOver && Wrapper.mc.player != null && Wrapper.mc.world != null) {
            if (this.deathCoords.getValue() && Wrapper.mc.player.getHealth() <= 0.0f) {
                Command.sendClientMessage(ChatFormatting.BLUE + String.format("You died at x %d y %d z %d", (int)Wrapper.mc.player.posX, (int)Wrapper.mc.player.posY, (int)Wrapper.mc.player.posZ), true);
            }
            if ((this.respawn.getValue() && Wrapper.mc.player.getHealth() <= 0.0f)) {
                Wrapper.mc.displayGuiScreen((GuiScreen)null);
                Wrapper.mc.player.respawnPlayer();
            }
            if ((this.autoCope.getValue() && Wrapper.mc.player.getHealth() <= 0.0f)) {
                List<String> copeList = new ArrayList<>();
                copeList.add("Your just a ping player.");
                copeList.add("My game just froze.");
                copeList.add("My brother was playing.");
                copeList.add("Im using forgehax.");
                copeList.add("Thats photoshop, I didnt actually die.");
                copeList.add("I think im getting DDoSed.");
                copeList.add("My ping is so high today.");
                copeList.add("Im so weed rn.");

                Wrapper.mc.player.sendChatMessage(getRandomCope(copeList));
            }
        }
    }

    public String getRandomCope(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
