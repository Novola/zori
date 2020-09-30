package novola.zori.club.hack.hacks;

import java.lang.reflect.Field;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.system.Mapping;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;

public class Suicide extends Hack{
	
	public NumberValue damage;
	
	public Suicide() {
		super("Suicide", HackCategory.COMBAT);
		
		damage = new NumberValue("Damage", 0.35D, 0.0125D, 0.50D);
		
		this.addValue(damage);
	}
	
	@Override
	public String getDescription() {
		return "Kills you.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Wrapper.INSTANCE.player().isDead) this.toggle();
		Utils.selfDamage(damage.getValue().doubleValue());
		super.onClientTick(event);
	}
	
}
