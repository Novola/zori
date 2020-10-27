package club.novola.zori.module.render;

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

    private HashMap<BlockPos, Boolean> holes = new HashMap<>();

    private Setting<Integer> range = register("Range", 10, 1, 20);
    private Setting<Color> bedrockColor = register("BedrockColor", new Color(0, 255, 0)); // these can be changed from the config, not implemented in the gui
    private Setting<Color> obbyColor = register("ObbyColor", new Color(255, 0, 0)); // these can be changed from the config, not implemented in the gui
    private Setting<Integer> alpha = register("Alpha", 27, 27, 255);

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
                Color c = isBedrock ? bedrockColor.getValue() : obbyColor.getValue();
                RenderUtils.INSTANCE.drawBox(blockPos, c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, alpha.getValue() / 255f);
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
