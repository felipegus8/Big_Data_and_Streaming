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

    public void setUsername(String username) {
      this.Username = username;
    }

    public void setTweetText(String tweetText) {
      this.TweetText = tweetText;
    }

    public void setDateSent(String dateSent) { this.DateSent = dateSent; }
         return TweetText;
    }
    public String getUsername() {
      return Username;
    }

    public String getDateSent() {
      return DateSent;
    }

    public String getTweetText() {
      return TweetText;
    }

    public String toString() {
      return "@" + this.Username +": " + this.TweetText;
    }

}
