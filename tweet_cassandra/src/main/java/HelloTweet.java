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
            tr.inserttweetByCountry(tweet);
            Tweet tweet2 = new Tweet(UUIDs.timeBased(),"User 2","Teste 2",LocalDate.fromYearMonthDay(2019,07,21),null,false,false,new GeoLocation(30,30),null,null,"USA");
            tr.inserttweet(tweet2);
            tr.inserttweetByCountry(tweet2);
            Tweet tweet3 = new Tweet(UUIDs.timeBased(),"User 3","Teste 3", LocalDate.fromYearMonthDay(2019,07,22),null,true,true,new GeoLocation(40,40),null,null,"Italy");
            tr.inserttweet(tweet3);
            tr.inserttweetByCountry(tweet3);
            Tweet tweet4 = new Tweet(UUIDs.timeBased(),"User 4","Teste 4", LocalDate.fromYearMonthDay(2019,07,23),null,true,true,new GeoLocation(50,50),null,null,"Brazil");
            tr.inserttweet(tweet4);
            tr.inserttweetByCountry(tweet4);
            Tweet tweet5 = new Tweet(UUIDs.timeBased(),"User 5","Teste 5", LocalDate.fromYearMonthDay(2019,07,24),null,true,true,new GeoLocation(60,60),null,null,"USA");
            tr.inserttweet(tweet5);
            tr.inserttweetByCountry(tweet5);
            tr.selectAll();
            tr.selectAllFromByCountry();
            tr.selectByCountry("USA");
            tr.deletetweet(tweet.getId());
            tr.selectAll();
            tr.deletetweetByCountry(tweet2.getId(),tweet2.getCountry());
            tr.selectAllFromByCountry();
            tr.deleteTable("tweets");
            tr.deleteTable("tweetsByCountry");
            sr.deleteKeyspace("twitter");

        } finally {
            if (cluster != null) cluster.close();
        }
    }
}
