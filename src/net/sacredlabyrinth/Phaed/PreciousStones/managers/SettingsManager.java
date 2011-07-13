package net.sacredlabyrinth.Phaed.PreciousStones.managers;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.config.Configuration;

import net.sacredlabyrinth.Phaed.PreciousStones.Helper;
import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.vectors.*;

/**
 *
 * @author phaed
 */
public final class SettingsManager
{
    public int maxSnitchRecords;
    public int saveBatchSize;
    public int saveFrequency;
    public List<Integer> griefUndoBlackList;
    public int griefIntervalSeconds;
    public List<Integer> foresterFertileBlocks;
    public int foresterInterval;
    public int foresterTrees;
    public int visualizeAdminChunkRadius;
    public int visualizeMarkBlock;
    public int visualizeBlock;
    public int visualizeSeconds;
    public boolean visualizeEndOnMove;
    public boolean debug;
    public ArrayList<LinkedHashMap> forceFieldBlocks;
    public List<Integer> unbreakableBlocks;
    public List<Integer> bypassBlocks;
    public List<Integer> unprotectableBlocks;
    public List<Integer> toolItems;
    public boolean logFire;
    public boolean logEntry;
    public boolean logPlace;
    public boolean logUse;
    public boolean logEmpty;
    public boolean logDestroy;
    public boolean logDestroyArea;
    public boolean logUnprotectable;
    public boolean logPvp;
    public boolean logBypassPvp;
    public boolean logBypassDelete;
    public boolean logBypassPlace;
    public boolean logBypassDestroy;
    public boolean logBypassUnprotectable;
    public boolean logConflictPlace;
    public boolean notifyPlace;
    public boolean notifyDestroy;
    public boolean notifyBypassPvp;
    public boolean notifyBypassPlace;
    public boolean notifyBypassDestroy;
    public boolean notifyBypassUnprotectable;
    public boolean warnInstantHeal;
    public boolean warnSlowHeal;
    public boolean warnSlowDamage;
    public boolean warnFastDamage;
    public boolean warnGiveAir;
    public boolean warnPlace;
    public boolean warnUse;
    public boolean warnEmpty;
    public boolean warnDestroy;
    public boolean warnDestroyArea;
    public boolean warnUnprotectable;
    public boolean warnEntry;
    public boolean warnPvp;
    public boolean warnFire;
    public boolean warnLaunch;
    public boolean warnCannon;
    public boolean warnMine;
    public boolean publicBlockDetails;
    public boolean sneakingBypassesDamage;
    public boolean allowedCanBreakPstones;
    public boolean dropOnDelete;
    public boolean disableAlertsForAdmins;
    public boolean disableBypassAlertsForAdmins;
    public boolean offByDefault;
    public int chunksInLargestForceFieldArea;
    public List<Integer> ffBlocks = new ArrayList<Integer>();
    public int[] throughFields = new int[]
    {
        0, 6, 8, 9, 10, 11, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 69, 68, 70, 72, 75, 76, 77, 83, 92, 93, 94
    };
    public int linesPerPage;
    public boolean useMysql;
    public String host;
    public String database;
    public String username;
    public String password;
    private final HashMap<Integer, FieldSettings> fieldsettings = new HashMap<Integer, FieldSettings>();
    private PreciousStones plugin;

    /**
     *
     * @param plugin
     */
    public SettingsManager(PreciousStones plugin)
    {
        this.plugin = plugin;
        load();
    }

