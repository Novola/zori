package novola.zori.club.command;

import java.awt.Desktop;

import novola.zori.club.managers.FileManager;
import novola.zori.club.utils.LoginUtils;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import novola.zori.club.managers.FileManager;
import novola.zori.club.utils.visual.ChatUtils;

public class OpenDir extends Command
{
	public OpenDir()
	{
		super("opendir");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			Desktop.getDesktop().open(FileManager.GISHCODE_DIR);
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Opening directory of config.";
	}

	@Override
	public String getSyntax()
	{
		return "opendir";
	}
}