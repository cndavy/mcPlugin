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
         this.getServer().getPluginManager().registerEvents(new BuildCloneEventExecutor(this) ,this);
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


}
