package club.novola.zori.module.combat;

import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import club.novola.zori.event.PacketSendEvent;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Aura extends Module {
    public Aura() {
        super("Aura", Category.COMBAT);
    }

    private Setting<Mode> mode = register("Mode", Mode.SMART);
    private Setting<Double> range = register("Range", 5.5d, 0.0d, 7.0d);
    private Setting<Boolean> rotate = register("Rotate", true);
    private Setting<Boolean> crits = register("Criticals", true);
    private Setting<Boolean> delay = register("Delay", true);

    private boolean isAttacking = false;

    @Override
    public String getHudInfo(){
        return mode.getValue().name();
    }

    @Override
    public void onUpdate() {
        if(Wrapper.getWorld() == null || Wrapper.getPlayer() == null || Wrapper.getWorld().playerEntities.isEmpty()) return; // if the client world or client player don't exist or there are no players in render distance return
        if(mode.getValue().equals(Mode.SMART)){
            if(!(Wrapper.getPlayer().getHeldItemMainhand().getItem() instanceof ItemSword)) return; // return if the player is not holding a sword
            if(((AutoCrystal) Zori.getInstance().moduleManager.getModuleByName("AutoCrystal")).isActive) return; // return if AutoCrystal is hitting / placing
        }

        List<EntityPlayer> list = new ArrayList<>();
		// loop through all players in render distance
        for(EntityPlayer player : Wrapper.getWorld().playerEntities){
            if(player == Wrapper.getPlayer()) continue; // ignore if it's the client player
            if(Wrapper.getPlayer().getDistance(player) > range.getValue()) continue; // ignore if the player is too far
            if(player.getHealth() <= 0 || player.isDead) continue; // ignore if the player is dead
            if(Zori.getInstance().playerStatus.getStatus(player.getName()) == 1) continue; // ignore if the player is a friend
            list.add(player); // add to the list
        }
        if(list.isEmpty()) return; // return if no valid players were found
		// if there are enemies in range prioritise them, otherwise sort by distance
        list.sort(Comparator.comparing(p -> Zori.getInstance().playerStatus.isEnemyInRange(range.getValue()) ? Zori.getInstance().playerStatus.getStatus(p.getName()) : Wrapper.getPlayer().getDistance(p)));
        attack(list.get(0)); // attack the first player in the list - closest / enemy
    }

	// criticals
    @SubscribeEvent
    public void onSendPacket(PacketSendEvent event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
            if (crits.getValue() && packet.getAction().equals(CPacketUseEntity.Action.ATTACK) && Wrapper.getPlayer() != null && Wrapper.getPlayer().onGround && isAttacking) {
                Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.1f, Wrapper.getPlayer().posZ, false));
                Wrapper.getPlayer().connection.sendPacket(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));
            }
        }
    }

    private void attack(EntityPlayer target) {
        if (Wrapper.getPlayer().getCooledAttackStrength(0f) >= 1f || !delay.getValue()) { // check hit delay
            isAttacking = true; // set variable for criticals
            boolean rotatee = rotate.getValue(); // check rotate setting
            if (rotatee) Zori.getInstance().rotationManager.rotate(target.posX, target.posY, target.posZ); // rotate to the target
            Wrapper.mc.playerController.attackEntity(Wrapper.getPlayer(), target); // attack
            Wrapper.getPlayer().swingArm(EnumHand.MAIN_HAND); // swing hand
            if (rotatee) Zori.getInstance().rotationManager.reset(); // reset rotation
            isAttacking = false; // set variable for criticals
        }
    }

    public enum Mode{
        NORMAL, SMART
    }
}
