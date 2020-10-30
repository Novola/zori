package club.novola.zori.module.movement;

import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;

public class Step extends Module {
    public Step() {
        super("Step", Category.MOVEMENT);
    }

    Minecraft mc = Minecraft.getMinecraft();

    private Setting<Mode> mode = register("Mode", Mode.VANILLA);
	private Setting<Float> height = register("Height", 2f, 0.1f, 3f);

    private float oldStepHeight = -1f;

    @Override
    public void onEnable(){
        if(Wrapper.getPlayer() != null && mode.getValue().equals(Mode.VANILLA)){
            oldStepHeight = Wrapper.getPlayer().stepHeight;
            Wrapper.getPlayer().stepHeight = height.getValue();
        }
    }

    @Override
    public void onDisable(){
        if(mode.getValue().equals(Mode.VANILLA) && Wrapper.getPlayer() != null && oldStepHeight != -1f)
            Wrapper.getPlayer().stepHeight = oldStepHeight;
			oldStepHeight = -1f;
    }

    public void onUpdate(){
        if(mode.getValue().equals(Mode.BETTER) && Wrapper.getPlayer() != null) {
            if (!mc.player.collidedHorizontally) return;
            if (!mc.player.onGround || mc.player.isOnLadder() || mc.player.isInWater() || mc.player.isInLava() || mc.player.movementInput.jump || mc.player.noClip)
                return;
            if (mc.player.moveForward == 0 && mc.player.moveStrafing == 0) return;

            final double n = get_n_normal();

            if (n < 0 || n > 2) return;

            if (n == 2.0) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.42, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.78, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.63, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.51, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.9, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.21, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.45, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.43, mc.player.posZ, mc.player.onGround));
                mc.player.setPosition(mc.player.posX, mc.player.posY + 2.0, mc.player.posZ);
            }
            if (n == 1.5) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805212, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.00133597911214, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.16610926093821, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.24918707874468, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 1.1707870772188, mc.player.posZ, mc.player.onGround));
                mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ);
            }
            if (n == 1.0) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.41999998688698, mc.player.posZ, mc.player.onGround));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 0.7531999805212, mc.player.posZ, mc.player.onGround));
                mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ);
            }
        }
    }

    public double get_n_normal() {
        mc.player.stepHeight = 0.5f;

        double max_y = -1;

        final AxisAlignedBB grow = mc.player.getEntityBoundingBox().offset(0, 0.05, 0).grow(0.05);

        if (!mc.world.getCollisionBoxes(mc.player, grow.offset(0, 2, 0)).isEmpty()) return 100;

        for (final AxisAlignedBB aabb : mc.world.getCollisionBoxes(mc.player, grow)) {

            if (aabb.maxY > max_y) {
                max_y = aabb.maxY;
            }

        }

        return max_y - mc.player.posY;
    }

    public enum Mode{
        VANILLA, BETTER
    }
}
