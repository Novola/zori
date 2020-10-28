package club.novola.zori.module.combat;

import club.novola.zori.setting.Setting;
import club.novola.zori.Zori;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Rewrite
// modified (old)kami scaffold
// original surround goes all the way back to apollyon, since then I've changed it quite a bit tho
// also it's a fuckin mess but I never got around to rewriting it
public class instantFeetPlace extends Module {
    public instantFeetPlace() {
        super("instantFeetPlace", Category.COMBAT);
    }

    private List<Block> whiteList = Arrays.asList(Blocks.OBSIDIAN, Blocks.ENDER_CHEST);
    private Setting<Boolean> sneak = register("SneakOnly", true);
    private Setting<Boolean> rotate = register("Rotate", true);
    private Setting<Integer> bpt = register("BlocksPerTick", 4, 1, 4);

    public String getHudInfo(){
        return sneak.getValue() ? "Sneak" : "";
    }

    public void onUpdate() {
        if(Wrapper.getPlayer() == null) return;
        if(sneak.getValue() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) return;
        Vec3d vec3d = getInterpolatedPos(Wrapper.mc.player, 0);
        BlockPos northBlockPos = new BlockPos(vec3d).north();
        BlockPos southBlockPos = new BlockPos(vec3d).south();
        BlockPos eastBlockPos = new BlockPos(vec3d).east();
        BlockPos westBlockPos = new BlockPos(vec3d).west();

        int blocksPlaced = 0;

        // //check if block is already placed
        // if(!Wrapper.getWorld().getBlockState(northBlockPos).getMaterial().isReplaceable() || !Wrapper.getWorld().getBlockState(southBlockPos).getMaterial().isReplaceable() || !Wrapper.getWorld().getBlockState(eastBlockPos).getMaterial().isReplaceable() || !Wrapper.getWorld().getBlockState(westBlockPos).getMaterial().isReplaceable())
        //     return;
        // search blocks in hotbar
        int newSlot = -1;
        for(int i = 0; i < 9; i++)
        {
            // filter out non-block items
            ItemStack stack = Wrapper.mc.player.inventory.getStackInSlot(i);

            if(stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) continue;
            // only use whitelisted blocks
            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (!whiteList.contains(block)) continue;

            newSlot = i;
            break;
        }

        // check if any blocks were found
        if(newSlot == -1)
            return;

        // set slot
        int oldSlot = Wrapper.mc.player.inventory.currentItem;
        Wrapper.mc.player.inventory.currentItem = newSlot;

        // check if we don't have a block adjacent to North blockpos
        A: if (!hasNeighbour(northBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = northBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    northBlockPos = neighbour;
                    break A;
                }
            }
            return;
        }

