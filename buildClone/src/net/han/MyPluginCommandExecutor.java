package net.han;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by han on 2016/1/30.
 */
public class MyPluginCommandExecutor implements CommandExecutor {
    private World world;
    private Location location;
    private final BuildClonePlugin plugin;
    private Player player;
    private Inventory inventory;

    public MyPluginCommandExecutor(BuildClonePlugin plugin) {
        this.plugin = plugin; // Store the plugin in situations where you need it.
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


    //    plugin.getLogger().info(cmdCV + " X=" + X + ",Y=" + Y + ",Z=" + Z);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("xiaoxi")) {

                plugin.getLogger().info(player.getDisplayName() + " use command xiaoxi");
                world = player.getWorld();
                location = player.getLocation();
                inventory = player.getInventory();
                if (args.length==4) {
                    String cmdCV=args[0];
                    String  X= args[1];
                    String  Y =args[2];
                    String  Z=args[3];
                    try{
                        if (args[0].equalsIgnoreCase("m"))
                            BuildUtils.generateCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

                        if (args[0].equalsIgnoreCase("c"))
                            BuildUtils.copyCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

                        if (args[0].equalsIgnoreCase("v"))
                            BuildUtils.pasteCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                        return true;
                    }
                    catch (Exception e) {
                        return  false;
                    }

                }else if (args.length==2){
                    if (args[0].equalsIgnoreCase("x")) {
                        try{
                            List<String> lores = new ArrayList<String>();
                            lores.add("Example lore");
                            lores.add("this one comes on line 2");
                            ItemStack myItem = new ItemStack(Material.DIAMOND_SWORD);  //your item
                            myItem.addEnchantment(Enchantment.DAMAGE_ALL, Integer.parseInt(args[1]));  //enchant the item
                            ItemMeta im = myItem.getItemMeta(); //get the itemmeta of the item again
                            im.setLore(lores); //add the lores of course
                            myItem.setItemMeta(im); //give the item the new itemmeta
                            return true;
                        }catch (Exception e) {
                            return false;
                        }

                    }
                }

            }
        } else {
            sender.sendMessage("You must be a player!");
            return false;
        }
        // do something
        return false;


    }



}
