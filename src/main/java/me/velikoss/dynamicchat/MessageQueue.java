package me.velikoss.dynamicchat;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MessageQueue {

    private Map<String, PlayerQueue> messages = new HashMap<String, PlayerQueue>();
    private ChatMessage[] array;
    private byte pointer;

    public MessageQueue() {
        array = new ChatMessage[100];
    }

    public synchronized void addPlayer(Player p) {
        messages.put(p.getName(), new PlayerQueue(p));
    }

    public synchronized void removePlayer(Player p) {
        messages.remove(p.getName());
    }

    public synchronized void putChatMessage(ChatMessage msg) {
        msg.setId(pointer);
        array[pointer] = msg;
        pointer++;
        if(pointer >= array.length) pointer = 0;
    }

    public synchronized void send() {
        for(PlayerQueue queue : messages.values()) {
            if(!queue.getVisibility()) return;
            queue.send();
        }
    }

    public synchronized PlayerQueue getPlayer(Player p) {
        PlayerQueue q = messages.get(p.getName());
        if(q == null) {
            q = new PlayerQueue(p);
            messages.put(p.getName(), q);
        }
        return q;
    }

}
