/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Asus
 */
public class Event {

    private String ID, title, description, venue, educatorID;
    private LocalDate date;
    private LocalTime time;

    public Event(String ID, String title, String description, String venue, LocalDate date, LocalTime time, String educatorID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.educatorID = educatorID;
    }
    
    public Event(String title, String description, String venue, LocalDate date, LocalTime time, String educatorID) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.educatorID = educatorID;
    }

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern("d-M-yyyy"));
    }

    public LocalTime getTime() {
        return time;
    }
    
    public String getFormattedTime() {
        return time.format(DateTimeFormatter.ofPattern("hh:mm a")).toUpperCase();
    }

    public String getEducatorID() {
        return educatorID;
    }

}
