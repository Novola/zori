package club.novola.zori.module.combat;

import club.novola.zori.Zori;
import club.novola.zori.command.Command;
import club.novola.zori.setting.Setting;
import club.novola.zori.module.Module;
import club.novola.zori.util.EntityUtils;
import club.novola.zori.util.Wrapper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// im not adding comments to this mess
public class AutoTrap extends Module {
    public AutoTrap() {
        super("AutoTrap", Category.COMBAT);
    }

    private Setting<Double> range = register("Range", 5.0d, 0.0d, 7.0d); // if you used 5.0, 0.0, 7.0 it would assume those are float numbers and throw an exception
    private Setting<Integer> bpt = register("BlocksPerTick", 4, 0, 13);
    private Setting<Boolean> rotateS = register("Rotate", true);

    private int blocksPlaced = 0;
    private boolean switchDelay = false;
    private List<EntityPlayer> list;
    private int oldSlot = -1;

    public void onUpdate(){
        if(Wrapper.getPlayer() == null || Wrapper.getWorld() == null || Wrapper.getWorld().playerEntities.isEmpty()) return;
        if(!switchDelay) {
            list = new ArrayList<>();

            for (EntityPlayer player : Wrapper.getWorld().playerEntities) {
                if (player == Wrapper.getPlayer()) continue;
                if (Wrapper.getPlayer().getDistance(player) > range.getValue()) continue;
                if (player.getHealth() <= 0 || player.isDead) continue;
                if(Zori.getInstance().playerStatus.getStatus(player.getName()) == 1) continue;
                list.add(player);
            }

            if (list.isEmpty()) return;
            list.sort(Comparator.comparing(p -> Zori.getInstance().playerStatus.isEnemyInRange(range.getValue()) ? Zori.getInstance().playerStatus.getStatus(p.getName()) : Wrapper.getPlayer().getDistance(p)));

            boolean mainHand = obbyOrEchest(Wrapper.getPlayer().getHeldItemMainhand());

            if (!mainHand) {
                oldSlot = Wrapper.getPlayer().inventory.currentItem;
                for (int i = 0; i < 9; i++) {
                    if (Wrapper.getPlayer().inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                        ItemBlock item = ((ItemBlock) Wrapper.getPlayer().inventory.getStackInSlot(i).getItem());
                        if (item.getBlock() instanceof BlockObsidian || item.getBlock() instanceof BlockEnderChest) {
                            Wrapper.getPlayer().inventory.currentItem = i;
                            switchDelay = true;
                            return;
                        }
                    }
                }

                mainHand = obbyOrEchest(Wrapper.getPlayer().getHeldItemMainhand());
            }

            if(!mainHand) return;
        }

        switchDelay = false;

        for(EntityPlayer player : list) {
            BlockPos playerPos = new BlockPos(EntityUtils.INSTANCE.getInterpolatedPos(player, 0));
            BlockPos[] offsets = new BlockPos[]{
                    playerPos.add(1, 0, 0),
                    playerPos.add(-1, 0, 0),
                    playerPos.add(0, 0, 1),
                    playerPos.add(0, 0, -1),
                    playerPos.add(0, 2, 0),
                    playerPos.add(1, 1, 0),
                    playerPos.add(-1, 1, 0),
                    playerPos.add(0, 1, 1),
                    playerPos.add(0, 1, -1),
                    playerPos.add(1, 2, 0),
                    playerPos.add(-1, 2, 0),
                    playerPos.add(0, 2, 1),
                    playerPos.add(0, 2, -1)
            };


            blocksPlaced = 0;
            for (BlockPos blockPos : offsets) {
                if (blocksPlaced > bpt.getValue()){
                    if(Wrapper.getPlayer().inventory.currentItem != oldSlot)
                        Wrapper.getPlayer().inventory.currentItem = oldSlot;
                    return;
                }
                if (shouldPlace(blockPos)) {
                    Surround.placeBlockScaffold(blockPos, rotateS.getValue());
                    blocksPlaced++;
                }
            }
        }

        if(Wrapper.getPlayer().inventory.currentItem != oldSlot && oldSlot != -1) {
            Wrapper.getPlayer().inventory.currentItem = oldSlot;
        }
        oldSlot = -1;
    }

    private boolean obbyOrEchest(ItemStack stack){
        if(stack.getItem() instanceof ItemBlock) {
            ItemBlock item = ((ItemBlock) stack.getItem());
            return item.getBlock() instanceof BlockObsidian || item.getBlock() instanceof BlockEnderChest;
        }
        return false;
    }

    private boolean shouldPlace(BlockPos pos) {
        List<Entity> entities =  Wrapper.getWorld().getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream()
                .filter( e -> !(e instanceof EntityItem))
                .filter( e -> !(e instanceof EntityXPOrb))
                .collect(Collectors.toList());

        boolean a = entities.isEmpty();
        boolean b = Wrapper.getWorld().getBlockState(pos).getMaterial().isReplaceable();
        boolean c = blocksPlaced < bpt.getValue();
        return a && b && c;
    }
}
