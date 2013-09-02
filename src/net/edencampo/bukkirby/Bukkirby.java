package net.edencampo.bukkirby;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Bukkirby extends JavaPlugin
{
	/*
	    Creeper: Explode, leaving you with less health*
		Enderman: Teleport to where you were looking, with no damage
		Zombie: Deal More Damage* but lose hunger*
		Skeleton: Shoot an Arrow. No arrow items required
		Spider: Allow you to climb a wall
		Cave Spider: A chance* giving enemies poison when you attack them
		Iron Golem: Gives you a chance* of throwing enemies into the air when you attack them
		Snow Golem: Throw a snowball. No snowball item required
		PigZombie: Other Pigzombies near you will attack enemies
		Blaze: Shoot Fireballs
		Ghast: Shoot Bombs
	 */

	public enum PlayerAbility
	{
		ABILITY_NONE,
		ABILITY_CREEPER,
		ABILITY_ENDERMAN,
		ABILITY_ZOMBIE,
		ABILITY_SKELETON,
		ABILITY_SPIDER,
		ABILITY_CAVESPIDER,
		ABILITY_IRONGOLEM,
		ABILITY_SNOWGOLEM,
		ABILITY_PIGZOMBIE,
		ABILITY_BLAZE,
		ABILITY_GHAST
	}
	
	public HashMap<Player, String> currentplayerability = new HashMap<Player, String>();
	
	BukkirbyLogger BKirbyLog = new BukkirbyLogger(this);
	BukkirbyListener BKirbyListener = new BukkirbyListener(this);
	BukkirbyAbilityManager BKirbyAB = new BukkirbyAbilityManager(this);
	
	public String KirbyTag = ChatColor.BLACK + "[" + ChatColor.GREEN + "Bukkirby" + ChatColor.BLACK + "]" + " " + ChatColor.WHITE;
	
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(BKirbyListener, this);
		
		for(Player p : Bukkit.getServer().getOnlinePlayers())
		{
			BKirbyAB.setPlayerAbility(p, PlayerAbility.ABILITY_NONE.toString());
		}
		
		BKirbyListener.addValidCreatures();
		
		BKirbyLog.logInfo("Successfully enabled!");
	}
	
	public void onDisable()
	{
		BKirbyListener.ValidCreatures.clear();
		
		BKirbyLog.logInfo("Successfully disabled!");
	}
}