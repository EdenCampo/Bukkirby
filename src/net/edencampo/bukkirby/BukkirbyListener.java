package net.edencampo.bukkirby;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.edencampo.bukkirby.Bukkirby.PlayerAbility;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class BukkirbyListener implements Listener 
{
	Bukkirby plugin;
	
	public BukkirbyListener(Bukkirby instance)
	{
		plugin = instance;
	}

	List<String> ValidCreatures = new ArrayList<String>();
	
	
	public void addValidCreatures()
	{
		ValidCreatures.add("creeper");
		ValidCreatures.add("enderman");
		ValidCreatures.add("zombie");
		ValidCreatures.add("skeleton");
		ValidCreatures.add("spider");
		ValidCreatures.add("cavespider");
		ValidCreatures.add("irongolem");
		ValidCreatures.add("snowgolem");
		ValidCreatures.add("pigzombie");
		ValidCreatures.add("blaze");
		ValidCreatures.add("ghast");
	}
	
	@EventHandler
	public void onPlayerHitEntity(EntityDamageByEntityEvent e)
	{
		if (e.getDamager() instanceof Egg) 
		{
			Egg egg = (Egg) e.getDamager();
			Entity EntityDamaged = e.getEntity();
			LivingEntity shooter = egg.getShooter();
			Player p = (Player) shooter;
			
			String creature = EntityDamaged.getType().toString().toLowerCase().replace("_", "");
			
			if(ValidCreatures.contains(creature))
			{	
				if (shooter instanceof Player) 
				{
					p.sendMessage(plugin.KirbyTag + "You hit a " + creature + "! You have received his abilities!");
					plugin.BKirbyAB.setPlayerAbility(p, "ABILITY_" + creature.toUpperCase());
					p.sendMessage(plugin.KirbyTag + "DEBUG: " + plugin.BKirbyAB.getCurrentAbility(p));
				}
			}
			else
			{
				p.sendMessage(plugin.KirbyTag + "DEBUG: Invalid Entity! : " + creature);
			}
		}
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		Action action = e.getAction();
		
		if(action == Action.LEFT_CLICK_AIR)
		{
			String currentability = plugin.BKirbyAB.getCurrentAbility(p);
					
			if(currentability.equalsIgnoreCase("ABILITY_ENDERMAN"))
			{
				Location eyepos = p.getEyeLocation();
				p.teleport(eyepos);
			}
			else if(currentability.equalsIgnoreCase("ABILITY_SKELETON"))
			{	
				if(e.getItem() == null)
				{
					Arrow arrow = p.launchProjectile(Arrow.class);
					arrow.remove();
				}
			}
			else if(currentability.equalsIgnoreCase("ABILITY_BLAZE"))
			{
				if(e.getItem() == null)
				{
					p.launchProjectile(Fireball.class);
				}
			}
			else if(currentability.equalsIgnoreCase("ABILITY_GHAST"))
			{
				if(e.getItem() == null)
				{
					p.launchProjectile(Fireball.class);
				}
			}
			else if(currentability.equalsIgnoreCase("ABILITY_SNOWGOLEM"))
			{
				if(e.getItem() == null)
				{
					p.launchProjectile(Snowball.class);
				}
			}
		}
	}
	
	@EventHandler
	public void onHitPlayer(EntityDamageByEntityEvent e)
	{
		Entity damager = e.getDamager();
		Entity damaged = e.getEntity();
		
		if(damager instanceof Player)
		{
			Player damagerp = (Player)damager;
			
			String damagerability = plugin.BKirbyAB.getCurrentAbility(damagerp);
			
			if(damagerability.equalsIgnoreCase("ABILITY_ZOMBIE"))
			{
				Object damage = e.getDamage();
				
				String newndamage = damage.toString() + "2.5";
				
				double finaldamage = 0;
				try
				{
					finaldamage = Integer.parseInt(newndamage);
				}
				catch(NumberFormatException ex)
				{
					ex.printStackTrace();
				}
				
				e.setDamage(finaldamage);
			}
			else if(damagerability.equalsIgnoreCase("ABILITY_CAVESPIDER"))
			{
				Random PoisonRandom = new Random();
				
				int RandomNum = PoisonRandom.nextInt(10);
				
				if(RandomNum == 5)
				{
					if(damaged instanceof Player)
					{
						Player damagedp = (Player)damaged;
						
						damagedp.addPotionEffect(PotionEffectType.HUNGER.createEffect(10, 2));
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		Entity entity = e.getEntity();
		
		if(entity instanceof Player)
		{
			Player p = (Player)entity;
			
			String currentability = plugin.BKirbyAB.getCurrentAbility(p);
			
			if(currentability.equalsIgnoreCase("ABILITY_CREEPER"))
			{
				Location loc = p.getLocation();
				
				p.getWorld().createExplosion(loc, 10L);
			}
			
			plugin.BKirbyAB.setPlayerAbility(p, PlayerAbility.ABILITY_NONE.toString());
		}
	}
	
	/*
	 * 	public enum PlayerAbility - TODO LIST
	{
		ABILITY_ZOMBIE(WORKS?),
		ABILITY_SPIDER,
		ABILITY_CAVESPIDER(WORKS?),
		ABILITY_IRONGOLEM,
		ABILITY_PIGZOMBIE,
	}
	 */
}
