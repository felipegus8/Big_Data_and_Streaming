import java.util.Date;

public class Tweet {
    private String username;
    private String tweetContent;
    private Date tweetdate;

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
}
