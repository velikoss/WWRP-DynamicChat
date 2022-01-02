package me.velikoss.dynamicchat;

public class ChatMessage {

    private String player;
    private String message;
    private int id;
    private boolean deleted;

    public ChatMessage(String player, String message) {
        this.player = player;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage(){
        return new FormatMessages().formatMessage(player, message);
    }

}
