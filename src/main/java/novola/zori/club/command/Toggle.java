package novola.zori.club.command;

import novola.zori.club.managers.HackManager;
import novola.zori.club.utils.LoginUtils;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import novola.zori.club.managers.HackManager;
import novola.zori.club.utils.visual.ChatUtils;

public class Toggle extends Command
{
	public Toggle()
	{
		super("toggle");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			HackManager.getHack(args[0]).toggle();
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Toggling selected hack.";
	}

	@Override
	public String getSyntax()
	{
		return "toggle <hackname>";
	}
}