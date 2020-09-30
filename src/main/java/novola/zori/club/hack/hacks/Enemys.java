package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.hack.HackCategory;

public class Enemys extends Hack{

	public Enemys() {
		super("Enemys", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Target only in enemy list.";
	}
}
