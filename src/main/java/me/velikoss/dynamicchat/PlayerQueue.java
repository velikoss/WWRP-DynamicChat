package me.velikoss.dynamicchat;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

public class PlayerQueue {

    private final Player player;
    private boolean lock, visibility = true;
    private byte pointer;
    private final IText[] array;

    public PlayerQueue(Player player){
        this.player = player;
        array = new IText[100];
    }

    public void setLock(boolean set){
        this.lock = set;
    }

    public void setVisibility(boolean set){
        this.visibility = set;
    }

    public boolean getLock(){
        return lock;
    }

    public boolean getVisibility(){
        return visibility;
    }

    public synchronized void putMessage(WrappedChatComponent component) {
        array[pointer] = new WrappedText(component);
        pointer++;
        if(pointer >= array.length) pointer = 0;
    }

    public synchronized void putMessage(ChatMessage message) {
        array[pointer] = new ChatText(message);
        pointer++;
        if(pointer >= array.length) pointer = 0;
    }

    public synchronized void send(){
        lock = true;
        try {
            send0();
        } catch(Throwable e) {
            lock = false;
            throw e;
        }
        lock = false;
    }

    private void send0(){
        for (byte i = 0; i < 100; ++i) player.sendMessage("\n");
        for (int i = pointer; i < array.length; i++) if(array[i] != null) array[i].send(player);
        for (int i = 0; i < pointer; i++)  if(array[i] != null) array[i].send(player);
    }

}