    /**
     * Load the configuration
     */
    @SuppressWarnings("unchecked")
    public void load()
    {
        Configuration config = plugin.getConfiguration();
        config.load();

        List<Integer> fblocks = new ArrayList<Integer>();
        fblocks.add(2);
        fblocks.add(3);
        fblocks.add(13);
        fblocks.add(87);

        List<Integer> blacklist = new ArrayList<Integer>();
        blacklist.add(92);

        forceFieldBlocks = (ArrayList) config.getProperty("force-field-blocks");
        unbreakableBlocks = config.getIntList("unbreakable-blocks", new ArrayList<Integer>());
        bypassBlocks = config.getIntList("bypass-blocks", new ArrayList<Integer>());
        unprotectableBlocks = config.getIntList("unprotectable-blocks", new ArrayList<Integer>());
        toolItems = config.getIntList("tool-items", new ArrayList<Integer>());
        logFire = config.getBoolean("log.fire", false);
        logEntry = config.getBoolean("log.entry", false);
        logPlace = config.getBoolean("log.place", false);
        logUse = config.getBoolean("log.use", false);
        logPvp = config.getBoolean("log.pvp", false);
        logDestroy = config.getBoolean("log.destroy", false);
        logDestroyArea = config.getBoolean("log.destroy-area", false);
        logUnprotectable = config.getBoolean("log.unprotectable", false);
        logBypassPvp = config.getBoolean("log.bypass-pvp", false);
        logBypassDelete = config.getBoolean("log.bypass-delete", false);
        logBypassPlace = config.getBoolean("log.bypass-place", false);
        logBypassDestroy = config.getBoolean("log.bypass-destroy", false);
        logConflictPlace = config.getBoolean("log.conflict-place", false);
        notifyPlace = config.getBoolean("notify.place", false);
        notifyDestroy = config.getBoolean("notify.destroy", false);
        notifyBypassUnprotectable = config.getBoolean("notify.bypass-unprotectable", false);
        notifyBypassPvp = config.getBoolean("notify.bypass-pvp", false);
        notifyBypassPlace = config.getBoolean("notify.bypass-place", false);
        notifyBypassDestroy = config.getBoolean("notify.bypass-destroy", false);
        warnInstantHeal = config.getBoolean("warn.instant-heal", false);
        warnSlowHeal = config.getBoolean("warn.slow-heal", false);
        warnSlowDamage = config.getBoolean("warn.slow-damage", false);
        warnFastDamage = config.getBoolean("warn.fast-damage", false);
        warnGiveAir = config.getBoolean("warn.give-air", false);
        warnFire = config.getBoolean("warn.fire", false);
        warnEntry = config.getBoolean("warn.entry", false);
        warnPlace = config.getBoolean("warn.place", false);
        warnUse = config.getBoolean("warn.use", false);
        warnPvp = config.getBoolean("warn.pvp", false);
        warnDestroy = config.getBoolean("warn.destroy", false);
        warnDestroyArea = config.getBoolean("warn.destroy-area", false);
        warnUnprotectable = config.getBoolean("warn.unprotectable", false);
        warnLaunch = config.getBoolean("warn.launch", false);
        warnCannon = config.getBoolean("warn.cannon", false);
        warnMine = config.getBoolean("warn.mine", false);
        publicBlockDetails = config.getBoolean("settings.public-block-details", false);
        sneakingBypassesDamage = config.getBoolean("settings.sneaking-bypasses-damage", false);
        allowedCanBreakPstones = config.getBoolean("settings.allowed-can-break-pstones", false);
        dropOnDelete = config.getBoolean("settings.drop-on-delete", false);
        disableAlertsForAdmins = config.getBoolean("settings.disable-alerts-for-admins", false);
        disableBypassAlertsForAdmins = config.getBoolean("settings.disable-bypass-alerts-for-admins", false);
        offByDefault = config.getBoolean("settings.off-by-default", false);
        linesPerPage = config.getInt("settings.lines-per-page", 12);
        saveFrequency = config.getInt("saving.frequency-seconds", 300);
        saveBatchSize = config.getInt("saving.batch-size", 100);
        maxSnitchRecords = config.getInt("saving.max-records-per-snitch", 50);
        visualizeBlock = config.getInt("visualization.block-type", 20);
        visualizeMarkBlock = config.getInt("visualization.mark-block-type", 49);
        visualizeSeconds = config.getInt("visualization.seconds", 30);
        visualizeEndOnMove = config.getBoolean("visualization.end-on-player-move", true);
        visualizeAdminChunkRadius = config.getInt("visualization.admin-chunk-radius", 15);
        foresterInterval = config.getInt("forester.interval-seconds", 1);
        foresterFertileBlocks = config.getIntList("forester.fertile-blocks", fblocks);
        foresterTrees = config.getInt("forester.trees", 60);
        griefIntervalSeconds = config.getInt("grief-undo.interval-seconds", 300);
        griefUndoBlackList = config.getIntList("grief-undo.black-list", blacklist);
        useMysql = config.getBoolean("mysql.enable", false);
        host = config.getString("mysql.host", "localhost");
        database = config.getString("mysql.database", "minecraft");
        username = config.getString("mysql.username", "");
        password = config.getString("mysql.password", "");

        addForceFieldStones(forceFieldBlocks);

        save();
    }

