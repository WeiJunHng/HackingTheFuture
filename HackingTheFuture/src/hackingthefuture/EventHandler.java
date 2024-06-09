/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.util.List;

/**
 *
 * @author Asus
 */
public class EventHandler extends DBHandler {

    private static final DBHandler dBHandler = new DBHandler();

    public static void createEvent(Event event, Educator creator) {
        dBHandler.saveEvent(event, creator);
    }
    
    public static List<Event> getUpcomingEventList(){
        return dBHandler.getUpcomingEvents();
    }
    
    public static List<Event> getOngoingEventList(){
        return dBHandler.getOngoingEvents();
    }
}
