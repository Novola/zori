package club.novola.zori.module.misc;

import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import com.google.common.collect.Lists;
import club.novola.zori.event.PlayerKillEvent;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SoundEffects extends Module {
    public SoundEffects() {
        super("SoundEffects", Category.MISC);

        Zori.getInstance().log.info("[SoundEffects] Registering Sounds");

        effects = Lists.newArrayList(
                registerS("godlike"),
                registerS("holyshit"),
                registerS("impressive"),
                registerS("ownage"),
                registerS("perfect"),
                registerS("wickedsick")
        );
		hitmarker = registerS("skeet");

        Zori.getInstance().log.info("[SoundEffects] Successfully Registered Sounds");
    }

    private final List<SoundEvent> effects;
	private final SoundEvent hitmarker;
	private int hitDelay = 0;
	
	private final Setting<Boolean> kills = register("Kills", true);
	private final Setting<Boolean> hit = register("HitMarker", true);
	
	@Override
	public void onUpdate(){
		if(!hit.getValue()){
			hitDelay = 0;
			return;
		}
		if(hitDelay > 0) hitDelay--; // reduce hit delay every tick
	}
	
	@Override
	public void onEnable(){
		hitDelay = 0; // reset hit delay
	}

    @SubscribeEvent
    public void onKill(PlayerKillEvent event){
        if(Wrapper.getPlayer() != null && kills.getValue()) Wrapper.getPlayer().playSound(randomSound(), 1f, 1f);
    }
	
	@SubscribeEvent
	public void onAttackEntity(AttackEntityEvent event){
		if(Wrapper.getPlayer() != null && hit.getValue() && hitDelay <= 0){
			Wrapper.getPlayer().playSound(hitmarker, 0.5f, 1f);
			hitDelay = 10;
		}
	}

    private SoundEvent randomSound(){
        return effects.get(ThreadLocalRandom.current().nextInt(0, effects.size()));
    }

    private SoundEvent registerS(String name){
        SoundEvent soundEvent = new SoundEvent(new ResourceLocation(Zori.MODID, name));
        soundEvent.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(soundEvent);
        return soundEvent;
    }
}