    public void save()
    {
        Configuration config = plugin.getConfiguration();

        config.setProperty("force-field-blocks", forceFieldBlocks);
        config.setProperty("unbreakable-blocks", unbreakableBlocks);
        config.setProperty("bypass-blocks", bypassBlocks);
        config.setProperty("unprotectable-blocks", unprotectableBlocks);
        config.setProperty("tool-items", toolItems);
        config.setProperty("log.fire", logFire);
        config.setProperty("log.entry", logEntry);
        config.setProperty("log.place", logPlace);
        config.setProperty("log.use", logUse);
        config.setProperty("log.pvp", logPvp);
        config.setProperty("log.destroy", logDestroy);
        config.setProperty("log.destroy-area", logDestroyArea);
        config.setProperty("log.unprotectable", logUnprotectable);
        config.setProperty("log.bypass-pvp", logBypassPvp);
        config.setProperty("log.bypass-delete", logBypassDelete);
        config.setProperty("log.bypass-place", logBypassPlace);
        config.setProperty("log.bypass-destroy", logBypassDestroy);
        config.setProperty("log.conflict-place", logConflictPlace);
        config.setProperty("notify.place", notifyPlace);
        config.setProperty("notify.destroy", notifyDestroy);
        config.setProperty("notify.bypass-unprotectable", notifyBypassUnprotectable);
        config.setProperty("notify.bypass-pvp", notifyBypassPvp);
        config.setProperty("notify.bypass-place", notifyBypassPlace);
        config.setProperty("notify.bypass-destroy", notifyBypassDestroy);
        config.setProperty("warn.instant-heal", warnInstantHeal);
        config.setProperty("warn.slow-heal", warnSlowHeal);
        config.setProperty("warn.slow-damage", warnSlowDamage);
        config.setProperty("warn.fast-damage", warnFastDamage);
        config.setProperty("warn.give-air", warnGiveAir);
        config.setProperty("warn.fire", warnFire);
        config.setProperty("warn.entry", warnEntry);
        config.setProperty("warn.place", warnPlace);
        config.setProperty("warn.use", warnUse);
        config.setProperty("warn.pvp", warnPvp);
        config.setProperty("warn.destroy", warnDestroy);
        config.setProperty("warn.destroy-area", warnDestroyArea);
        config.setProperty("warn.unprotectable", warnUnprotectable);
        config.setProperty("warn.launch", warnLaunch);
        config.setProperty("warn.cannon", warnCannon);
        config.setProperty("warn.mine", warnMine);
        config.setProperty("settings.public-block-details", publicBlockDetails);
        config.setProperty("settings.sneaking-bypasses-damage", sneakingBypassesDamage);
        config.setProperty("settings.allowed-can-break-pstones", allowedCanBreakPstones);
        config.setProperty("settings.drop-on-delete", dropOnDelete);
        config.setProperty("settings.disable-alerts-for-admins", disableAlertsForAdmins);
        config.setProperty("settings.disable-bypass-alerts-for-admins", disableBypassAlertsForAdmins);
        config.setProperty("settings.off-by-default", offByDefault);
        config.setProperty("settings.lines-per-page", linesPerPage);
        config.setProperty("saving.frequency-seconds", saveFrequency);
        config.setProperty("saving.batch-size", saveBatchSize);
        config.setProperty("saving.max-records-per-snitch", maxSnitchRecords);
        config.setProperty("visualization.mark-block-type", visualizeMarkBlock);
        config.setProperty("visualization.block-type", visualizeBlock);
        config.setProperty("visualization.seconds", visualizeSeconds);
        config.setProperty("visualization.end-on-player-move", visualizeEndOnMove);
        config.setProperty("visualization.admin-chunk-radius", visualizeAdminChunkRadius);
        config.setProperty("forester.interval-seconds", foresterInterval);
        config.setProperty("forester.fertile-blocks", foresterFertileBlocks);
        config.setProperty("grief-undo.interval-seconds", griefIntervalSeconds);
        config.setProperty("grief-undo.black-list", griefUndoBlackList);
        config.setProperty("mysql.enable", useMysql);
        config.setProperty("mysql.host", host);
        config.setProperty("mysql.database", database);
        config.setProperty("mysql.username", username);
        config.setProperty("mysql.password", password);

        config.save();
    }

