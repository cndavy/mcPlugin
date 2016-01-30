package net.han;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
        if (args.length > 5) {
            sender.sendMessage("Too many arguments!");
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage("Not enough arguments!");
            return false;
        }

     /*   Player target = (Bukkit.getServer().getPlayer(args[0]));
        if (target == null) {
            sender.sendMessage(args[0] + " is not online!");
            plugin.getLogger().info(args[0]);
            return false;
        }*/
        String cmdCV=args[0];
        String  X= args[1];
        String  Y =args[2];
        String  Z=args[3];
        plugin.getLogger().info(cmdCV + " X=" + X + ",Y=" + Y + ",Z=" + Z);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("xiaoxi")) {
                player = (Player) sender;
                plugin.getLogger().info(player.getDisplayName() + " use command xiaoxi");
                world = player.getWorld();
                location = player.getLocation();
                inventory = player.getInventory();
                if (args[0].equalsIgnoreCase("m"))
                    generateCube(  location,Integer.parseInt(args[1]));

                if (args[0].equalsIgnoreCase("c"))
                    copyCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]),Integer.parseInt(args[3]));

                if (args[0].equalsIgnoreCase("v"))
                    pasteCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

                return true;
            }
        } else {
            sender.sendMessage("You must be a player!");
            return false;
        }
        // do something
        return false;


    }
private List<LocCub> cvList =null;

    public void copyCube(Location loc,int X,int Y,int Z ){
        int x1 = loc.getBlockX();
        int y1 = loc.getBlockY();
        int z1 = loc.getBlockZ();
        cvList=new ArrayList<LocCub>();

        // Figure out the opposite corner of the cube by taking the corner and adding length to all coordinates.
        int x2 = x1 + X;
        int y2 = y1 + Y;
        int z2 = z1 + Z;
        World world = loc.getWorld();
        for (int xPoint = x1; xPoint <= x2; xPoint++) {
            // Loop over the cube in the y dimension.
            for (int yPoint = y1; yPoint <= y2; yPoint++) {
                // Loop over the cube in the z dimension.
                for (int zPoint = z1; zPoint <= z2; zPoint++) {
                    // Get the block that we are currently looping over.
                    Block currentBlock = world.getBlockAt(xPoint, yPoint, zPoint);
                    // Set the block to type 57 (Diamond block!)
                    cvList.add(new LocCub(xPoint-x1, yPoint-y1, zPoint-z1 ,currentBlock));
                   // currentBlock.setType(Material.DIAMOND_BLOCK);
                    plugin.getLogger().info(" X=" + xPoint + ",Y=" + yPoint + ",Z=" + zPoint);
                }
            }
        }
    }
   private void pasteCube(Location loc,int X,int Y,int Z ){

        World world = loc.getWorld();
       for (LocCub locCub : cvList){
           Block block =  locCub.getBlock();

           Block currentBlock = world.getBlockAt(loc.getBlockX()+locCub.getX(),
                   loc.getBlockY()+locCub.getY(),
                   loc.getBlockZ()+locCub.getZ());

           plugin.getLogger().info(" X=" + (loc.getBlockX()+locCub.getX()) + "," +
                                    " Y=" + (loc.getBlockY()+locCub.getY()) + "," +
                                    " Z=" + (loc.getBlockZ()+locCub.getZ()));
           currentBlock.setType(block.getType());
       }



    }
    public void generateCube(Location loc, int length) {
        // Set one corner of the cube to the given location.
        // Uses getBlockN() instead of getN() to avoid casting to an int later.
        int x1 = loc.getBlockX();
        int y1 = loc.getBlockY();
        int z1 = loc.getBlockZ();

        // Figure out the opposite corner of the cube by taking the corner and adding length to all coordinates.
        int x2 = x1 + length;
        int y2 = y1 + length;
        int z2 = z1 + length;

        World world = loc.getWorld();

        // Loop over the cube in the x dimension.
        for (int xPoint = x1; xPoint <= x2; xPoint++) {
            // Loop over the cube in the y dimension.
            for (int yPoint = y1; yPoint <= y2; yPoint++) {
                // Loop over the cube in the z dimension.
                for (int zPoint = z1; zPoint <= z2; zPoint++) {
                    // Get the block that we are currently looping over.
                    Block currentBlock = world.getBlockAt(xPoint, yPoint, zPoint);
                    // Set the block to type 57 (Diamond block!)
                    currentBlock.setType(Material.DIAMOND_BLOCK);
                }
            }
        }
    }
}
