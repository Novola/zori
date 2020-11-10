package club.novola.zori.module.combat;

import club.novola.zori.setting.Setting;
import club.novola.zori.Zori;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OffhandCrystal extends Module {
    public OffhandCrystal() {
        super("OffhandCrystal", Category.COMBAT);
    }

    private boolean dragging = false;
    private int totems = 0;

    private Setting<Integer> health = register("Health", 15, 0, 40);
    private Setting<Boolean> totemOnDisable = register("TotemOnDisable", false);
    private Setting<Boolean> disableOthers = register("DisableOthers", false);

    public String getHudInfo(){
        return totems + "";
    }

    public void onEnable() {
        if(disableOthers.getValue()){
            AutoTotem autoTotem = (AutoTotem) Zori.getInstance().moduleManager.getModuleByName("AutoTotem");
            OffhandGap offhandGap = (OffhandGap) Zori.getInstance().moduleManager.getModuleByName("OffhandGap");
            if(autoTotem.isEnabled()) autoTotem.disable();
            if(offhandGap.isEnabled()) offhandGap.disable();
        }
    }

    public void onDisable() {
        AutoTotem autoTotem = (AutoTotem) Zori.getInstance().moduleManager.getModuleByName("AutoTotem");
        if(totemOnDisable.getValue());
        autoTotem.enable();
    }

	// called every tick even if the module is disabled
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(isEnabled()) return; // return if the module is enabled because we're already calling onUpdte()
        if(!totemOnDisable.getValue() || Wrapper.mc.currentScreen instanceof GuiContainer || Wrapper.getPlayer() == null){
            MinecraftForge.EVENT_BUS.unregister(this); // unregister if it shouldn't / can't use a totem
            return;
        }

		// click on an empty slot if we're dragging an unwanted item
        if(!Wrapper.getPlayer().inventory.getItemStack().isEmpty() && !dragging){
            for(int i = 0; i < 45; i++) {
                if(Wrapper.getPlayer().inventory.getStackInSlot(i).isEmpty() || Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() == Items.AIR){
                    int slot = i < 9 ? i + 36 : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
                    return;
                }
            }
        }

        if(Wrapper.getPlayer().getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING){
            MinecraftForge.EVENT_BUS.unregister(this); // unregister if we're holding a totem
            dragging = false;
            return;
        }

		// click on offhand slot if we're dragging a totem
        if(dragging){
            Wrapper.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, Wrapper.getPlayer());
            dragging = false;
            return;
        }

		// look for a totem and grab it
        for(int i = 0; i < 45; i++) {
            if(Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING){
                int slot = i < 9 ? i + 36 : i;
                Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.getPlayer());
                dragging = true;
                return;
            }
        }
    }

    public void onUpdate() {
        if(Wrapper.mc.currentScreen instanceof GuiContainer) return;
        EntityPlayerSP player = Wrapper.getPlayer();
        if(player == null) return;

		// click on an empty slot if we're dragging an unwanted item
        if(!player.inventory.getItemStack().isEmpty() && !dragging){
            for(int i = 0; i < 45; i++) {
                if(player.inventory.getStackInSlot(i).isEmpty() || player.inventory.getStackInSlot(i).getItem() == Items.AIR){
                    int slot = i < 9 ? i + 36 : i;
                    Wrapper.mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, player);
                    return;
                }
            }
        }

		// get amount of totems in inventory
        totems = player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING ? player.getHeldItemOffhand().getCount() : 0;
        for(ItemStack stack : player.inventory.mainInventory){
            if(stack.getItem() == Items.TOTEM_OF_UNDYING)
                totems += stack.getCount();
        }

        Item item = shouldTotem() ? Items.TOTEM_OF_UNDYING : Items.END_CRYSTAL; // check which item we should use, for OffhandGapple just change Items.END_CRYSTAL to Items.APPLE_GOLDEN
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

    private boolean shouldTotem(){
        boolean hp = Wrapper.getPlayer().getHealth() + Wrapper.getPlayer().getAbsorptionAmount() <= health.getValue(); // check if player's health + absorption is lower than the hp setting
        boolean totemCount = totems > 0 || dragging || !Wrapper.getPlayer().inventory.getItemStack().isEmpty(); // check if we have totems

        return hp && totemCount;
    }
}