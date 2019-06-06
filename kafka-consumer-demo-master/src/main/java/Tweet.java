import java.util.Date;

public class Tweet {
    private String username;
    private String tweetContent;
    private Date tweetdate;

    public Tweet() {}


    public Tweet(String username,String tweetContent,Date tweetdate) {
        this.username = username;
        this.tweetContent = tweetContent;
        this.tweetdate = tweetdate;
    }

    public String getUsername() {
        return username;
    }

    public Date getTweetdate() {
        return tweetdate;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }


    public void setTweetdate(Date tweetdate) {
        this.tweetdate = tweetdate;
    }


    public String toString() {
        return "@" + this.username +": " + this.tweetContent;
    }

}