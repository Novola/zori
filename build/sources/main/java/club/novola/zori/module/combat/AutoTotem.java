package club.novola.zori.module.combat;

import club.novola.zori.setting.Setting;
import club.novola.zori.Zori;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", Category.COMBAT);
    }

    private Setting<Boolean> soft = register("Soft", false);
    private Setting<Boolean> disableOthers = register("DisableOthers", false);

    private boolean dragging = false;
    private int totems = 0;

    public String getHudInfo(){
        return totems + "";
    }

    public void onEnable() {
        if(disableOthers.getValue()){
            OffhandCrystal offhandCrystal = (OffhandCrystal) Zori.getInstance().moduleManager.getModuleByName("OffhandCrystal");
            OffhandGap offhandGap = (OffhandGap) Zori.getInstance().moduleManager.getModuleByName("OffhandGap");
            if(offhandCrystal.isEnabled()) offhandCrystal.disable();
            if(offhandGap.isEnabled()) offhandGap.disable();
        }
    }

	// never click more than once per tick if you're making something that uses the inventory
    public void onUpdate() {
        if(Wrapper.mc.currentScreen instanceof GuiContainer) return;
        EntityPlayerSP player = Wrapper.getPlayer();
        if(player == null) return;

		// click on an empty slot if we're dragging an item when we're not supposed to
        if(!player.inventory.getItemStack().isEmpty() && !dragging){
            for(int i = 0;i < 45; i++){
                if(player.inventory.getStackInSlot(i).isEmpty() || player.inventory.getStackInSlot(i).getItem() == Items.AIR){
                    int slot = i < 9 ? i + 36 : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, player);
                    return;
                }
            }
        }

		// get amount of totems in inventory
        totems = 0;
        for(ItemStack stack : player.inventory.mainInventory){
            if(stack.getItem() == Items.TOTEM_OF_UNDYING)
                totems += stack.getCount();
        }

        if(player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING){
            totems += player.getHeldItemOffhand().getCount();
            return;
        }

        if(soft.getValue() && !player.getHeldItemOffhand().isEmpty()) return;

		// click on the offhand slot if we're dragging a totem
        if(dragging){
            Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, player);
            dragging = false;
            return;
        }

		// look for a totem and grab it
        for(int i = 0; i < 45; i++){
            if(player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING){
                int slot = i < 9 ? i + 36 : i;
                Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, player);
                dragging = true;
                return;
            }
        }
    }
}
