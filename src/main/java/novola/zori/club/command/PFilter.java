package novola.zori.club.command;

import novola.zori.club.managers.PickupFilterManager;
import org.lwjgl.input.Mouse;

import novola.zori.club.managers.PickupFilterManager;
import novola.zori.club.managers.XRayManager;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;
import novola.zori.club.xray.XRayData;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class PFilter extends Command
{
	public PFilter()
	{
		super("pfilter");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add")) {
				PickupFilterManager.addItem(Integer.parseInt(args[1]));
			}
			else
			if(args[0].equalsIgnoreCase("remove")) {
				PickupFilterManager.removeItem(Integer.parseInt(args[1]));
			}
			else
			if(args[0].equalsIgnoreCase("clear")) {
				PickupFilterManager.clear();
			}
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "PickupFilter manager.";
	}

	@Override
	public String getSyntax()
	{
		return "pfilter add <id> | remove <id> | clear";
	}
}