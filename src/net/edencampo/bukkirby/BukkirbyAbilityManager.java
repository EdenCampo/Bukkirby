package net.edencampo.bukkirby;

import org.bukkit.entity.Player;

public class BukkirbyAbilityManager
{
	Bukkirby plugin;
	
	public BukkirbyAbilityManager(Bukkirby instance)
	{
		plugin = instance;
	}
	
	
	public String getCurrentAbility(Player p)
	{
		String Ability = plugin.currentplayerability.get(p);
		
		return Ability;
	}
	
	public void setPlayerAbility(Player p, String Ability)
	{
		plugin.currentplayerability.put(p, Ability);
	}
}
