package novola.zori.club.command;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

import novola.zori.club.Main;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.visual.ChatUtils;
import novola.zori.club.wrappers.Wrapper;
import net.minecraft.client.network.NetworkPlayerInfo;
import novola.zori.club.utils.Utils;
import novola.zori.club.utils.visual.ChatUtils;

public class DumpClasses extends Command
{
	public DumpClasses()
	{
		super("dumpclasses");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			ArrayList<String> list = new ArrayList<String>();
			
			Field f = ClassLoader.class.getDeclaredField("classes");
			f.setAccessible(true);

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Vector<Class> classes =  (Vector<Class>) f.get(classLoader);
			
	        for (Class<?> clazz: classes) {
	        	String className = clazz.getName();
	        	if(args.length > 0) {
	        		if(className.contains(args[0]))
	        			list.add("\n" + className);
	        	} else { list.add("\n" + className); }
	        }
	        
	        if(list.isEmpty()) {
				ChatUtils.error("List is empty.");
			}
			else
			{
				Utils.copy(list.toString());
				ChatUtils.message("List copied to clipboard.");
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
		return "Get classes from ClassLoader by regex.";
	}

	@Override
	public String getSyntax()
	{
		return "dumpclasses <regex>";
	}
}