import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TweetRepository {
    private static final String TABLE_NAME = "tweets";
    private Session session;
    public TweetRepository(Session session) { this.session = session;}

    public void createTable() {
        System.out.println("Started create Table");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("id uuid PRIMARY KEY, ")
                .append("username text,")
                .append("tweetContent text,")
                .append("tweetDate date);");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished create Table");
    }



    public void inserttweet(Tweet tweet) {
        System.out.println("Started insert Tweet");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME).append(" (id,username,tweetContent,tweetDate) ")
                .append("VALUES ( ").append(tweet.getId()).append(", '")
                .append(tweet.getUsername()).append("', '")
                .append(tweet.getTweetContent()).append("', '")
                .append(tweet.getTweetdate()).append("');");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished insert Tweet");
    }



    public List<Tweet> selectAll() {
        System.out.println("Started select all");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);
        final String query = sb.toString();
        ResultSet rs = session.execute(query);
        List <Tweet> tweets = new ArrayList<Tweet>();
        for (Row r:rs) {
            Tweet s = new Tweet(r.getUUID("id"),r.getString("username"),r.getString("tweetContent"),r.getDate("tweetDate"));
            tweets.add(s);
        }
        System.out.println("Finished select all");
        return tweets;
    }


    public void deletetweet(UUID id) {
        System.out.println("Started delete tweet");
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME).append(" WHERE id = ").append(id).append(";");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished delete tweet");
    }

    public void deleteTable(String tableName) {
        System.out.println("Started delete Table");
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName);
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished delete Table");
    }
}
