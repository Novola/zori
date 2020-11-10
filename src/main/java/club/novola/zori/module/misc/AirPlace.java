package club.novola.zori.module.misc;

import club.novola.zori.command.Command;
import club.novola.zori.module.Module;
import club.novola.zori.util.BlockUtil;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class AirPlace extends Module {

    public AirPlace(){
        super("AirPlace (WIP)", Category.MISC);
    }

    public void onEnable(){
        BlockPos pos = (Wrapper.mc.player.getPosition().add(0, 1, 1));
        try {
            BlockUtil.placeBlock(pos, EnumFacing.UP, false);
        }catch(Exception e){
            Command.sendErrorMessage("Frick it didnt work.", true);
        }
        disable();
    }
}
