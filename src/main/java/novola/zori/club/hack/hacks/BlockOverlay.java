package novola.zori.club.hack.hacks;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.BlockUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.utils.BlockUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.wrappers.Wrapper;

public class BlockOverlay extends Hack{

	public BlockOverlay() {
		super("BlockOverlay", HackCategory.VISUAL);
	}
	
	@Override
    public String getDescription() {
        return "Show of selected block.";
    }
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		if(Wrapper.INSTANCE.mc().objectMouseOver == null) {
			return;
		}
		if (Wrapper.INSTANCE.mc().objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            Block block = BlockUtils.getBlock(Wrapper.INSTANCE.mc().objectMouseOver.getBlockPos());
            BlockPos blockPos = Wrapper.INSTANCE.mc().objectMouseOver.getBlockPos();

            if (Block.getIdFromBlock(block) == 0) {
                return;
            }
            RenderUtils.drawBlockESP(blockPos, 1F, 1F, 1F);
        }
		
		super.onRenderWorldLast(event);
	}

}
