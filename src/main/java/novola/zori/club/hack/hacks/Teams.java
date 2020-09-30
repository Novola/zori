package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.hack.HackCategory;

public class Teams extends Hack{

	public ModeValue mode;
	
	public Teams() {
		super("Teams", HackCategory.ANOTHER);
		this.mode = new ModeValue("Mode", new Mode("Base", false), new Mode("ArmorColor", false), new Mode("NameColor", true));
		this.addValue(mode);
	}
	
	@Override
	public String getDescription() {
		return "Ignore if player in your team.";
	}

}
