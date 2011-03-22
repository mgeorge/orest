/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.domain;

import nz.ac.otago.orest.resource.RestResource;

/**
 *
 * @author geoma48p
 */
public class Message implements RestResource {

   private Integer messageId;
   private String sender;
   private String message;

   public Message() {
   }

   /**
    * ID is server assigned so this constructor can be used by clients to create messages to send.
    *
    * @param sender
    * @param message
    */
   public Message(String sender, String message) {
      this.sender = sender;
      this.message = message;
   }

   public Message(Integer messageId, String sender, String message) {
      this.messageId = messageId;
      this.sender = sender;
      this.message = message;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public Integer getMessageId() {
      return messageId;
   }

   public void setMessageId(Integer messageId) {
      this.messageId = messageId;
   }

   public String getSender() {
      return sender;
   }

   public void setSender(String sender) {
      this.sender = sender;
   }

   public String getResourceId() {
      return String.valueOf(this.getMessageId());
   }
}
