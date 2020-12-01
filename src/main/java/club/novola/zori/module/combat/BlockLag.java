package club.novola.zori.module.combat;

import java.util.ArrayList;

import club.novola.zori.event.PacketSendEvent;
import club.novola.zori.module.Module;
import club.novola.zori.managers.ModuleManager;
import club.novola.zori.util.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * @author Novola
 * half the code is //'d out coz i need scaffold for it to be like completely auto and brr
 */

public class BlockLag extends Module {
    public BlockLag() {
        super("BlockLag", Category.COMBAT);
    }

    @Override
    public void onEnable(){
        //Scaffold scaffold = (Scaffold) Zori.getInstance().moduleManager.getModuleByName("Scaffold");
        //scaffold.enable();
        Wrapper.getPlayer().jump();
        --Wrapper.getPlayer().motionY;
    }

    //@Override
    //public void onDisable(){
        //Scaffold scaffold = (Scaffold) Zori.getInstance().moduleManager.getModuleByName("Scaffold");
        //if (scaffold.isEnabled()) scaffold.disable();
    }
//}
