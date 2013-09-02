package net.edencampo.bukkirby;

import org.bukkit.plugin.PluginDescriptionFile;

public class BukkirbyLogger
{
	Bukkirby plugin;
	
	public BukkirbyLogger(Bukkirby instance)
	{
		plugin = instance;
	}
	
	public void logSevereError(String msg)
	{
		PluginDescriptionFile pdFile = plugin.getDescription();
		plugin.getLogger().severe(pdFile.getName() + " " + pdFile.getVersion() + ": " + msg);
	}
	
	public void logWarning(String msg)
	{
		PluginDescriptionFile pdFile = plugin.getDescription();
		plugin.getLogger().warning(pdFile.getName() + " " + pdFile.getVersion() + ": " + msg);
	}
	
	public void logInfo(String msg)
	{	
		PluginDescriptionFile pdFile = plugin.getDescription();
		plugin.getLogger().info(pdFile.getName() + " " + pdFile.getVersion() + ": " + msg);
	}
}
