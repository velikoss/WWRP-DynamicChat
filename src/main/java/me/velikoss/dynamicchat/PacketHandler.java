package me.velikoss.dynamicchat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PacketHandler extends PacketAdapter {
    public PacketHandler(Plugin plugin, ListenerPriority priority, PacketType type) {
        super(plugin, priority, type);
    }
    @Override
    public void onPacketSending(PacketEvent event) {
        if(event.getPacketType() != PacketType.Play.Server.CHAT || DynamicChat.getInstance().isSending()) return;
        PacketContainer packet = event.getPacket();
        Player p = event.getPlayer();
        try {
            if(packet.getChatTypes().getValues().get(0) == EnumWrappers.ChatType.GAME_INFO) return;
        } catch(Exception e) {
            return;
        }
        PlayerQueue queue = DynamicChat.getInstance().getQueue().getPlayer(p);
        if(queue.getLock()) return;
        WrappedChatComponent c = packet.getChatComponents().getValues().get(0);
        queue.putMessage(c);
    }
}