        // check if we don't have a block adjacent to South blockpos
        B: if (!hasNeighbour(southBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = southBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    southBlockPos = neighbour;
                    break B;
                }
            }
            return;
        }

        // check if we don't have a block adjacent to East blockpos
        C: if (!hasNeighbour(eastBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = eastBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    eastBlockPos = neighbour;
                    break C;
                }
            }
            return;
        }

        // check if we don't have a block adjacent to West blockpos
        D: if (!hasNeighbour(westBlockPos)) {
            // find air adjacent to blockpos that does have a block adjacent to it, let's fill this first as to form a bridge between the player and the original blockpos. necessary if the player is going diagonal.
            for (EnumFacing side : EnumFacing.values()) {
                BlockPos neighbour = westBlockPos.offset(side);
                if (hasNeighbour(neighbour)) {
                    westBlockPos = neighbour;
                    break D;
                }
            }
            return;
        }



        // place blocks
        if(Wrapper.mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()) {
            if(isEntitiesEmpty(northBlockPos)) {
                placeBlockScaffold(northBlockPos, rotate.getValue());
                blocksPlaced++;
            } else if(isEntitiesEmpty(northBlockPos.north()) && Wrapper.mc.world.getBlockState(northBlockPos).getMaterial().isReplaceable()){
                placeBlockScaffold(northBlockPos.north(), rotate.getValue());
                blocksPlaced++;
            }
        }
        if(blocksPlaced >= bpt.getValue()){ Wrapper.mc.player.inventory.currentItem = oldSlot; return; }

        if(Wrapper.mc.world.getBlockState(southBlockPos).getMaterial().isReplaceable()) {
            if(isEntitiesEmpty(southBlockPos)) {
                placeBlockScaffold(southBlockPos, rotate.getValue());
                blocksPlaced++;
            } else if(isEntitiesEmpty(southBlockPos.south()) && Wrapper.mc.world.getBlockState(southBlockPos.south()).getMaterial().isReplaceable()){
                placeBlockScaffold(southBlockPos.south(), rotate.getValue());
                blocksPlaced++;
            }
        }
        if(blocksPlaced >= bpt.getValue()){ Wrapper.mc.player.inventory.currentItem = oldSlot; return; }

        if(Wrapper.mc.world.getBlockState(eastBlockPos).getMaterial().isReplaceable()) {
            if(isEntitiesEmpty(eastBlockPos)) {
                placeBlockScaffold(eastBlockPos, rotate.getValue());
                blocksPlaced++;
            } else if(isEntitiesEmpty(eastBlockPos.east()) && Wrapper.mc.world.getBlockState(eastBlockPos.east()).getMaterial().isReplaceable()){
                placeBlockScaffold(eastBlockPos.east(), rotate.getValue());
                blocksPlaced++;
            }
        }
        if(blocksPlaced >= bpt.getValue()){ Wrapper.mc.player.inventory.currentItem = oldSlot; return; }

        if(Wrapper.mc.world.getBlockState(westBlockPos).getMaterial().isReplaceable()) {
            if(isEntitiesEmpty(westBlockPos)) {
                placeBlockScaffold(westBlockPos, rotate.getValue());
                //blocksPlaced++;
            } else if(isEntitiesEmpty(westBlockPos.west()) && Wrapper.mc.world.getBlockState(westBlockPos.west()).getMaterial().isReplaceable()){
                placeBlockScaffold(westBlockPos.west(), rotate.getValue());
                //blocksPlaced++;
            }
        }

        // reset slot
        Wrapper.mc.player.inventory.currentItem = oldSlot;
    }

    public static boolean hasNeighbour(BlockPos blockPos) {
        for (EnumFacing side : EnumFacing.values()) {
            BlockPos neighbour = blockPos.offset(side);
            if(!Wrapper.mc.world.getBlockState(neighbour).getMaterial().isReplaceable())
                return true;
        }
        return false;
    }

    private boolean isEntitiesEmpty(BlockPos pos){
        List<Entity> entities =  Wrapper.mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream()
                .filter(e -> !(e instanceof EntityItem))
                .filter(e -> !(e instanceof EntityXPOrb))
                .collect(Collectors.toList());
        return entities.isEmpty();
    }

    public static boolean placeBlockScaffold(BlockPos pos, boolean rotate) {
        //Vec3d eyesPos = new Vec3d(mc.player.posX,
        //        mc.player.posY + mc.player.getEyeHeight(),
        //        mc.player.posZ);

        for(EnumFacing side : EnumFacing.values())
        {
            BlockPos neighbor = pos.offset(side);
            EnumFacing side2 = side.getOpposite();

            // check if side is visible (facing away from player)
            //if(eyesPos.squareDistanceTo(
            //        new Vec3d(pos).add(0.5, 0.5, 0.5)) >= eyesPos
            //        .squareDistanceTo(
            //                new Vec3d(neighbor).add(0.5, 0.5, 0.5)))
            //    continue;

            // check if neighbor can be right clicked
            if(!canBeClicked(neighbor))
                continue;

            Vec3d hitVec = new Vec3d(neighbor).addVector(0.5, 0.5, 0.5)
                    .add(new Vec3d(side2.getDirectionVec()).scale(0.5));

            // check if hitVec is within range (4.25 blocks)
            //if(eyesPos.squareDistanceTo(hitVec) > 18.0625)
            //continue;

            // place block
            //if(rotate) faceVectorPacketInstant(hitVec);
            if(rotate) Zori.getInstance().rotationManager.rotate(hitVec.x, hitVec.y, hitVec.z);
            Wrapper.mc.player.connection.sendPacket(new CPacketEntityAction(Wrapper.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            //Wrapper.mc.rightClickDelayTimer = 0;
            processRightClickBlock(neighbor, side2, hitVec);
            //mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(neighbor, side2, EnumHand.MAIN_HAND, 0, 0, 0));
            Wrapper.mc.player.swingArm(EnumHand.MAIN_HAND);
            Wrapper.mc.player.connection.sendPacket(new CPacketEntityAction(Wrapper.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            if(rotate) Zori.getInstance().rotationManager.reset();

            return true;
        }

        return false;
    }

    private static PlayerControllerMP getPlayerController()
    {
        return Wrapper.mc.playerController;
    }

    private static void processRightClickBlock(BlockPos pos, EnumFacing side,
                                               Vec3d hitVec)
    {
        getPlayerController().processRightClickBlock(Wrapper.mc.player, Wrapper.mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
    }

    private static IBlockState getState(BlockPos pos)
    {
        return Wrapper.mc.world.getBlockState(pos);
    }

    private static Block getBlock(BlockPos pos)
    {
        return getState(pos).getBlock();
    }

    private static boolean canBeClicked(BlockPos pos)
    {
        return getBlock(pos).canCollideCheck(getState(pos), false);
    }

    public static void faceVectorPacketInstant(Vec3d vec)
    {
        float[] rotations = getNeededRotations2(vec);

        Wrapper.mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0],
                rotations[1], Wrapper.mc.player.onGround));
    }

    private static float[] getNeededRotations2(Vec3d vec)
    {
        Vec3d eyesPos = getEyesPos();

        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));

        return new float[]{
                Wrapper.mc.player.rotationYaw
                        + MathHelper.wrapDegrees(yaw - Wrapper.mc.player.rotationYaw),
                Wrapper.mc.player.rotationPitch + MathHelper
                        .wrapDegrees(pitch - Wrapper.mc.player.rotationPitch)};
    }

    private static Vec3d getEyesPos()
    {
        return new Vec3d(Wrapper.mc.player.posX,
                Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight(),
                Wrapper.mc.player.posZ);
    }

    public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(getInterpolatedAmount(entity, ticks));
    }

    private static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }

    private static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
        return new Vec3d(
                (entity.posX - entity.lastTickPosX) * x,
                (entity.posY - entity.lastTickPosY) * y,
                (entity.posZ - entity.lastTickPosZ) * z
        );
    }
}
