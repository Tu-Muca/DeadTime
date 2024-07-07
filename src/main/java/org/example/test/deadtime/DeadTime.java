package org.example.test.deadtime;

import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public class DeadTime extends JavaPlugin {
    File data;
    FileConfiguration datafile;
    File Language;
    FileConfiguration LanguageFile;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§a§l[DeadTime] 插件正在加载..");
        Bukkit.getConsoleSender().sendMessage("§a§l[DeadTime] 正在载入配置..");
        saveDefaultConfig();
        File data = new File(this.getDataFolder(),"data.yml");
        FileConfiguration datafile = YamlConfiguration.loadConfiguration(data);
        File Language = new File(this.getDataFolder(),"language.yml");
        FileConfiguration LanguageFile = YamlConfiguration.loadConfiguration(Language);
        this.data = data;
        this.datafile = datafile;
        this.Language =Language;
        this.LanguageFile = LanguageFile;
        if (!(data.exists())){
            saveResource("data.yml",false);
        }
        if (!(Language.exists())){
            saveResource("language.yml",false);
        }
        try {
            LanguageFile.load(new File(getDataFolder(),"language.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getConsoleSender().sendMessage("§a§l[DeadTime] 正在载入事件..");
        Bukkit.getPluginManager().registerEvents(new Listener(this),this);
        Bukkit.getConsoleSender().sendMessage("§a§l[DeadTime] 正在载入指令..");
        Bukkit.getPluginCommand("DeadTime").setExecutor(new Command(this));
        Bukkit.getConsoleSender().sendMessage("§a§l[DeadTime] 插件载入完毕！");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p:Bukkit.getOnlinePlayers()){
                    String key = p.getName() + ".time";
                    String key_name = p.getName();
                    if (datafile.contains(p.getName()) & datafile.getInt(key) == 1){
                        String gamemodes = GetPlayerGamemode(p.getName());
                        p.setGameMode(GameMode.valueOf(gamemodes));
                        p.playSound(p,Sound.ENTITY_PLAYER_LEVELUP,1,1);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',LanguageFile.getString("Fix")) + ChatColor.translateAlternateColorCodes('&',LanguageFile.getString("Resurrection")));
                        if (IfKeepDeadLocation()) {
                            FMakePlayerTo(p);
                        } else {
                            MakePlayerTo(p);
                        }
                        datafile.set(key_name,null);
                    } else if (datafile.contains(p.getName())){
                        int time = datafile.getInt(key) -1;
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT,1,1);
                        datafile.set(key,time);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',LanguageFile.getString("Fix")) + ChatColor.translateAlternateColorCodes('&',LanguageFile.getString("NeedTime")) + time + ChatColor.translateAlternateColorCodes('&',LanguageFile.getString("Second")));
                    }
                    try {
                        datafile.save(data);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        },0L, 20L);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§a§l[DeadTime] 插件已卸载！");
    }

    public boolean IfInDeadTime(String name){
        return datafile.contains(name);
    }
    public boolean IfKeepDead(){
        return getConfig().getBoolean("KeepDead");
    }
    public boolean IfKeepDeadLocation(){
        return getConfig().getBoolean("KeepDeadLocation");
    }
    public String GetPlayerGamemode(String name){
        String key = name + ".Gamemode";
        return datafile.getString(key);
    }
    public void SetPlayerGamemode(String name,String Gamemode){
        String Key = name + ".Gamemode";
        datafile.set(Key,Gamemode);
        try {
            datafile.save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SetTime(String name){
        String key = name + ".time";
        int NeedTime = getConfig().getInt("NeedTime") +1;
        datafile.set(key,NeedTime);
        try {
            datafile.save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //从data文件夹中删除玩家
    public void SetNull(String name){
        datafile.set(name,null);
    }

    public void FSetXYZ(Player player){
        double X = player.getLocation().getX();
        double Y = player.getLocation().getY();
        double Z = player.getLocation().getZ();
        String KeyX = player.getName() + ".Flocation.X";
        String KeyY = player.getName() + ".Flocation.Y";
        String KeyZ = player.getName() + ".Flocation.Z";
        datafile.set(KeyX,X);
        datafile.set(KeyY,Y);
        datafile.set(KeyZ,Z);
        try {
            datafile.save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SetXYZ(Player player){
        double X = player.getLocation().getX();
        double Y = player.getLocation().getY();
        double Z = player.getLocation().getZ();
        String KeyX = player.getName() + ".location.X";
        String KeyY = player.getName() + ".location.Y";
        String KeyZ = player.getName() + ".location.Z";
        datafile.set(KeyX,X);
        datafile.set(KeyY,Y);
        datafile.set(KeyZ,Z);
        try {
            datafile.save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void FSetPlayerWorld(Player player){
        String world = player.getWorld().getName();
        String Key = player.getName() + ".Flocation.world";
        datafile.set(Key,world);
        try {
            datafile.save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SetPlayerWorld(Player player){
        String world = player.getWorld().getName();
        String Key = player.getName() + ".location.world";
        datafile.set(Key,world);
        try {
            datafile.save(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void FMakePlayerTo(Player p){
        String KeyX = p.getName() + ".Flocation.X";
        String KeyY = p.getName() + ".Flocation.Y";
        String KeyZ = p.getName() + ".Flocation.Z";
        String KeyWorld = p.getName() + ".Flocation.world";
        World World = Bukkit.getWorld(datafile.getString(KeyWorld));
        double X = datafile.getDouble(KeyX);
        double Y = datafile.getDouble(KeyY);
        double Z = datafile.getDouble(KeyZ);
        Location location = new Location(World,X,Y,Z);
        p.teleport(location);
    }
    public void MakePlayerTo(Player p){
        String KeyX = p.getName() + ".location.X";
        String KeyY = p.getName() + ".location.Y";
        String KeyZ = p.getName() + ".location.Z";
        String KeyWorld = p.getName() + ".location.world";
        World World = Bukkit.getWorld(datafile.getString(KeyWorld));
        double X = datafile.getDouble(KeyX);
        double Y = datafile.getDouble(KeyY);
        double Z = datafile.getDouble(KeyZ);
        Location location = new Location(World,X,Y,Z);
        p.teleport(location);
    }
    public void RewpawnTP(Player p){
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (!(IfKeepDeadLocation())){
                    SetXYZ(p);
                    SetPlayerWorld(p);
                }
                FMakePlayerTo(p);
            }
        },1L);
    }
    public void reload(){
        reloadConfig();
        try {
            LanguageFile.load(new File(getDataFolder(),"language.yml"));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    //language
    public String LGetFix(){
        return LanguageFile.getString("Fix");
    }
    public String LGetMainCommand(){
        return LanguageFile.getString("MainCommand");
    }
    public String LGetReload(){
        return LanguageFile.getString("Reload");
    }
}
