package net.edencampo.bukkirby;

import net.edencampo.bukkirby.BukkirbyListener;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
		saveDefaultConfig();
		reloadConfig();
		
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) 
	{
		if(sender instanceof Player)
		{
			Player player = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("kirbyability") || cmd.getName().equalsIgnoreCase("ka"))
			{
				if(args.length != 1)
				{
					player.sendMessage(KirbyTag + "Usage: /kirbyability <ability>");
					return true;
				}
				
				String ability = args[0];
				
				player.sendMessage(KirbyTag + "Successfully set own ability to - ABILITY_" + ability.toUpperCase());
				BKirbyAB.setPlayerAbility(player, "ABILITY_" + ability.toUpperCase());
				
				player.getWorld().playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 0);
				player.getWorld().playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 0);
				player.getWorld().strikeLightning(player.getLocation().add(50.00D, 25.00D, 50.00D));
				return true;
			}
		}
		
		return false;
	}
}
