package com.zstu.natsukawa.fasterner.msgmangement;

public class MessageEntity {

    private String messageTime;
    private String messageContent;

    public MessageEntity(String messageTime, String messageContent) {
        this.messageTime = messageTime;
        this.messageContent = messageContent;
    }

    public MessageEntity(){}

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