    /**
     *
     * @param maps
     */
    @SuppressWarnings("unchecked")
    public void addForceFieldStones(ArrayList<LinkedHashMap> maps)
    {
        if (maps == null)
        {
            return;
        }

        double largestForceField = 0;

        for (LinkedHashMap map : maps)
        {
            FieldSettings pstone = new FieldSettings(map);

            if (pstone.blockDefined)
            {
                // add stone to our collection
                fieldsettings.put(pstone.blockId, pstone);

                // add the values to our reference lists
                ffBlocks.add(pstone.blockId);

                // see if the radius is the largest
                if (pstone.radius > largestForceField)
                {
                    largestForceField = pstone.radius;
                }
            }
        }

        PreciousStones.log(Level.INFO, "configured fields: {0}", maps.size());

        chunksInLargestForceFieldArea = (int) Math.max(Math.ceil(((largestForceField * 2.0) + 1.0) / 16.0), 1);
    }

    /**
     * Whether any pstones have welcome or farewell flags
     * @return
     */
    public boolean haveNameable()
    {
        for (FieldSettings setting : fieldsettings.values())
        {
            if (setting.welcomeMessage || setting.farewellMessage)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Whether any pstones have cannon or launch flag
     * @return
     */
    public boolean haveVelocity()
    {
        for (FieldSettings setting : fieldsettings.values())
        {
            if (setting.cannon || setting.launch)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Whether any pstones have snitch flag
     * @return
     */
    public boolean haveSnitch()
    {
        for (FieldSettings setting : fieldsettings.values())
        {
            if (setting.snitch)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if a type is one of the unprotectable types
     * @param placedblock
     * @return
     */
    public boolean isUnprotectableType(int type)
    {
        return unprotectableBlocks.contains(type);
    }

    /**
     * Check if the id is one of forrester fertile types
     * @param block
     * @return
     */
    public boolean isFertileType(int id)
    {
        return foresterFertileBlocks.contains(id);
    }

    /**
     * Check if the id is one of grief undo blacklisted types
     * @param block
     * @return
     */
    public boolean isGriefUndoBlackListType(int id)
    {
        return griefUndoBlackList.contains(id);
    }

    /**
     * Check if a block is one of the tool item types
     * @param block
     * @return
     */
    public boolean isToolItemType(Block block)
    {
        return toolItems.contains(block.getTypeId());
    }

    /**
     * Check if a block is one of the tool item types
     * @param typeId
     * @return
     */
    public boolean isToolItemType(int typeId)
    {
        return toolItems.contains(typeId);
    }

    /**
     * Check if a block is one of the tool item types
     * @param type
     * @return
     */
    public boolean isToolItemType(String type)
    {
        return toolItems.contains(Material.getMaterial(type).getId());
    }

    /**
     * Check if a block is one of the snitch types
     * @param block
     * @return
     */
    public boolean isSnitchType(Block block)
    {
        for (FieldSettings setting : fieldsettings.values())
        {
            if (setting.snitch && setting.blockId == block.getTypeId())
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if a block is one of the unbreakable types
     * @param unbreakableblock
     * @return
     */
    public boolean isUnbreakableType(Block unbreakableblock)
    {
        return unbreakableBlocks.contains(unbreakableblock.getTypeId());
    }

    /**
     * Check if a type is one of the unbreakable types
     * @param typeId
     * @return
     */
    public boolean isUnbreakableType(int typeId)
    {
        return unbreakableBlocks.contains(typeId);
    }

    /**
     * Check if a type is one of the unbreakable types
     * @param type
     * @return
     */
    public boolean isUnbreakableType(String type)
    {
        return unbreakableBlocks.contains(Material.getMaterial(type).getId());
    }

    /**
     * Check if a block is one of the forcefeld types
     * @param block
     * @return
     */
    public boolean isFieldType(Block block)
    {
        return ffBlocks.contains(block.getTypeId());
    }

    /**
     * Check if a type is one of the forcefeld types
     * @param type
     * @return
     */
    public boolean isFieldType(String type)
    {
        return ffBlocks.contains(Material.getMaterial(type).getId());
    }

    /**
     * Check if the material is one of the forcefeld types
     * @param material
     * @return
     */
    public boolean isFieldType(Material material)
    {
        return ffBlocks.contains(material.getId());
    }

    /**
     * Check if a type is one of the forcefeld types
     * @param typeId
     * @return
     */
    public boolean isFieldType(int typeId)
    {
        return ffBlocks.contains(typeId);
    }

    /**
     * Whetehr the block is a bypass type
     * @param block
     * @return
     */
    public boolean isBypassBlock(Block block)
    {
        return bypassBlocks.contains(block.getTypeId());
    }

    /**
     * Returns the settings for a specific field type
     * @param field
     * @return
     */
    public FieldSettings getFieldSettings(Field field)
    {
        return getFieldSettings(field.getTypeId());
    }

    /**
     * Returns the settings for a specific block type
     * @param typeId
     * @return
     */
    public FieldSettings getFieldSettings(int typeId)
    {
        return fieldsettings.get(typeId);
    }

    /**
     * Returns all the field settings
     * @return
     */
    public HashMap<Integer, FieldSettings> getFieldSettings()
    {
        return fieldsettings;
    }

    /**
     *
     */
    public class FieldSettings
    {
        public boolean blockDefined = true;
        public int blockId;
        public int radius = 0;
        public int height = 0;
        public int launchHeight = 0;
        public int cannonHeight = 0;
        public int mineDelaySeconds = 0;
        public int mineReplaceBlock = 0;
        public int lightningDelaySeconds = 0;
        public int lightningReplaceBlock = 0;
        public String title;
        public boolean preventFire = false;
        public boolean preventPlace = false;
        public boolean preventDestroy = false;
        public boolean preventExplosions = false;
        public boolean preventPvP = false;
        public boolean preventMobDamage = false;
        public boolean preventMobSpawn = false;
        public boolean preventAnimalSpawn = false;
        public boolean preventEntry = false;
        public boolean preventUnprotectable = false;
        public boolean preventFlow = false;
        public List<Integer> preventUse = new ArrayList<Integer>();
        public boolean instantHeal = false;
        public boolean slowHeal = false;
        public boolean slowDamage = false;
        public boolean fastDamage = false;
        public boolean breakable = false;
        public boolean welcomeMessage = false;
        public boolean farewellMessage = false;
        public boolean giveAir = false;
        public boolean snitch = false;
        public boolean noConflict = false;
        public boolean launch = false;
        public boolean cannon = false;
        public boolean mine = false;
        public boolean lightning = false;
        public boolean noOwner = false;
        public boolean forester = false;
        public boolean foresterShrubs = false;
        public boolean griefUndoInterval = false;
        public boolean griefUndoRequest = false;
        public boolean entryAlert = false;

        /**
         *
         * @return
         */
        public String getTitle()
        {
            if (title == null)
            {
                return "";
            }

            return title;
        }

        /**
         *
         * @return
         */
        public String getTitleCap()
        {
            if (title == null)
            {
                return "";
            }

            return Helper.capitalize(title);
        }

        /**
         *
         * @return
         */
        public int getHeight()
        {
            if (this.height == 0)
            {
                return (this.radius * 2) + 1;
            }
            else
            {
                return this.height;
            }
        }

        /**
         * Whether a block type can be used in this field
         * @return
         */
        public boolean canUse(int type)
        {
            return !preventUse.contains(type);
        }

        /**
         *
         * @param map
         */
        @SuppressWarnings("unchecked")
        public FieldSettings(LinkedHashMap map)
        {
            if (map.containsKey("block") && Helper.isInteger(map.get("block")))
            {
                blockId = (Integer) map.get("block");
            }
            else
            {
                blockDefined = false;
                return;
            }

            if (map.containsKey("title") && Helper.isString(map.get("title")))
            {
                title = (String) map.get("title");
            }

            if (map.containsKey("radius") && Helper.isInteger(map.get("radius")))
            {
                radius = (Integer) map.get("radius");
            }

            if (map.containsKey("custom-height"))
            {
                if (Helper.isInteger(map.get("custom-height")))
                {
                    height = (Integer) map.get("custom-height");

                }
                if (height == 0)
                {
                    height = radius;
                }
            }

            if (map.containsKey("prevent-fire") && Helper.isBoolean(map.get("prevent-fire")))
            {
                preventFire = (Boolean) map.get("prevent-fire");
            }

            if (map.containsKey("prevent-place") && Helper.isBoolean(map.get("prevent-place")))
            {
                preventPlace = (Boolean) map.get("prevent-place");
            }

            if (map.containsKey("prevent-destroy") && Helper.isBoolean(map.get("prevent-destroy")))
            {
                preventDestroy = (Boolean) map.get("prevent-destroy");
            }

            if (map.containsKey("prevent-explosions") && Helper.isBoolean(map.get("prevent-explosions")))
            {
                preventExplosions = (Boolean) map.get("prevent-explosions");
            }

            if (map.containsKey("prevent-pvp") && Helper.isBoolean(map.get("prevent-pvp")))
            {
                preventPvP = (Boolean) map.get("prevent-pvp");
            }

            if (map.containsKey("prevent-mob-damage") && Helper.isBoolean(map.get("prevent-mob-damage")))
            {
                preventMobDamage = (Boolean) map.get("prevent-mob-damage");
            }

            if (map.containsKey("prevent-mob-spawn") && Helper.isBoolean(map.get("prevent-mob-spawn")))
            {
                preventMobSpawn = (Boolean) map.get("prevent-mob-spawn");
            }

            if (map.containsKey("prevent-animal-spawn") && Helper.isBoolean(map.get("prevent-animal-spawn")))
            {
                preventAnimalSpawn = (Boolean) map.get("prevent-animal-spawn");
            }

            if (map.containsKey("prevent-entry") && Helper.isBoolean(map.get("prevent-entry")))
            {
                preventEntry = (Boolean) map.get("prevent-entry");
            }

            if (map.containsKey("prevent-unprotectable") && Helper.isBoolean(map.get("prevent-unprotectable")))
            {
                preventUnprotectable = (Boolean) map.get("prevent-unprotectable");
            }

            if (map.containsKey("instant-heal") && Helper.isBoolean(map.get("instant-heal")))
            {
                instantHeal = (Boolean) map.get("instant-heal");
            }

            if (map.containsKey("slow-heal") && Helper.isBoolean(map.get("slow-heal")))
            {
                slowHeal = (Boolean) map.get("slow-heal");
            }

            if (map.containsKey("slow-damage") && Helper.isBoolean(map.get("slow-damage")))
            {
                slowDamage = (Boolean) map.get("slow-damage");
            }

            if (map.containsKey("fast-damage") && Helper.isBoolean(map.get("fast-damage")))
            {
                fastDamage = (Boolean) map.get("fast-damage");
            }

            if (map.containsKey("breakable") && Helper.isBoolean(map.get("breakable")))
            {
                breakable = (Boolean) map.get("breakable");
            }

            if (map.containsKey("welcome-message") && Helper.isBoolean(map.get("welcome-message")))
            {
                welcomeMessage = (Boolean) map.get("welcome-message");
            }

            if (map.containsKey("farewell-message") && Helper.isBoolean(map.get("farewell-message")))
            {
                farewellMessage = (Boolean) map.get("farewell-message");
            }

            if (map.containsKey("give-air") && Helper.isBoolean(map.get("give-air")))
            {
                giveAir = (Boolean) map.get("give-air");
            }

            if (map.containsKey("snitch") && Helper.isBoolean(map.get("snitch")))
            {
                snitch = (Boolean) map.get("snitch");
            }

            if (map.containsKey("no-conflict") && Helper.isBoolean(map.get("no-conflict")))
            {
                noConflict = (Boolean) map.get("no-conflict");
            }

            if (map.containsKey("launch") && Helper.isBoolean(map.get("launch")))
            {
                launch = (Boolean) map.get("launch");
            }

            if (map.containsKey("launch-height") && Helper.isInteger(map.get("launch-height")))
            {
                launchHeight = (Integer) map.get("launch-height");
            }

            if (map.containsKey("cannon") && Helper.isBoolean(map.get("cannon")))
            {
                cannon = (Boolean) map.get("cannon");
            }

            if (map.containsKey("cannon-height") && Helper.isInteger(map.get("cannon-height")))
            {
                cannonHeight = (Integer) map.get("cannon-height");
            }

            if (map.containsKey("mine") && Helper.isBoolean(map.get("mine")))
            {
                mine = (Boolean) map.get("mine");
            }

            if (map.containsKey("mine-replace-block") && Helper.isInteger(map.get("mine-replace-block")))
            {
                mineReplaceBlock = (Integer) map.get("mine-replace-block");
            }

            if (map.containsKey("mine-delay-seconds") && Helper.isInteger(map.get("mine-delay-seconds")))
            {
                mineDelaySeconds = (Integer) map.get("mine-delay-seconds");
            }

            if (map.containsKey("lightning") && Helper.isBoolean(map.get("lightning")))
            {
                lightning = (Boolean) map.get("lightning");
            }

            if (map.containsKey("lightning-replace-block") && Helper.isInteger(map.get("lightning-replace-block")))
            {
                lightningReplaceBlock = (Integer) map.get("lightning-replace-block");
            }

            if (map.containsKey("lightning-delay-seconds") && Helper.isInteger(map.get("lightning-delay-seconds")))
            {
                lightningDelaySeconds = (Integer) map.get("lightning-delay-seconds");
            }

            if (map.containsKey("prevent-flow") && Helper.isBoolean(map.get("prevent-flow")))
            {
                preventFlow = (Boolean) map.get("prevent-flow");
            }

            if (map.containsKey("prevent-use") && Helper.isIntList(map.get("prevent-use")))
            {
                preventUse = (List<Integer>) map.get("prevent-use");
            }

            if (map.containsKey("no-owner") && Helper.isBoolean(map.get("no-owner")))
            {
                noOwner = (Boolean) map.get("no-owner");
            }

            if (map.containsKey("forester") && Helper.isBoolean(map.get("forester")))
            {
                forester = (Boolean) map.get("forester");
            }

            if (map.containsKey("forester-shrubs") && Helper.isBoolean(map.get("forester-shrubs")))
            {
                foresterShrubs = (Boolean) map.get("forester-shrubs");
            }

            if (map.containsKey("grief-undo-request") && Helper.isBoolean(map.get("grief-undo-request")))
            {
                griefUndoRequest = (Boolean) map.get("grief-undo-request");
            }

            if (map.containsKey("grief-undo-interval") && Helper.isBoolean(map.get("grief-undo-interval")))
            {
                griefUndoInterval = (Boolean) map.get("grief-undo-interval");
            }

            if (map.containsKey("entry-alert") && Helper.isBoolean(map.get("entry-alert")))
            {
                entryAlert = (Boolean) map.get("entry-alert");
            }
        }
    }
}
