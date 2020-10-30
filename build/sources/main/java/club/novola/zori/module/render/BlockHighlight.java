package club.novola.zori.module.render;

import club.novola.zori.setting.Setting;
import club.novola.zori.Zori;
import club.novola.zori.module.Module;
import club.novola.zori.util.RenderUtils;
import club.novola.zori.util.Wrapper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class BlockHighlight extends Module {
    public BlockHighlight() {
        super("BlockHighlight", Category.RENDER);
    }

    private Setting<Integer> red = register("Red", 10, 0, 255);
    private Setting<Integer> green = register("Green", 100, 0, 255);
    private Setting<Integer> blue = register("Blue", 200, 0, 255);
    private Setting<Boolean> sync = register("Sync", false);
    private Setting<Integer> width = register("Width", 1, 1, 10);

    public void onRender3D(){
        if(Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.mc.objectMouseOver == null || Wrapper.mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) return;
        BlockPos blockpos = Wrapper.mc.objectMouseOver.getBlockPos();
        IBlockState iblockstate = Wrapper.getWorld().getBlockState(blockpos);

        if (iblockstate.getMaterial() != Material.AIR && sync.getValue()) {
            RenderUtils.INSTANCE.drawBoundingBox(iblockstate.getSelectedBoundingBox(Wrapper.getWorld(), blockpos), Zori.getInstance().clientSettings.getColor(), width.getValue().floatValue());
        }else{
            RenderUtils.INSTANCE.drawBoundingBox(iblockstate.getSelectedBoundingBox(Wrapper.getWorld(), blockpos), red.getValue() / 255f, green.getValue() / 255f, blue.getValue() / 255f, 0.50f, width.getValue());
        }
    }
}
