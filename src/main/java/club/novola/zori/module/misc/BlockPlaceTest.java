package club.novola.zori.module.misc;

import club.novola.zori.command.Command;
import club.novola.zori.module.Module;
import club.novola.zori.util.BlockUtil;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockPlaceTest extends Module {

    public BlockPlaceTest(){
        super("BlockPlaceTest", Category.MISC);
    }

    public void onEnable(){
        BlockPos pos = (Wrapper.mc.player.getPosition().add(0, 0, 1));
        try {
            BlockUtil.placeBlock(pos, EnumFacing.DOWN, false);
        }catch(Exception e){
            Command.sendErrorMessage("Frick it didnt work.", true);
        }
        disable();
    }
}
