package club.novola.zori.module.player;

import club.novola.zori.module.Module;
import club.novola.zori.setting.Setting;
import club.novola.zori.util.Wrapper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class NoVoid extends Module {

    private Setting<Integer> height = register("Height", 3, 0, 256);

    public NoVoid() {
        super("NoVoid", Category.PLAYER);
    }

    public void onUpdate() {
        if (Wrapper.mc.world != null) {
            if (Wrapper.mc.player.noClip || Wrapper.mc.player.posY > height.getValue()) {
                return;
            }
            final RayTraceResult trace = Wrapper.mc.world.rayTraceBlocks(Wrapper.mc.player.getPositionVector(), new Vec3d(Wrapper.mc.player.posX, 0.0, Wrapper.mc.player.posZ), false, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return;
            }
            Wrapper.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
    }
}
