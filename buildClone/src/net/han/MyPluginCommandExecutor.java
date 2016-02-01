package net.han;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
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
                    if (args[0].equalsIgnoreCase("m"))
                        generateCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

                    if (args[0].equalsIgnoreCase("c"))
                        copyCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

                    if (args[0].equalsIgnoreCase("v"))
                        pasteCube(location, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                    return true;
                }else if (args.length==2){
                    if (args[0].equalsIgnoreCase("x")) {
                        List<String> lores = new ArrayList<String>();
                        lores.add("Example lore");
                        lores.add("this one comes on line 2");
                        ItemStack myItem = new ItemStack(Material.DIAMOND_SWORD);  //your item
                        myItem.addEnchantment(Enchantment.DAMAGE_ALL, Integer.parseInt(args[1]));  //enchant the item
                        ItemMeta im = myItem.getItemMeta(); //get the itemmeta of the item again
                        im.setLore(lores); //add the lores of course
                        myItem.setItemMeta(im); //give the item the new itemmeta
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
           if (locCub.getX()>X) continue;
           if (locCub.getY()>Y) continue;
           if (locCub.getY()>Y) continue;
           plugin.getLogger().info(" X=" + (loc.getBlockX()+locCub.getX()) + "," +
                                    " Y=" + (loc.getBlockY()+locCub.getY()) + "," +
                                    " Z=" + (loc.getBlockZ()+locCub.getZ()));
           currentBlock.setType(block.getType());
       }



    }
    public void generateCube(Location loc, int x, int y, int z) {
        // Set one corner of the cube to the given location.
        // Uses getBlockN() instead of getN() to avoid casting to an int later.
        int x1 = loc.getBlockX();
        int y1 = loc.getBlockY();
        int z1 = loc.getBlockZ();

        // Figure out the opposite corner of the cube by taking the corner and adding length to all coordinates.
        int x2 = x1 + x;
        int y2 = y1 + y;
        int z2 = z1 + z;

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

    private static double func1(double num1,double num2){
        if(num1<0)return num1+num2;
        return num1-num2;
    }
    private static double disFromCenter(Vector v){
        return Math.sqrt(v.getX()*v.getX()+v.getY()*v.getY()+v.getZ()*v.getZ());
    }
    private static void weaken(Vector v,double wval){
        double dis=disFromCenter(v);
        v.setX(func1(v.getX(),(wval/dis)*v.getX()));
        v.setY(func1(v.getY(),(wval/dis)*v.getY()));
        v.setZ(func1(v.getZ(),(wval/dis)*v.getZ()));
    }
    private static double distance(Location loc,double x,double y,double z){
        return Math.sqrt((loc.getX()-x)*(loc.getX()-x)+(loc.getY()-y)*(loc.getY()-y)+(loc.getZ()-z)*(loc.getZ()-z));
    }

    public static List<Entity> explore(Location loc,int damage,int raidus){

        World hdw=loc.getWorld();

        List<Entity> ret=new ArrayList<Entity>();
        Collection<Entity> sch=loc.getWorld().getNearbyEntities(loc,raidus,raidus,raidus);
        for(Entity e:sch){
            if(loc.distance(e.getLocation())<=raidus)ret.add(e);
        }
        for(int x=loc.getBlockX()-raidus;x<=loc.getBlockX()+raidus;x++)
            for(int y=loc.getBlockY()-raidus;y<=loc.getBlockY()+raidus;y++)
                for(int z=loc.getBlockZ()-raidus;z<=loc.getBlockZ()+raidus;z++){
                    if(distanceSquared(loc,x,y,z)<=raidus*raidus){
                        Block b=hdw.getBlockAt(x,y,z);
                        if(!b.getType().isBlock())continue;
                        if(b.getType().getMaxDurability()>(short)(damage-damage*((distance(loc,x,y,z)/raidus))))continue;
                        Location bkat=b.getLocation();
                        Material type=b.getType();
                        if(b.breakNaturally(null)){
                            FallingBlock ent=hdw.spawnFallingBlock(bkat,type,(byte) 0);
                            ret.add(ent);
                        }
                    }
                }
        for(Entity e:ret){
            Vector v=e.getVelocity();//
            Vector v2=e.getLocation().subtract(loc).toVector();
            double dis=e.getLocation().distance(loc);
            double proportion=(double)(raidus)/dis;
            v2.setX(v2.getX()*proportion);
            v2.setY(v2.getY()*proportion);
            v2.setZ(v2.getZ()*proportion);
            weaken(v2,dis);
            v2.multiply(0.2f);
            v.add(v2);
            e.setVelocity(v);
        }

        return ret;

        //TODO

    }
    private static double distanceSquared(Location loc, int x, int y, int z) {
        return (loc.getX()-x)*(loc.getX()-x)+(loc.getY()-y)*(loc.getY()-y)+(loc.getZ()-z)*(loc.getZ()-z);
    }
}
