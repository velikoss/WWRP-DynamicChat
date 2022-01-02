package me.velikoss.dynamicchat;

import org.bukkit.entity.Player;


public class ChatText implements IText {

    private ChatMessage msg;

    public ChatText(ChatMessage msg) {
        this.msg = msg;
    }

    @Override
    public void send(Player p) {
        DynamicChat.sendChatFormat(msg, p);
    }

}