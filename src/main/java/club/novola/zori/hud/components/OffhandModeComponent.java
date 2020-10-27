package club.novola.zori.hud.components;

import club.novola.zori.Zori;
import club.novola.zori.module.gui.OffhandMode;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import club.novola.zori.hud.HudComponent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class OffhandModeComponent extends HudComponent<OffhandMode> {
    public OffhandModeComponent() {
        super("OffhandMode", 0, 190, OffhandMode.INSTANCE);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private int totems = 0;

    @Override
    public void render() {
        super.render();
        String mode = isEnabled("AutoTotem") ? "TOTEM " : isEnabled("OffhandCrystal") ? "CRYSTAL " : "NONE ";
        String s = mode + ChatFormatting.GRAY + totems;
        Wrapper.getFontRenderer().drawStringWithShadow(s, x, y, Zori.getInstance().clientSettings.getColor());
        width = Wrapper.getFontRenderer().getStringWidth(s);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(Wrapper.getPlayer() == null) return;
        totems = Wrapper.getPlayer().getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING ? Wrapper.getPlayer().getHeldItemOffhand().getCount() : 0;
        for(ItemStack stack : Wrapper.getPlayer().inventory.mainInventory){
            if(stack.getItem() == Items.TOTEM_OF_UNDYING)
                totems += stack.getCount();
        }
    }

    private boolean isEnabled(String name){
        return Zori.getInstance().moduleManager.getModuleByName(name).isEnabled();
    }
}
