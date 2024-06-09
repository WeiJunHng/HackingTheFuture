/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Asus
 */
public class Comment {

    private String ID, content, discussionID, userLiked;
    private LocalDateTime datePublished;
    private User author;

    public Comment(String ID, String authorID, String content, String discussionID, LocalDateTime datePublished, String userLiked) {
        this.ID = ID;
        this.author = UserHandler.getUser(authorID);
        this.discussionID = discussionID;
        this.content = content;
        this.datePublished = datePublished;
        this.userLiked = userLiked;
    }
    
    public Comment(String authorID, String content, String discussionID, LocalDateTime datePublished) {
        this.author = UserHandler.getUser(authorID);
        this.discussionID = discussionID;
        this.content = content;
        this.datePublished = datePublished;
    }
    
    public String getID(){
        return ID;
    }
    
    public String getDiscussionID(){
        return discussionID;
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

    public List<String> getUserLikedList() {
        if (userLiked.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(userLiked.split(","));
    }

    public int getLikeCount() {
        return getUserLikedList().size();
    }

    public boolean isUserLiked(User user) {
        return Arrays.asList(userLiked.split(",")).contains(user.getID());
    }

    public void liked(User user) {
        DiscussionHandler.commentLiked(this, user);
    }

    public void disliked(User user) {
        DiscussionHandler.commentDisliked(this, user);
    }
    
    public void updateUserLiked(String userLikedNew) {
        this.userLiked = userLikedNew;
    }
    
    public String getFormattedDate(){
        return datePublished.format(DateTimeFormatter.ofPattern("d-M-yyyy  hh:mm a")).toUpperCase();
    }
}

