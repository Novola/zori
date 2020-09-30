package novola.zori.club.hack.hacks;

import java.util.ArrayList;

import novola.zori.club.hack.Hack;
import novola.zori.club.hack.HackCategory;
import novola.zori.club.managers.HackManager;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.system.Connection.Side;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.utils.visual.RenderUtils;
import novola.zori.club.value.BooleanValue;
import novola.zori.club.value.Mode;
import novola.zori.club.value.ModeValue;
import novola.zori.club.wrappers.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import novola.zori.club.hack.HackCategory;

public class PacketFilter extends Hack{
	
	public ModeValue mode;
	
	public BooleanValue cCPacketPlayer;
	public BooleanValue cCPacketCloseWindow;
	public BooleanValue cCPacketRotation;
	public BooleanValue cCPacketPosition;
	public BooleanValue cCPacketPositionRotation;
	public BooleanValue cCPacketClientStatus;
	public BooleanValue cCPacketInput;
	public BooleanValue cCPacketPlayerAbilities;
	public BooleanValue cCPacketPlayerDigging;
	public BooleanValue cCPacketUseEntity;
	public BooleanValue cCPacketVehicleMove;
	public BooleanValue cCPacketEntityAction;
	public BooleanValue cCPacketClickWindow;
	
	public PacketFilter() {
		super("PacketFilter", HackCategory.ANOTHER);
		
		this.mode = new ModeValue("Mode", new Mode("Output", true), new Mode("Input", false), new Mode("AllSides", false));
		
		cCPacketPlayer = new BooleanValue("Player", false);
		cCPacketEntityAction = new BooleanValue("EntityAction", false);
		cCPacketCloseWindow = new BooleanValue("CloseWindow", false);
		cCPacketRotation = new BooleanValue("Rotation", false);
		cCPacketPosition = new BooleanValue("Position", false);
		cCPacketPositionRotation = new BooleanValue("PositionRotation", false);
		cCPacketClientStatus = new BooleanValue("ClientStatus", false);
		cCPacketInput = new BooleanValue("Input", false);
		cCPacketPlayerAbilities = new BooleanValue("PlayerAbilities", false);
		cCPacketPlayerDigging = new BooleanValue("PlayerDigging", false);
		cCPacketUseEntity = new BooleanValue("UseEntity", false);
		cCPacketVehicleMove = new BooleanValue("VehicleMove", false);
		cCPacketEntityAction = new BooleanValue("EntityAction", false);
		cCPacketClickWindow = new BooleanValue("ClickWindow", false);
		
		this.addValue(
				this.mode,
				cCPacketPlayer,
				cCPacketEntityAction,
				cCPacketCloseWindow,
				cCPacketRotation,
				cCPacketPosition,
				cCPacketPositionRotation,
				cCPacketClientStatus,
				cCPacketInput,
				cCPacketPlayerAbilities,
				cCPacketPlayerDigging,
				cCPacketUseEntity,
				cCPacketVehicleMove,
				cCPacketEntityAction,
				cCPacketClickWindow
				);
	}
	
	@Override
	public String getDescription() {
		return "Packet filter.";
	}
	
	@Override
	public boolean onPacket(Object packet, Side side) {
		if((this.mode.getMode("Output").isToggled() && side == Side.OUT) 
				|| (this.mode.getMode("Input").isToggled() && side == Side.IN)
				|| (this.mode.getMode("AllSides").isToggled()))
			return checkPacket(packet);
		return true;
	}
	
	public boolean checkPacket(Object packet) {
		if((cCPacketPlayer.getValue() &&  packet instanceof CPacketPlayer)
				|| (cCPacketEntityAction.getValue() &&  packet instanceof CPacketEntityAction)
				|| (cCPacketCloseWindow.getValue() &&  packet instanceof CPacketCloseWindow)
				|| (cCPacketRotation.getValue() &&  packet instanceof CPacketPlayer.Rotation)
				|| (cCPacketPosition.getValue() &&  packet instanceof CPacketPlayer.Position)
				|| (cCPacketPositionRotation.getValue() &&  packet instanceof CPacketPlayer.PositionRotation)
				|| (cCPacketClientStatus.getValue() &&  packet instanceof CPacketClientStatus)
				|| (cCPacketInput.getValue() &&  packet instanceof CPacketInput)
				|| (cCPacketPlayerAbilities.getValue() &&  packet instanceof CPacketPlayerAbilities)
				|| (cCPacketPlayerDigging.getValue() &&  packet instanceof CPacketPlayerDigging)
				|| (cCPacketUseEntity.getValue() &&  packet instanceof CPacketUseEntity)
				|| (cCPacketVehicleMove.getValue() &&  packet instanceof CPacketVehicleMove)
				|| (cCPacketEntityAction.getValue() &&  packet instanceof CPacketEntityAction)
				|| (cCPacketClickWindow.getValue() &&  packet instanceof CPacketClickWindow))
			return false;
		return true;
	}
}
