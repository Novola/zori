package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.BooleanValue;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.value.BooleanValue;

public class Targets extends Hack{

	public BooleanValue players;
    public BooleanValue mobs;
    public BooleanValue invisibles;
    public BooleanValue murder;
    
	public Targets() {
		super("Targets", HackCategory.ANOTHER);
		this.setShow(false);
		this.setToggled(true);
		
		players = new BooleanValue("Players", true);
		mobs = new BooleanValue("Mobs", false);
		invisibles = new BooleanValue("Invisibles", false);
		murder = new BooleanValue("Murder", false);
		
		addValue(players, mobs, invisibles, murder);
	}
	
	@Override
	public String getDescription() {
		return "Manage targets for hacks.";
	}
}
