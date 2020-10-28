package club.novola.zori.module.misc;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoSlowBypass extends Module {
    private boolean sneaking;

    public NoSlowBypass() {
        super("2bNoSlow", Category.MISC);
    }

    public void onUpdate() {

        Item item = Wrapper.getPlayer().getActiveItemStack().getItem();
        if (Wrapper.getPlayer().isSneaking() && ((!Wrapper.getPlayer().isHandActive() && item instanceof ItemFood || item instanceof ItemBow || item instanceof ItemPotion) || (!(item instanceof ItemFood) || !(item instanceof ItemBow) || !(item instanceof ItemPotion)))) {
            Wrapper.getPlayer().connection.sendPacket(new CPacketEntityAction(Wrapper.getPlayer(), CPacketEntityAction.Action.STOP_SNEAKING));
            sneaking = false;
        }
    }

    @SubscribeEvent
    public void onUseItem() {
        if (!Wrapper.getPlayer().isSneaking()) {
            Wrapper.getPlayer().connection.sendPacket(new CPacketEntityAction(Wrapper.getPlayer(), CPacketEntityAction.Action.START_SNEAKING));
            sneaking = true;
        }
    }
}
