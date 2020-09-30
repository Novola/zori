package novola.zori.club.hack.hacks;

import java.util.LinkedList;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.XRayManager;
import novola.zori.club.utils.BlockUtils;

import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;
import novola.zori.club.xray.XRayBlock;
import novola.zori.club.xray.XRayData;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.XRayManager;
import novola.zori.club.utils.BlockUtils;
import novola.zori.club.utils.TimerUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.value.NumberValue;
import novola.zori.club.wrappers.Wrapper;

public class XRay extends Hack{

    public NumberValue distance;
	public NumberValue delay;

	public TimerUtils timer;
	
	LinkedList<XRayBlock> blocks = new LinkedList<XRayBlock>();
	
	public XRay() {
		super("XRay", HackCategory.VISUAL);
		distance = new NumberValue("Distance", 50D, 4D, 100D);
		delay = new NumberValue("UpdateDelay", 100D, 0D, 300D);
		timer = new TimerUtils();
		this.addValue(distance, delay);
	}

	@Override
	public String getDescription() {
		return "Allows you to see blocks through walls.";
	}
	
	@Override
	public void onEnable() {
		blocks.clear();
	}

	@Override
	public void onClientTick(ClientTickEvent event) {
		int distance = this.distance.getValue().intValue();
		if(!timer.isDelay((long) (delay.getValue().intValue() * 10))) {
			return;
		}
		blocks.clear();
		for(XRayData data : XRayManager.xrayList) {
			for (BlockPos blockPos : BlockUtils.findBlocksNearEntity(Wrapper.INSTANCE.player(), data.getId(), data.getMeta(), distance)) {
				XRayBlock xRayBlock = new XRayBlock(blockPos, data);
				blocks.add(xRayBlock);
			}
		}
		timer.setLastMS();
		super.onClientTick(event);
	}

	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		RenderUtils.drawXRayBlocks(blocks, event.getPartialTicks());
		super.onRenderWorldLast(event);
	}
}
