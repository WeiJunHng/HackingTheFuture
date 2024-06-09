/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Asus
 */
public class Quiz {
    private String ID, title, description, theme, content, educatorID;

    public Quiz(String ID, String title, String description, String theme, String content, String educatorID) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.theme = theme;
        this.content = content;
        this.educatorID = educatorID;
    }
    
    public Quiz(String title, String description, String theme, String content, String educatorID) {
        this.title = title;
        this.description = description;
        this.theme = theme;
        this.content = content;
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

    public String getTheme() {
        return theme;
    }

    public String getContent() {
        return content;
    }

    public String getEducatorID() {
        return educatorID;
    }
}
