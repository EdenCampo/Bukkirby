package net.edencampo.bukkirby;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.edencampo.bukkirby.Bukkirby.PlayerAbility;
import net.minecraft.server.v1_6_R2.Item;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
		String AllowedMobs = plugin.getConfig().getString("AllowedMobs");
		
		String[] AllowedMobsSplit = AllowedMobs.split(", ");
		
		int id = 0;
		while(id < AllowedMobsSplit.length)
		{
			ValidCreatures.add(AllowedMobsSplit[id]);
			
			plugin.BKirbyLog.logInfo("Added valid mob: " + AllowedMobsSplit[id]);
			
			id++;
		}
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
					
					p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);
					p.getWorld().playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 0);
					p.getWorld().strikeLightning(p.getLocation().add(50.00D, 25.00D, 50.00D));
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
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
				if(p.getInventory().contains(Item.ENDER_PEARL.id) && p.isOnGround())
				{
					Block blockloc = p.getTargetBlock(null, 15);
					p.teleport(blockloc.getLocation());	
					p.getWorld().playEffect(p.getLocation(), Effect.SMOKE, 0);
				}
			}
			else if(currentability.equalsIgnoreCase("ABILITY_SKELETON"))
			{	
				if(e.getItem() == null)
				{
					p.launchProjectile(Arrow.class);
					p.getWorld().playEffect(p.getLocation(), Effect.BOW_FIRE, 0);
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
				double damage = e.getDamage();
				
				double newndamage = damage + 2.5;
				
				e.setDamage(newndamage);
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
			else if(damagerability.equalsIgnoreCase("ABILITY_IRONGOLEM"))
			{
				if(damaged instanceof Player)
				{
					/**
					 * Author of snippet: MrSugarCaney
					 */
					
					Player damagedp = (Player)damaged;
					
		            Location loc = damagedp.getLocation();
		            loc.add(0.5D, 0.5D, 0.5D);
		            boolean check = true;
		            while (check) 
		            {
		              if (loc.getBlock().getType() == Material.AIR) 
		              {
		                loc.add(0.0D, 1.0D, 0.0D);
		                if (loc.getBlock().getType() == Material.AIR) 
		                {
		                  loc.add(0.0D, -1.0D, 0.0D);

		                  for (int i = 1; i <= 8; i++) 
		                  {
		                    if (loc.add(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR) 
		                    {	
		                      check = true;
		                      break;
		                    }
		                    if (i == 8) 
		                    {
		                      damagedp.teleport(loc);
		                      check = false;
		                      break;
		                    }
		                  }
		                }
		                else 
		                {
		                  loc.add(0.0D, -1.0D, 0.0D);
		                }
		              }
		              loc.add(0.0D, 1.0D, 0.0D);
		            }
				}
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		
		Block blockfacing = p.getTargetBlock(null, 1);
		
		double byaxiS = blockfacing.getY();
		double pyaxiS = p.getLocation().getY();
		
		if(byaxiS > pyaxiS)
		{
			String currentability = plugin.BKirbyAB.getCurrentAbility(p);
			
			if(currentability.equalsIgnoreCase("ABILITY_SPIDER"))
			{
				if(blockfacing.getType() == Material.AIR)
				{	
					p.setFlying(false);
					
					return;
				}
				
				p.setFlying(true);
			}
		}
	}
	
	@EventHandler
	public void onArrowHit(ProjectileHitEvent e)
	{
		if(e.getEntity() instanceof Arrow)
		{	
			Arrow arrow = (Arrow) e.getEntity();
			
			Entity shooter = arrow.getShooter();
			
			if(shooter instanceof Player)
			{
				Player pshooter = (Player) arrow.getShooter();
				
				String currentability = plugin.BKirbyAB.getCurrentAbility(pshooter);
				
				if(currentability.equalsIgnoreCase("ABILITY_SKELETON"))
				{
					arrow.remove();
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		plugin.BKirbyAB.setPlayerAbility(p, PlayerAbility.ABILITY_NONE.toString());
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
		ABILITY_CAVESPIDER(WORKS?),
		ABILITY_PIGZOMBIE,
	}
	 */
}
