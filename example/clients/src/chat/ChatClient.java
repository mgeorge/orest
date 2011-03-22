package chat;

import chat.domain.Message;
import java.util.Collection;
import java.util.Scanner;
import nz.ac.otago.orest.client.BadResponse;
import nz.ac.otago.orest.client.RestClient;
import nz.ac.otago.orest.client.RestResponse;

/**
 *
 * @author mark
 */
public class ChatClient {

   public static void main(String[] args) throws Exception {

      // create client using URL that orest is listening on
      final RestClient client = new RestClient("http://localhost:8080/orest-example-services/rest/");

      // register resource (domain) class with framework
      client.addResourceType(Message.class, "message");


      // create polling thread
      new Thread() {

         @Override
         public void run() {
            while (true) {

               // get ids of all message resources
               Collection<String> messageIds = client.getCollection("messages");
               
               // iterate through the ids
               for (String id : messageIds) {

                  // send a GET request to get the resource matching the id
                  Message message = (Message) client.get("messages/" + id);

                  // extract and print details from deserialised message object
                  System.out.println(message.getSender() + ": " + message.getMessage());
               }

               try {
                  // sleep for a bit so we don't flood the network
                  Thread.sleep(2000);
               } catch (InterruptedException ignored) {
                  // ignored
               }
            }
         }
      }.start();

      // sending loop
      String username = "Fred";
      Scanner scanner = new Scanner(System.in);
      while (true) {
         System.out.println("Enter a message:");
         String message = scanner.nextLine();

         // create a message object to send
         Message m = new Message(username, message);

         try {
            // send the message via POST.
            client.post("messages", m);
         } catch(BadResponse ex) {
            System.out.println(ex.getMessage());
         }
      }
   }
}
