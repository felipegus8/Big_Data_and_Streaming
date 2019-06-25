import com.datastax.driver.core.LocalDate;

import java.util.Date;
import java.util.UUID;
import com.datastax.driver.core.LocalDate;

public class Tweet {
    private UUID id;
    private String username;
    private String tweetContent;
    private LocalDate tweetdate;

    public Tweet(UUID id,String username, String tweetContent, LocalDate tweetdate) {
        this.id = id;
        this.username = username;
        this.tweetContent = tweetContent;
        this.tweetdate = tweetdate;
    }

    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getTweetdate() {
        return tweetdate;
    }

    public String getTweetContent() {
        return tweetContent;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setTweetText(String tweetText) {
        this.tweetContent = tweetContent;
    }


    public String toString() {
        return "@" + this.username + ": " + this.tweetContent;
    }
}