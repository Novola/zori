package club.novola.zori.module.render;

import club.novola.zori.Zori;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.RenderUtils;
import club.novola.zori.util.Wrapper;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.HashMap;

public class HoleESP extends Module {
    public HoleESP() {
        super("HoleESP", Category.RENDER);
    }

    public int Red;
    public int Green;
    public int Blue;

    private HashMap<BlockPos, Boolean> holes = new HashMap<>();

    private Setting<Integer> range = register("Range", 10, 1, 20);
    private Setting<Integer> ored = register("ObbyRed", 255, 0, 255);
    private Setting<Integer> ogreen = register("ObbyGreen", 0, 0, 255);
    private Setting<Integer> oblue = register("ObbyBlue", 0, 0, 255);
    private Setting<Integer> bred = register("BedrockRed", 0, 0, 255);
    private Setting<Integer> bgreen = register("BedrockGreen", 255, 0, 255);
    private Setting<Integer> bblue = register("BedrockBlue", 0, 0, 255);
    private Setting<Integer> linewidth = register("LineWidth", 1, 1, 10);
    private Setting<Boolean> sync = register("Sync", false);

    @Override
    public void onUpdate() {
        if(Wrapper.getPlayer() == null || Wrapper.getWorld() == null) return;
        holes = new HashMap<>();
        findHoles();
    }

    @Override
    public void onRender3D() {
        if(!holes.isEmpty() && Wrapper.getPlayer() != null && Wrapper.getWorld() != null) {
            holes.forEach((blockPos, isBedrock) -> {
                if(sync.getValue()){
                    Color c = Zori.getInstance().clientSettings.getColorr(255);
                    RenderUtils.INSTANCE.drawBoundingBox(blockPos, c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.50f, linewidth.getValue());
                    RenderUtils.INSTANCE.drawBox(blockPos, c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, 0.22f);
                }else{
                    if(isBedrock){
                        Red = bred.getValue();
                        Green = bgreen.getValue();
                        Blue = bblue.getValue();
                    }else{
                        Red = ored.getValue();
                        Green = ogreen.getValue();
                        Blue = oblue.getValue();
                    }
                    RenderUtils.INSTANCE.drawBoundingBox(blockPos, Red / 255f, Green / 255f, Blue / 255f, 0.50f, linewidth.getValue());
                    RenderUtils.INSTANCE.drawBox(blockPos, Red / 255f, Green / 255f, Blue / 255f, 0.22f);
                }
            });
        }
    }

    public boolean isBedrock(BlockPos blockPos) {
        boolean air = Wrapper.getWorld().getBlockState(blockPos).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.up()).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.add(0, 2, 0)).getBlock() instanceof BlockAir;
        boolean down = Wrapper.getWorld().getBlockState(blockPos.down()).getBlock() == Blocks.BEDROCK;
        boolean north = Wrapper.getWorld().getBlockState(blockPos.north()).getBlock() == Blocks.BEDROCK;
        boolean south = Wrapper.getWorld().getBlockState(blockPos.south()).getBlock() == Blocks.BEDROCK;
        boolean west = Wrapper.getWorld().getBlockState(blockPos.west()).getBlock() == Blocks.BEDROCK;
        boolean east = Wrapper.getWorld().getBlockState(blockPos.east()).getBlock() == Blocks.BEDROCK;
        return air && down && north && south && west && east;
    }

    public boolean isObby(BlockPos blockPos) {
        boolean air = Wrapper.getWorld().getBlockState(blockPos).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.up()).getBlock() instanceof BlockAir && Wrapper.getWorld().getBlockState(blockPos.add(0, 2, 0)).getBlock() instanceof BlockAir;
        boolean down = Wrapper.getWorld().getBlockState(blockPos.down()).getBlock() == Blocks.BEDROCK || obbyOrEchest(blockPos.down());
        boolean north = Wrapper.getWorld().getBlockState(blockPos.north()).getBlock() == Blocks.BEDROCK || obbyOrEchest(blockPos.north());
        boolean south = Wrapper.getWorld().getBlockState(blockPos.south()).getBlock() == Blocks.BEDROCK || obbyOrEchest(blockPos.south());
        boolean west = Wrapper.getWorld().getBlockState(blockPos.west()).getBlock() == Blocks.BEDROCK || obbyOrEchest(blockPos.west());
        boolean east = Wrapper.getWorld().getBlockState(blockPos.east()).getBlock() == Blocks.BEDROCK || obbyOrEchest(blockPos.east());
        return air && down && north && south && west && east;
    }

    private boolean obbyOrEchest(BlockPos blockPos) {
        if(Wrapper.getWorld() == null) return false;
        return Wrapper.getWorld().getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || Wrapper.getWorld().getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST;
    }

    //credit: 086
    private void findHoles(){
        if(Wrapper.mc.getRenderViewEntity() == null) return;

        BlockPos loc = Wrapper.mc.getRenderViewEntity().getPosition();
        float r = range.getValue().floatValue();
        int plus_y = 0;

        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = cy - (int) r; y < cy + r; y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y);
                    if (dist < r * r) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        if(isBedrock(l)) holes.put(l, true);
                        else if(isObby(l)) holes.put(l, false);
                    }
                }
            }
        }
    }
}
