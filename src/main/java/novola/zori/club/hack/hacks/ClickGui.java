package novola.zori.club.hack.hacks;

import java.awt.Color;

import novola.zori.club.hack.HackCategory;
import novola.zori.club.wrappers.Wrapper;
import org.lwjgl.input.Keyboard;

import novola.zori.club.Main;
import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.visual.ColorUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClickGui extends Hack{

	public ModeValue theme;
	public static BooleanValue rainbow;
	public static BooleanValue shadow;
	public static BooleanValue tooltip;
	
	public static NumberValue red;
	public static NumberValue green;
	public static NumberValue blue;
	public static NumberValue alpha;
	
	private static int color;
	public static boolean isLight = false;
	
	public ClickGui() {
		super("ClickGui", HackCategory.VISUAL);
		this.setKey(Keyboard.KEY_RSHIFT);
		this.setShow(false);
		
		this.theme = new ModeValue("Theme", new Mode("Dark", true), new Mode("Light", false));
		
		this.tooltip = new BooleanValue("Tooltip", true);
		this.shadow = new BooleanValue("Shadow", true);
		this.rainbow = new BooleanValue("Rainbow", true);
		this.red = new NumberValue("Red", 255D, 0D, 255D);
		this.green = new NumberValue("Green", 255D, 0D, 255D);
		this.blue = new NumberValue("Blue", 255D, 0D, 255D);
		this.alpha = new NumberValue("Alpha", 255D, 0D, 255D);
		
		this.addValue(theme, tooltip, shadow, rainbow, red, green, blue, alpha);
		this.setColor();
	}
	
	@Override
	public String getDescription() {
		return "Graphical user interface.";
	}
	
	 public static int getColor() {
		 return rainbow.getValue() ? ColorUtils.rainbow().getRGB() : color;
	 }
	
	 public static void setColor() {
		color = ColorUtils.color(red.getValue().intValue(),
				green.getValue().intValue(),
				blue.getValue().intValue(),
				alpha.getValue().intValue());
	}
	
	@Override
	public void onEnable() {
		if(GhostMode.enabled) 
			return;
		Wrapper.INSTANCE.mc().displayGuiScreen(Main.hackManager.getGui());
		super.onEnable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		this.setColor();
		this.isLight = theme.getMode("Light").isToggled();
		super.onClientTick(event);
	}
	
	@Override
	public void onRenderGameOverlay(Text event) {
		if(shadow.getValue()) {
			ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
			RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtils.color(0.0F, 0.0F, 0.0F, 0.5F));
		}
		super.onRenderGameOverlay(event);
	}

}
