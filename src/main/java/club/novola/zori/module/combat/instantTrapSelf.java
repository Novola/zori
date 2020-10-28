package club.novola.zori.module.combat;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.EntityUtils;
import club.novola.zori.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

// not finished
public class intantTrapSelf extends Module {
    public instantTrapSelf() {
        super("intantTrapSelf", Category.COMBAT);
    }

    private Setting<Boolean> full = register("Full", false);
    private Setting<Integer> bpt = register("BlocksPerTick", 4, 0, 13);
    private Setting<Boolean> rotateS = register("Rotate", true);

    private int blocksPlaced = 0;
    private boolean switchDelay = false;
    private int oldSlot = -1;

    @Override
    public void onUpdate(){
        if(Wrapper.getPlayer() == null || Wrapper.getWorld() == null || isSolid(new BlockPos(getPlayerPos()).up())) return;

        BlockPos[] offsets;

        if(full.getValue())
            offsets = new BlockPos[]{

            };
        else
            offsets = new BlockPos[]{
                    new BlockPos(getPlayerPos()).offset(Wrapper.getPlayer().getAdjustedHorizontalFacing().getOpposite()).down(),
                    new BlockPos(getPlayerPos()).offset(Wrapper.getPlayer().getAdjustedHorizontalFacing().getOpposite()),
                    new BlockPos(getPlayerPos()).offset(Wrapper.getPlayer().getAdjustedHorizontalFacing().getOpposite()).up(),
                    new BlockPos(getPlayerPos()).up()
            };

        blocksPlaced = 0;
        for(BlockPos blockPos : offsets){
            if(blocksPlaced >= bpt.getValue()){
                return;
            }
            if(canPlace(blockPos)){
                placeBlock(blockPos);
                blocksPlaced++;
            }
        }
    }

    private void placeBlock(BlockPos blockPos){
    }

    private boolean canPlace(BlockPos blockPos){
        List<Entity> entities = new ArrayList<>();
        for(Entity e : Wrapper.getWorld().getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos))){
            if(e instanceof EntityItem || e instanceof EntityXPOrb) continue;
            entities.add(e);
        }

        return !isSolid(blockPos) && entities.isEmpty();
    }

    private Vec3d getPlayerPos(){
        return EntityUtils.INSTANCE.getInterpolatedPos(Wrapper.getPlayer());
    }

    private boolean isSolid(BlockPos blockPos){
        return !Wrapper.getWorld().getBlockState(blockPos).getMaterial().isReplaceable();
    }
}
