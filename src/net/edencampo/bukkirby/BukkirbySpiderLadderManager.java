package net.edencampo.bukkirby;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class BukkirbySpiderLadderManager implements Runnable
{
	Bukkirby plugin;
	
	public BukkirbySpiderLadderManager(Bukkirby instance)
	{
		plugin = instance;
	}
	
	@Override
	public void run() 
	{
		int block = 0;
		while(block < plugin.BKirbyListener.invisladders.size())
		{
			Block ladder = Bukkit.getServer().getWorld("world").getBlockAt(plugin.BKirbyListener.invisladders.get(block)); 
			
			ladder.breakNaturally();
			block++;
		}
		
	}

}
