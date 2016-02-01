package net.han;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Created by han on 2016/2/1.
 */
public class BuildCloneEventExecutor implements Listener {
  private   BuildClonePlugin me;
     public BuildCloneEventExecutor(BuildClonePlugin buildClonePlugin) {
         me=buildClonePlugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        Player player = evt.getPlayer(); // The player who joined
        PlayerInventory inventory = player.getInventory(); // The player's inventory
        ItemStack itemstack = new ItemStack(Material.DIAMOND, 64); // A stack of diamonds

        if (inventory.contains(itemstack)) {
            inventory.addItem(itemstack); // Adds a stack of diamonds to the player's inventory
            player.sendMessage("Welcome! You seem to be reeeally rich, so we gave you some more diamonds!");
        }
    }

   // @EventHandler
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
@EventHandler
    public  void onOpenBag(InventoryClickEvent evt){

    Inventory inve = Bukkit.createInventory(null, 54, " §2 !DC! ");
    ItemStack a = new ItemStack(Material.DIAMOND_PICKAXE ,1) ;
    ItemMeta ai = a.getItemMeta();
    ai.setDisplayName("§2 lol~ ") ;
    a.setItemMeta(ai);
                 evt.getClick().equals(ClickType.RIGHT);
    if (evt.getWhoClicked() instanceof Player == false) { return;}
    Player p = (Player)evt.getWhoClicked();
    if (!evt.getInventory().getTitle().equalsIgnoreCase("")) {return;}
    evt.setCancelled(true);
}
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Create the task anonymously and schedule to run it once, after 20 ticks
        new BukkitRunnable() {

            @Override
            public void run() {
                // What you want to schedule goes here
                me.getServer().broadcastMessage(
                        "Welcome to Bukkit! Remember to read the documentation!");
            }

        }.runTaskLater(this.me, 20);
    }
public void schedulerTask(){
    BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
    scheduler.scheduleSyncRepeatingTask(me, new Runnable() {
        @Override
        public void run() {
            // Do something
        }
    }, 0L, 20L);
}

}
