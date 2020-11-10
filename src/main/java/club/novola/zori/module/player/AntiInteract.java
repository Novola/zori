package club.novola.zori.module.player;

import club.novola.zori.event.EventPacketSent;
import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiInteract extends Module {

    private Setting<Boolean> foodCheck = register("FoodCheck", false);
    private Setting<Boolean> echests = register("EChests", true);
    private Setting<Boolean> anvils = register("Anvils", true);
    private Setting<AntiInteract.HandMode> handCheck = register("HandCheck", HandMode.BOTH);

    public AntiInteract() {
        super("AntiInteract", Category.PLAYER);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        doAntiInteract(event);
    }

    @SubscribeEvent
    public void onPacket(EventPacketSent event) {
        doAntiInteract(event);
    }

    private void doAntiInteract(Object fin) {
        if(doHandCheck()) {
            return;
        }
        if(fin instanceof EventPacketSent) {
            EventPacketSent event = (EventPacketSent)fin;
            if(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) {
                CPacketPlayerTryUseItemOnBlock packet = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                Block block = Wrapper.mc.world.getBlockState(packet.getPos()).getBlock();
                if(block != null) {
                    if(echests.getValue() && block.equals(Blocks.ENDER_CHEST)) {
                        event.setCanceled(true);
                    }
                    if(anvils.getValue() && block.equals(Blocks.ANVIL)) {
                        event.setCanceled(true);
                    }
                }
            }
        } else if(fin instanceof PlayerInteractEvent.RightClickBlock) {
            PlayerInteractEvent.RightClickBlock event = (PlayerInteractEvent.RightClickBlock)fin;
            Block block = Wrapper.mc.world.getBlockState(event.getPos()).getBlock();
            if(block != null) {
                if(echests.getValue() && block.equals(Blocks.ENDER_CHEST)) {
                    event.setCanceled(true);
                }
                if(anvils.getValue() && block.equals(Blocks.ANVIL)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    private boolean doHandCheck() {
        if(Wrapper.mc.player == null || Wrapper.mc.world == null) {
            return true;
        }
        if(foodCheck.getValue()) {
            switch ((HandMode)handCheck.getValue()) {
                case BOTH:
                    if(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood || Wrapper.mc.player.getHeldItemOffhand().getItem() instanceof ItemFood) {
                        return false;
                    }
                    break;
                case MAINHAND:
                    if(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood) {
                        return false;
                    }
                    break;
                case OFFHAND:
                    if(Wrapper.mc.player.getHeldItemOffhand().getItem() instanceof ItemFood) {
                        return false;
                    }
                    break;
            }
        } else {
            return false;
        }
        return true;
    }

    private enum HandMode {
        BOTH,
        MAINHAND,
        OFFHAND
    }
}
