package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.hack.HackCategory;

public class NoGuiEvents extends Hack{

	public NoGuiEvents() {
		super("NoGuiEvents", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Disables events when the GUI is open.";
	}
}
