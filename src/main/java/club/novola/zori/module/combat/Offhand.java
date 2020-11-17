package club.novola.zori.module.combat;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Offhand extends Module {

    public Offhand() {
        super("Offhand", Category.COMBAT);
    }

    private Setting<Integer> health = register("Health", 16, 0, 40);
    private Setting<Boolean> soft = register("Soft", false);
    private Setting<Offhand.Mode> mode = register("Mode", Offhand.Mode.TOTEM);

    Item item;

    private boolean dragging = false;
    private int totems = 0;

    public String getHudInfo(){
        return totems + "";
    }

    // never click more than once per tick if you're making something that uses the inventory
    public void onUpdate() {
        if (Wrapper.mc.currentScreen instanceof GuiContainer) return;
        EntityPlayerSP player = Wrapper.getPlayer();
        if (player == null) return;

        // click on an empty slot if we're dragging an item when we're not supposed to
        if (!player.inventory.getItemStack().isEmpty() && !dragging) {
            for (int i = 0; i < 45; i++) {
                if (player.inventory.getStackInSlot(i).isEmpty() || player.inventory.getStackInSlot(i).getItem() == Items.AIR) {
                    int slot = i < 9 ? i + 36 : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, player);
                    return;
                }
            }
        }

        if(mode.getValue().equals(Mode.TOTEM)) {
            // get amount of totems in inventory
            totems = 0;
            for (ItemStack stack : player.inventory.mainInventory) {
                if (stack.getItem() == Items.TOTEM_OF_UNDYING)
                    totems += stack.getCount();
            }

            if (player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                totems += player.getHeldItemOffhand().getCount();
                return;
            }

            if (soft.getValue() && !player.getHeldItemOffhand().isEmpty()) return;

            // click on the offhand slot if we're dragging a totem
            if (dragging) {
                Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, player);
                dragging = false;
                return;
            }

            // look for a totem and grab it
            for (int i = 0; i < 45; i++) {
                if (player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                    int slot = i < 9 ? i + 36 : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, player);
                    dragging = true;
                    return;
                }
            }
        }else{
            // get amount of totems in inventory
            totems = player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING ? player.getHeldItemOffhand().getCount() : 0;
            for(ItemStack stack : player.inventory.mainInventory){
                if(stack.getItem() == Items.TOTEM_OF_UNDYING)
                    totems += stack.getCount();
            }

            if(mode.getValue().equals(Mode.CRYSTAL)) {
                item = shouldTotem() ? Items.TOTEM_OF_UNDYING : Items.END_CRYSTAL;
            }else{
                item = shouldTotem() ? Items.TOTEM_OF_UNDYING : Items.GOLDEN_APPLE;
            }
            if(player.getHeldItemOffhand().getItem() == item) return; // return if we're already holding the item

            // click on offhand slot if we're dragging the item
            if(dragging){
                Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, player);
                dragging = false;
                return;
            }

            // look for the item and grab it
            for(int i = 0; i < 45; i++) {
                if(player.inventory.getStackInSlot(i).getItem() == item){
                    int slot = i < 9 ? i + 36 : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, player);
                    dragging = true;
                    return;
                }
            }
        }
    }

    private boolean shouldTotem(){
        boolean hp = Wrapper.getPlayer().getHealth() + Wrapper.getPlayer().getAbsorptionAmount() <= health.getValue(); // check if player's health + absorption is lower than the hp setting
        boolean totemCount = totems > 0 || dragging || !Wrapper.getPlayer().inventory.getItemStack().isEmpty(); // check if we have totems

        return hp && totemCount;
    }

    public enum Mode{
        TOTEM, CRYSTAL, GAP
    }
}
