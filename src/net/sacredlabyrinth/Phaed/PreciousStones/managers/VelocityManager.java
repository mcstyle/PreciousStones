package net.sacredlabyrinth.Phaed.PreciousStones.managers;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.managers.SettingsManager.FieldSettings;
import net.sacredlabyrinth.Phaed.PreciousStones.vectors.Field;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;

/**
 *
 * @author phaed
 */
public class VelocityManager
{
    private PreciousStones plugin;
    private HashMap<String, Integer> fallDamageImmune = new HashMap<String, Integer>();

    /**
     *
     * @param plugin
     */
    public VelocityManager(PreciousStones plugin)
    {
        this.plugin = plugin;
    }

    /**
     *
     * @param player
     * @param field
     */
    public void launchPlayer(final Entity entity, final Field field)
    {
        Player p = null;
        Vehicle v = null;

        if (entity instanceof Player)
        {
            p = (Player) entity;
        }

        if (entity instanceof Vehicle)
        {
            v = (Vehicle) entity;
            Entity e = v.getPassenger();

            if (e instanceof Player)
            {
                p = (Player) e;
            }
        }

        if (p == null)
        {
            return;
        }

        final Player player = p;
        final Vehicle vehicle = v;

        if (plugin.pm.hasPermission(player, "preciousstones.benefit.launch"))
        {
            if (plugin.ffm.isAllowed(field, player.getName()))
            {
                FieldSettings fieldsettings = plugin.settings.getFieldSettings(field);

                if (fieldsettings == null)
                {
                    plugin.ffm.queueRelease(field);
                    return;
                }

                final float launchheight = field.getVelocity() > 0 ? field.getVelocity() : fieldsettings.launchHeight;
                double speed = 8;

                Vector loc = player.getLocation().toVector();
                Vector target = new Vector(field.getX(), field.getY(), field.getZ());

                final Vector velocity = target.clone().subtract(new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
                velocity.multiply(speed / velocity.length());
                velocity.setY(launchheight > 0 ? launchheight : (((player.getLocation().getPitch() * -1) + 90) / 35));

                if (fieldsettings.launch)
                {
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (vehicle != null)
                            {
                                vehicle.setVelocity(velocity);
                            }
                            else
                            {
                                player.setVelocity(velocity);
                            }
                            plugin.cm.showLaunch(player);
                            startFallImmunity(player);
                        }
                    }, 0L);
                }
            }
        }
    }

    /**
     *
     * @param player
     * @param field
     */
    public void shootPlayer(final Entity entity, Field field)
    {
        Player p = null;
        Vehicle v = null;

        if (entity instanceof Player)
        {
            p = (Player) entity;
        }

        if (entity instanceof Vehicle)
        {
            v = (Vehicle) entity;
            Entity e = v.getPassenger();

            if (e instanceof Player)
            {
                p = (Player) e;
            }
        }

        if (p == null)
        {
            return;
        }

        final Player player = p;
        final Vehicle vehicle = v;

        if (plugin.pm.hasPermission(player, "preciousstones.benefit.bounce"))
        {
            if (plugin.ffm.isAllowed(field, player.getName()))
            {
                FieldSettings fieldsettings = plugin.settings.getFieldSettings(field);

                if (fieldsettings == null)
                {
                    plugin.ffm.queueRelease(field);
                    return;
                }

                final float bounceHeight = field.getVelocity() > 0 ? field.getVelocity() : fieldsettings.cannonHeight;
                final float height = bounceHeight > 0 ? bounceHeight : (((player.getLocation().getPitch() * -1) + 90) / 35);

                if (fieldsettings.cannon)
                {
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (vehicle != null)
                            {
                                vehicle.setVelocity(new Vector(0, height, 0));
                            }
                            else
                            {
                                player.setVelocity(new Vector(0, height, 0));
                            }
                            plugin.cm.showCannon(player);
                            startFallImmunity(player);
                        }
                    }, 0L);
                }
            }
        }
    }

    /**
     *
     * @param player
     */
    public void startFallImmunity(final Player player)
    {
        if (fallDamageImmune.containsKey(player.getName()))
        {
            int current = fallDamageImmune.get(player.getName());

            plugin.getServer().getScheduler().cancelTask(current);
        }

        fallDamageImmune.put(player.getName(), startImmuneRemovalDelay(player));
    }

    /**
     *
     * @param player
     * @return
     */
    public boolean isFallDamageImmune(final Player player)
    {
        return fallDamageImmune.containsKey(player.getName());
    }

    /**
     *
     * @param player
     * @return
     */
    public int startImmuneRemovalDelay(final Player player)
    {
        final String name = player.getName();

        return plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            @Override
            public void run()
            {
                fallDamageImmune.remove(name);
            }
        }, 15 * 20L);
    }
}
