package club.novola.zori.util;

import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockUtil {
    public static void placeBlock(BlockPos pos, EnumFacing side, boolean packet) {
        BlockPos neighbour = pos.offset(side);
        EnumFacing opposite = side.getOpposite();
        if (!Wrapper.mc.player.isSneaking()) {
            Wrapper.mc.player.connection.sendPacket(new CPacketEntityAction(Wrapper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
        }
        Vec3d hitVec = (new Vec3d(neighbour)).addVector(0.5D, 0.5D, 0.5D).add((new Vec3d(opposite.getDirectionVec())).scale(0.5D));
        if(packet)
            Wrapper.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, side, EnumHand.MAIN_HAND, (float)hitVec.x-pos.getX(), (float)hitVec.y-pos.getY(), (float)hitVec.z-pos.getZ()));
        else
            Wrapper.mc.playerController.processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, neighbour, opposite, hitVec, EnumHand.MAIN_HAND);
        Wrapper.mc.player.swingArm(EnumHand.MAIN_HAND);
    }
}
