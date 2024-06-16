/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Discussion {

    private String ID, title, content, userLiked;
    private LocalDateTime datePublished;
    private User author;

    public Discussion(String ID, String title, String authorID, String content, LocalDateTime datePublished, String userLiked) {
        this.ID = ID;
        this.title = title;
        this.author = UserHandler.getUser(authorID);
        this.content = content;
        this.datePublished = datePublished;
        this.userLiked = userLiked;
    }

    public Discussion(String title, String authorID, String content, LocalDateTime datePublished) {
        this.title = title;
        this.author = UserHandler.getUser(authorID);
        this.content = content;
        this.datePublished = datePublished;
    }
    
    // Getter for the object
    public String getID(){
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDatePublished() {
        return datePublished;
    }

    public User getAuthor() {
        return author;
    }

    public String getUserLiked() {
        return userLiked;
    }

    // Get list of user IDs who liked the discussion
    public List<String> getUserLikedList() {
        if (userLiked.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(userLiked.split(","));
    }

    // Get number of users liked the discussion
    public int getLikeCount() {
        return getUserLikedList().size();
    }

    // Get does the user liked the discussion
    public boolean isUserLiked(User user) {
        return Arrays.asList(userLiked.split(",")).contains(user.getID());
    }

    // User like the discussion
    public void liked(User user) {
        DiscussionHandler.liked(this, user);
    }

    // User dislike the discusison
    public void disliked(User user) {
        DiscussionHandler.disliked(this, user);
    }
    
    // Update users who liked the discussion
    public void updateUserLiked(String userLikedNew) {
        this.userLiked = userLikedNew;
    }
    
    // Get formatted string of published date of the discussion
    public String getFormattedDate(){
        return datePublished.format(DateTimeFormatter.ofPattern("d-M-yyyy  hh:mm a")).toUpperCase();
    }
}
