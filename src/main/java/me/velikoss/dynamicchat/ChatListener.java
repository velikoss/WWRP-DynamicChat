package me.velikoss.dynamicchat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) return;
        e.setCancelled(true);

        DynamicChat.getInstance().sendMessage(e.getPlayer().getName(), e.getMessage());
        System.out.println(e.getPlayer().getName() +": "+ e.getMessage());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        DynamicChat.getInstance().getQueue().addPlayer(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        DynamicChat.getInstance().getQueue().removePlayer(e.getPlayer());
    }
}