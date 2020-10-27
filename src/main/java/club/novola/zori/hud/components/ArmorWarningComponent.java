package club.novola.zori.hud.components;

import club.novola.zori.hud.HudComponent;
import club.novola.zori.module.gui.ArmorWarning;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ArmorWarningComponent extends HudComponent<ArmorWarning> {
    public ArmorWarningComponent() {
        super("ArmorWarning", (new ScaledResolution(Wrapper.mc).getScaledWidth() / 2) - (Wrapper.getFontRenderer().getStringWidth("LOW ARMOR") / 2), 10, ArmorWarning.INSTANCE);
        width = Wrapper.getFontRenderer().getStringWidth("Armor low!");
        height = Wrapper.getFontRenderer().FONT_HEIGHT;
        MinecraftForge.EVENT_BUS.register(this); // so we can listen for the ClientTickEvent
    }

    private boolean isArmorLow = false;

    @Override
    public void renderInGui(int mouseX, int mouseY) {
        super.renderInGui(mouseX, mouseY);
        if(!isArmorLow)
            Wrapper.getFontRenderer().drawStringWithShadow("Armor Warning", x, y, -1);
    }

    @Override
    public void render(){
        super.render();
        if(isArmorLow)
            Wrapper.getFontRenderer().drawStringWithShadow(ChatFormatting.BOLD + "Armor low!", x, y, 0xffff0000);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(Wrapper.getPlayer() == null || isInvisible()) return;
        isArmorLow = false;
		// check all armor the player is wearing
        for(ItemStack stack : Wrapper.getPlayer().inventory.armorInventory){
			// calculate damage percentage
            double dmg = stack.getItemDamage();
            double max = stack.getMaxDamage();
            if(dmg <= 0d || max <= 0d) continue;

            double percent = dmg / max;
            percent *= 100d; // percentage of the damage, NOT the durability

			// if damage is more than 65% break the loop
            if(percent > 65d){
                isArmorLow = true;
                break;
            }
        }
    }
}
