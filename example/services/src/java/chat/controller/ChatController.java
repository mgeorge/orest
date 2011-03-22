/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.controller;

import chat.domain.Message;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nz.ac.otago.orest.annotations.Controller;
import nz.ac.otago.orest.controller.RestController;

/**
 *
 * @author geoma48p
 */
@Controller(path="messages")
public class ChatController implements RestController<Message> {

    private static Integer lastIndex = 0;
    private static Map<Integer, Message> messages = new HashMap<Integer, Message>();

    public Collection<Message> getAll() {
        return messages.values();
    }

    public Message get(String id) {
        return messages.get(new Integer(id));
    }

    public void create(Message message) {
        messages.put(lastIndex++, message);
    }

    public void update(String id, Message domainToUpdate) {
        // nothing to do
    }

    public void delete(String id) {
        messages.remove(new Integer(id));
    }


}
