package net.han;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by han on 2016/1/30.
 */
public final class BuildClonePlugin extends JavaPlugin {
      Map playerList=new HashMap();
    @Override
    public void onEnable() {
        getLogger().info("xiao_xi 插件激活!");
        this.getCommand("xiaoxi").setExecutor(new MyPluginCommandExecutor(this));
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {

            playerList.put(player.getName(), playerData(player));
        }
    }

    private Object playerData(Player player) {

        return player;
    }

    @Override
    public void onDisable() {
        getLogger().info("xiao_xi 插件退出!");
    }
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer(); // The player who joined
        PlayerInventory inventory = player.getInventory(); // The player's inventory
        ItemStack itemstack = new ItemStack(Material.DIAMOND, 64); // A stack of diamonds

        if (inventory.contains(itemstack)) {
            inventory.addItem(itemstack); // Adds a stack of diamonds to the player's inventory
            player.sendMessage("Welcome! You seem to be reeeally rich, so we gave you some more diamonds!");
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Get the player's location.
        Location loc = event.getPlayer().getLocation();
        // Sets loc to five above where it used to be. Note that this doesn't change the player's position.
        loc.setY(loc.getY() + 5);
        // Gets the block at the new location.
        Block b = loc.getBlock();
        // Sets the block to type id 1 (stone).
        b.setType(Material.STONE);
    }

}
