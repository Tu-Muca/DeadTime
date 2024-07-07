package org.example.test.deadtime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {
    private final DeadTime Deadtime;

    public Command(DeadTime plugin) {
        Deadtime = plugin;
    }

    @Override
    public boolean onCommand(CommandSender Sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(Sender instanceof Player)){
            if (args.length == 0) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',Deadtime.LGetFix())+ChatColor.translateAlternateColorCodes('&',Deadtime.LGetMainCommand()));
                return true;
            }
            if (args[0].equals("reload")){
                Deadtime.reload();
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',Deadtime.LGetFix())+ChatColor.translateAlternateColorCodes('&',Deadtime.LGetReload()));
                return true;
            }
            return false;
        }
        Player p = (Player) Sender;
        if (args.length==0){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',Deadtime.LGetFix())+ChatColor.translateAlternateColorCodes('&',Deadtime.LGetMainCommand()));
            return true;
        }
        if (args[0].equals("reload")){
            Deadtime.reload();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',Deadtime.LGetFix())+ChatColor.translateAlternateColorCodes('&',Deadtime.LGetReload()));
            return true;
        }
        return false;
    }
}
