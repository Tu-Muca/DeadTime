package org.example.test.deadtime;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Listener implements org.bukkit.event.Listener {
    private final DeadTime DeadTime;

    public Listener(org.example.test.deadtime.DeadTime deadTime) {
        DeadTime = deadTime;
    }

    @EventHandler
    public void OnPlayerDeath(PlayerDeathEvent e){
    }

    @EventHandler
    public void OnPlayerRespwan(PlayerRespawnEvent e){
        DeadTime.FSetXYZ(e.getPlayer());
        DeadTime.FSetPlayerWorld(e.getPlayer());
        DeadTime.RewpawnTP(e.getPlayer());
        DeadTime.SetPlayerGamemode(e.getPlayer().getName(), String.valueOf(e.getPlayer().getGameMode()));
        e.getPlayer().setGameMode(GameMode.SPECTATOR);
        DeadTime.SetTime(e.getPlayer().getName());
    }

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent e){
        Boolean IfInDeadTime = DeadTime.IfInDeadTime(e.getPlayer().getName());
        Boolean IfKeepDead = DeadTime.IfKeepDead();
        Boolean IfKeepDeadLocation = DeadTime.IfKeepDeadLocation();
        String Gamemodes = DeadTime.GetPlayerGamemode(e.getPlayer().getName());
        if (IfInDeadTime && IfKeepDead){
            e.getPlayer().setGameMode(GameMode.SPECTATOR);
        } else if (IfInDeadTime && IfKeepDeadLocation) {
            e.getPlayer().setGameMode(GameMode.valueOf(Gamemodes));
            DeadTime.FMakePlayerTo(e.getPlayer());
            DeadTime.SetNull(e.getPlayer().getName());
        } else if (IfInDeadTime) {
            e.getPlayer().setGameMode(GameMode.valueOf(Gamemodes));
            DeadTime.MakePlayerTo(e.getPlayer());
            DeadTime.SetNull(e.getPlayer().getName());
        }
    }
}
