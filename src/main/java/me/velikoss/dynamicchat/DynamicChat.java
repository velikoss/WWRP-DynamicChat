package me.velikoss.dynamicchat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class DynamicChat extends JavaPlugin implements CommandExecutor {

    private static DynamicChat instance;
    private boolean sending = false;
    private MessageQueue queue;

    @Override
    public void onEnable() {
        instance = this;
        queue = new MessageQueue();
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketHandler(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT));
        getCommand("resend").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        queue.send();
        return false;
    }

    public void sendMessage(String sender, String message) {
        ChatMessage msg = new ChatMessage(sender, message);
        queue.putChatMessage(msg);
        sendChatMessage(msg);
    }

    public boolean isSending(){
        return sending;
    }

    public static DynamicChat getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MessageQueue getQueue() {
        return queue;
    }

    private void sendChatMessage(ChatMessage msg) {
        sending = true;

        try {
            for (Player p : Bukkit.getOnlinePlayers()) {
                queue.getPlayer(p).putMessage(msg);
                sendChatFormat(msg, p);
            }
        } catch(Exception e) {
            sending = false;
            throw e;
        }

        sending = false;
    }

    public static void sendChatFormat(ChatMessage msg, Player p) {
        p.spigot().sendMessage(ChatMessageType.CHAT,
                new TextComponent(msg.getMessage()));
    }
}
