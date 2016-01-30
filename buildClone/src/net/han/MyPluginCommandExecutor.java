package net.han;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
        if (args.length > 5) {
            sender.sendMessage("Too many arguments!");
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage("Not enough arguments!");
            return false;
        }

        Player target = (Bukkit.getServer().getPlayer(args[0]));
        if (target == null) {
            sender.sendMessage(args[0] + " is not online!");
            plugin.getLogger().info(args[0]);
            return false;
        }
        String cmdCV=args[0];
        String  X= args[1];
        String  Y =args[2];
        String  Z=args[3];
        plugin.getLogger().info( cmdCV + " X="+X +",Y="+Y+",Z=" +Z );
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("xiaoxi")) {
                player = (Player) sender;
                plugin.getLogger().info(player.getDisplayName() + " use command xiaoxi");
                world = player.getWorld();
                location = player.getLocation();
                inventory = player.getInventory();

                return true;
            }
        } else {
            sender.sendMessage("You must be a player!");
            return false;
        }
        // do something
        return false;


    }
}
