package net.han;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by han on 2016/2/1.
 */
public final class BuildUtils {
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
    private  static List<LocCub> cvList =null;

    public static void copyCube(Location loc,int X,int Y,int Z ){
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
                    Bukkit.getServer().getLogger().info(" X=" + xPoint + ",Y=" + yPoint + ",Z=" + zPoint);
                }
            }
        }
    }
    public static void pasteCube(Location loc, int X, int Y, int Z){

        World world = loc.getWorld();
        for (LocCub locCub : cvList){
            Block block =  locCub.getBlock();

            Block currentBlock = world.getBlockAt(loc.getBlockX()+locCub.getX(),
                    loc.getBlockY()+locCub.getY(),
                    loc.getBlockZ()+locCub.getZ());
            if (locCub.getX()>X) continue;
            if (locCub.getY()>Y) continue;
            if (locCub.getY()>Y) continue;
            Bukkit.getServer().getLogger().info(" X=" + (loc.getBlockX()+locCub.getX()) + "," +
                    " Y=" + (loc.getBlockY()+locCub.getY()) + "," +
                    " Z=" + (loc.getBlockZ()+locCub.getZ()));
            currentBlock.setType(block.getType());
        }



    }
    public static void generateCube(Location loc, int x, int y, int z) {
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
public static void SendCustomEvent(String msg){
    // Create the event here
    CustomEvent event = new CustomEvent(msg);
// Call the event
    Bukkit.getServer().getPluginManager().callEvent(event);
// Check if the event is not cancelled
    if (!event.isCancelled()) {
        // Now you do the event
        Bukkit.getServer().broadcastMessage(event.getMessage());
    }
}


    // Lore set
    public void invSetup()

    {
        FileConfiguration config;
        config=Bukkit.getServer().getPluginManager().getPlugin("buildClone").getConfig();
        List<String> alos = new ArrayList<String>();
        Inventory inve = Bukkit.createInventory(null, 54, " §2 !DC! ");
        Inventory invee= Bukkit.createInventory(null, 54, " §2 !DC! ");
        alos.add(1 , "第一条lore");
        alos.add(2 , "第二条lore");
        alos.add(3 , "第三条lore");
        ItemStack a = new ItemStack(Material.DIAMOND_PICKAXE ,1) ;
        ItemMeta ai = a.getItemMeta();
        ItemStack b = new ItemStack(Material.ARROW ,1) ;
        ItemStack c = new ItemStack(Material.APPLE ,1) ;
        ItemStack d = new ItemStack(Material.BOOK ,1) ;
        ItemStack e = new ItemStack(Material.DIAMOND,90) ;
        inve.setItem(0,a);
        inve.setItem(2,b);
        inve.setItem(4,c);
        inve.setItem(6,d);
        inve.setItem(8,e);
        ai.setDisplayName("§2 lol~ ") ;
        ai.setLore(alos);
        a.setItemMeta(ai);
        ItemStack r = new ItemStack(Material.BOOK ,1) ;
        ItemMeta ri = a.getItemMeta();
        ItemStack q = new ItemStack(Material.WOODEN_DOOR ,1) ;
        ItemMeta qi = a.getItemMeta();
        ri.setDisplayName(config.getString(" " + config.getString("trr")));
        r.setItemMeta(ri);
        qi.setDisplayName("§2 返回到 !DC! 界面 ") ;
        q.setItemMeta(qi);
        invee.setItem(53, q);
        invee.setItem(0, r);
    }
}
