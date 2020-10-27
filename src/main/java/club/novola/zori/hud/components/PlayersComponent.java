package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.gui.Players;
import club.novola.zori.util.EntityUtils;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import joptsimple.internal.Strings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayersComponent extends HudComponent<Players> {

    public PlayersComponent() {
        super("Players", 0, 100, Players.INSTANCE);
        width = 0;
        height = 10;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private List<String> players;

    @Override
    public void render() {
        super.render();
        if(Wrapper.getWorld() == null || Wrapper.getPlayer() == null || players == null || players.isEmpty()) return;
        int yy = 0;
        for(String text : players){
            drawString(text, x, y + yy);
            yy += Wrapper.getFontRenderer().FONT_HEIGHT;
        }
        height = Math.max(yy, 10);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(Wrapper.getWorld() == null || Wrapper.getPlayer() == null || isInvisible()) return;
        players = new ArrayList<>();
        boolean isOnCpvp = Wrapper.mc.getCurrentServerData() != null &&
                !Strings.isNullOrEmpty(Wrapper.mc.getCurrentServerData().serverIP) &&
                Wrapper.mc.getCurrentServerData().serverIP.equalsIgnoreCase("crystalpvp.cc");

        for(EntityPlayer player : Wrapper.getWorld().playerEntities){
            if(player == Wrapper.getPlayer()) continue;
            if(isOnCpvp && player.posY > 110) continue;
            String name = player.getName();
            ChatFormatting color = ChatFormatting.GRAY;
            switch(Zori.getInstance().playerStatus.getStatus(name)){
                case 1:
                    color = ChatFormatting.AQUA;
                    break;
                case -1:
                    color = ChatFormatting.RED;
                    break;
            }
            String strength = hasStrength(player) ? ChatFormatting.DARK_RED + "[ST] " : "";
            StringBuilder sb = new StringBuilder(strength).append(color).append(name).append(" ").append(EntityUtils.INSTANCE.getColoredHP(player));
            players.add(sb.toString());
            int w;
            switch(module.align.getValue()){
                case LEFT:
                    w = Wrapper.getFontRenderer().getStringWidth(sb.toString());
                    if(w > width) width = w;
                    break;
                case RIGHT:
                    w = -Wrapper.getFontRenderer().getStringWidth(sb.toString());
                    if(w < width) width = w;
                    break;
            }
        }

        if(width == 0){
            switch(module.align.getValue()){
                case LEFT:
                    width = 10;
                    break;
                case RIGHT:
                    width = -10;
                    break;
            }
        }
    }

    private boolean hasStrength(EntityPlayer player){
        return  player.isPotionActive(MobEffects.STRENGTH); // TODO: doesn't seem to always work
    }

    private void drawString(String text, int x, int y){
        switch (module.align.getValue()){
            case LEFT:
                Wrapper.getFontRenderer().drawStringWithShadow(text, x, y, -1);
                break;
            case RIGHT:
                Wrapper.getFontRenderer().drawStringWithShadow(text, x - Wrapper.getFontRenderer().getStringWidth(text), y, -1);
                break;
        }
    }
}
