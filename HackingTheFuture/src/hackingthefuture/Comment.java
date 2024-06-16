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
    
    // Get ID of the comment
    public String getID(){
        return ID;
    }
    
    // Get ID of discussion which the comment belongs to
    public String getDiscussionID(){
        return discussionID;
    }

    // Get content of the comment
    public String getContent() {
        return content;
    }

    // Get date and time the comment posted
    public LocalDateTime getDatePublished() {
        return datePublished;
    }

    // Get the user who posted the comment
    public User getAuthor() {
        return author;
    }

    // Get users who liked the comment
    public String getUserLiked() {
        return userLiked;
    }

    // Get list of users who like the comment
    public List<String> getUserLikedList() {
        if (userLiked.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(userLiked.split(","));
    }

    // Get number of users liked the comment
    public int getLikeCount() {
        return getUserLikedList().size();
    }

    // Determine if the comment is liked by the specific user
    public boolean isUserLiked(User user) {
        return Arrays.asList(userLiked.split(",")).contains(user.getID());
    }

    // Save user when he/she like the comment
    public void liked(User user) {
        DiscussionHandler.commentLiked(this, user);
    }

    // Remove user when he/she dislike the comment
    public void disliked(User user) {
        DiscussionHandler.commentDisliked(this, user);
    }
    
    // Update users liked the comment
    public void updateUserLiked(String userLikedNew) {
        this.userLiked = userLikedNew;
    }
    
    // Get formatted string of date and time where the comment posted
    public String getFormattedDate(){
        return datePublished.format(DateTimeFormatter.ofPattern("d-M-yyyy  hh:mm a")).toUpperCase();
    }
}

