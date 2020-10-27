package club.novola.zori.module.misc;

import club.novola.zori.Zori;
import club.novola.zori.module.render.HoleESP;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.math.BlockPos;

public class HoleStep extends Module {
    public HoleStep() {
        super("HoleStep", Category.MISC);
    }

    public void onUpdate(){
        if(Wrapper.getPlayer() == null || Wrapper.getWorld() == null) return;
        if(isHole(getPlayerPos().down()) && Wrapper.getPlayer().motionY <= 0 && Wrapper.getPlayer().fallDistance <= 1 && !Wrapper.mc.gameSettings.keyBindJump.isKeyDown()){
            Wrapper.getPlayer().addVelocity(0, -3, 0);
        }
    }

    private boolean isHole(BlockPos blockPos){
        HoleESP holeESP = (HoleESP) Zori.getInstance().moduleManager.getModuleByName("HoleESP");
        return holeESP.isBedrock(blockPos) || holeESP.isObby(blockPos);
    }

    private BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Wrapper.getPlayer().posX), Math.floor(Wrapper.getPlayer().posY), Math.floor(Wrapper.getPlayer().posZ));
    }
}
