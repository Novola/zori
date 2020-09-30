package novola.zori.club.hack.hacks;

import java.util.ArrayList;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.HackManager;
import novola.zori.club.managers.PickupFilterManager;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.value.BooleanValue;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.PickupFilterManager;

public class PickupFilter extends Hack{
	
	public BooleanValue all;
	
	public PickupFilter() {
		super("PickupFilter", HackCategory.ANOTHER);
		
		this.all = new BooleanValue("IgnoreAll", false);
		this.addValue(all);
	}
	
	@Override
	public String getDescription() {
		return "Filters item picking.";
	}
	
	@Override
	public void onItemPickup(EntityItemPickupEvent event) {
		if(this.all.getValue()) {
			event.setCanceled(true);
			return;
		}
		for(int itemId : PickupFilterManager.items) {
			Item item = Item.getItemById(itemId);
			if(item == null) continue;
			if(event.getItem().getItem().getItem() == item)
				event.setCanceled(true);
		}
		super.onItemPickup(event);
	}
}
