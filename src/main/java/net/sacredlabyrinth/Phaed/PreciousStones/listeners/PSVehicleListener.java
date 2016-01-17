package net.sacredlabyrinth.Phaed.PreciousStones.listeners;

import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.field.Field;
import net.sacredlabyrinth.Phaed.PreciousStones.field.FieldFlag;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

/**
 * @author phaed
 */
public class PSVehicleListener implements Listener {
    private final PreciousStones plugin;

    /**
     *
     */
    public PSVehicleListener() {
        plugin = PreciousStones.getInstance();
    }

    /**
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleDestroy(VehicleDestroyEvent event) {
        Vehicle vehicle = event.getVehicle();

        Field field = plugin.getForceFieldManager().getEnabledSourceField(vehicle.getLocation(), FieldFlag.PREVENT_VEHICLE_DESTROY);

        if (field != null) {
            if (event.getAttacker() instanceof Player) {
                Player player = (Player) event.getAttacker();

                if (FieldFlag.PREVENT_VEHICLE_DESTROY.applies(field, player)) {
                    if (plugin.getPermissionsManager().has(player, "preciousstones.bypass.destroy")) {
                        plugin.getCommunicationManager().notifyBypassDestroyVehicle(player, vehicle, field);
                    } else {
                        event.setCancelled(true);
                        plugin.getCommunicationManager().warnDestroyVehicle(player, vehicle, field);
                        return;
                    }
                }
            }
        }
    }

    /**
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleMove(VehicleMoveEvent event) {
        Vehicle v = event.getVehicle();
        Entity entity = v.getPassenger();

        if (plugin.getSettingsManager().isBlacklistedWorld(v.getLocation().getWorld())) {
            return;
        }

        if (!(entity instanceof Player)) {
            return;
        }

        plugin.getPlayerListener().onPlayerMove(new PlayerMoveEvent((Player) entity, event.getFrom(), event.getTo()));
    }

    /**
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleEnter(VehicleEnterEvent event) {
        Vehicle vehicle = event.getVehicle();

        Entity passenger = vehicle.getPassenger();

        if (passenger instanceof Player) {
            Player player = (Player) passenger;

            Field field = plugin.getForceFieldManager().getEnabledSourceField(player.getLocation(), FieldFlag.PREVENT_VEHICLE_ENTER);

            if (field != null) {
                if (FieldFlag.PREVENT_VEHICLE_ENTER.applies(field, player)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * @param event
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleExit(VehicleExitEvent event) {
        Vehicle vehicle = event.getVehicle();

        Entity passenger = vehicle.getPassenger();

        if (passenger instanceof Player) {
            Player player = (Player) passenger;

            Field field = plugin.getForceFieldManager().getEnabledSourceField(player.getLocation(), FieldFlag.PREVENT_VEHICLE_EXIT);

            if (field != null) {
                if (FieldFlag.PREVENT_VEHICLE_EXIT.applies(field, player)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
