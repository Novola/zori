package novola.zori.club.command;

import novola.zori.club.managers.EnemyManager;
import novola.zori.club.managers.FriendManager;
import novola.zori.club.utils.LoginUtils;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import novola.zori.club.managers.EnemyManager;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.visual.ChatUtils;

public class Enemy extends Command
{
	public Enemy()
	{
		super("enemy");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add")) {
				if(args[1].equalsIgnoreCase("all")) {
					for(Object object : Utils.getEntityList()) {
						if(object instanceof EntityPlayer) {
							EntityPlayer player = (EntityPlayer) object;
							if(!player.isInvisible()) {
								EnemyManager.addEnemy(Utils.getPlayerName(player));
							}
						}
					}
				} else {
					EnemyManager.addEnemy(args[1]);
				}
			}
			else
			if(args[0].equalsIgnoreCase("remove")) {
				EnemyManager.removeEnemy(args[1]);
			}
			else
			if(args[0].equalsIgnoreCase("clear")) {
				EnemyManager.clear();
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
		return "Enemy manager.";
	}

	@Override
	public String getSyntax()
	{
		return "enemy <add/remove/clear> <nick>";
	}
}