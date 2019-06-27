import com.datastax.driver.core.*;
import com.datastax.driver.core.utils.UUIDs;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import twitter4j.GeoLocation;
import twitter4j.Place;

public class HelloTweet {
    public static void main(String[] args) {
        System.out.println("Hello World");
        Cluster cluster = null;
        try {
            cluster = Cluster.builder().addContactPoint("localhost").build();
            Session session = cluster.connect();
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));

            KeyspaceRepository sr = new KeyspaceRepository(session);
            sr.createKeyspace("twitter","SimpleStrategy",1);

            sr.useKeyspace("twitter");

            TweetRepository tr = new TweetRepository(session);
            tr.createTable();
            tr.createTableTweetsByCountry();

            Tweet tweet = new Tweet(UUIDs.timeBased(),"User 1","Teste 1", LocalDate.fromYearMonthDay(2019,07,20),null,true,true,new GeoLocation(20,20),null,null,"Brazil");
            tr.inserttweet(tweet);
            Tweet tweet2 = new Tweet(UUIDs.timeBased(),"User 2","Teste 2",LocalDate.fromYearMonthDay(2019,07,21),null,false,false,new GeoLocation(30,30),null,null,"USA");
            tr.inserttweet(tweet2);
            tr.selectAll();
            tr.selectByCountry("USA");
            tr.deletetweet(tweet.getId());
            tr.deleteTable("tweets");
            tr.deleteTable("tweetsByCountry");
            sr.deleteKeyspace("twitter");

        } finally {
            if (cluster != null) cluster.close();
        }
    }
}
