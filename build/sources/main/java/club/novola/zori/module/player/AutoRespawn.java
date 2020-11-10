package club.novola.zori.module.player;

import club.novola.zori.command.Command;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoRespawn extends Module {
    private Setting<Boolean> deathCoords = register("DeathCoords", false);
    private Setting<Boolean> respawn = register("Respawn", true);

    public AutoRespawn() {
        super("AutoRespawn", Category.PLAYER);
    }

    @SubscribeEvent
    public void onDisplayDeathScreen(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            if (this.deathCoords.getValue() && event.getGui() instanceof GuiGameOver) {
                Command.sendClientMessage(ChatFormatting.BLUE + String.format("You died at x %d y %d z %d", (int)Wrapper.mc.player.posX, (int)Wrapper.mc.player.posY, (int)Wrapper.mc.player.posZ), true);
            }
            if ((this.respawn.getValue() && Wrapper.mc.player.getHealth() <= 0.0f)) {
                event.setCanceled(true);
                Wrapper.mc.player.respawnPlayer();
            }
        }
    }
}
