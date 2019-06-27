import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import twitter4j.GeoLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TweetRepository {
    private static final String TABLE_NAME = "tweets";
    private static final String TABLE_NAME_BY_COUNTRY = TABLE_NAME + "ByCountry";
    private Session session;
    public TweetRepository(Session session) { this.session = session;}

    public void createTable() {
        System.out.println("Started create Table");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("id uuid PRIMARY KEY, ")
                .append("username text,")
                .append("text text,")
                .append("created_at date,")
                .append("source text,")
                .append("isTruncated boolean,")
                .append("isFavourite boolean,")
                .append("geo_location_latitude float,")
                .append("geo_location_longitude float,")
                .append("lang text,")
                .append("country text,")
                .append("contributors list<float>);");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished create Table");
    }

    public void createTableTweetsByCountry() {
        System.out.println("Started create Table By Username");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME_BY_COUNTRY).append("(")
                .append("id uuid, ")
                .append("username text,")
                .append("text text,")
                .append("created_at date,")
                .append("source text,")
                .append("isTruncated boolean,")
                .append("isFavourite boolean,")
                .append("geo_location_latitude float,")
                .append("geo_location_longitude float,")
                .append("lang text,")
                .append("country text,")
                .append("contributors list<float>, PRIMARY KEY((id,country)));");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished create Table By Username");
    }



    public void inserttweet(Tweet tweet) {
        System.out.println("Started insert Tweet");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME).append(" (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) ")
                .append("VALUES ( ").append(tweet.getId()).append(", '")
                .append(tweet.getUsername()).append("', '")
                .append(tweet.getText()).append("', '")
                .append(tweet.getCreated_at()).append("', '")
                .append(tweet.getSource()).append("', ")
                .append(tweet.isTruncated()).append(", ")
                .append(tweet.isFavorited()).append(", ")
                .append(tweet.getGeoLocation().getLatitude()).append(", ")
                .append(tweet.getGeoLocation().getLongitude()).append(", '")
                .append(tweet.getLang()).append("', '")
                .append(tweet.getCountry()).append("', ")
                .append((tweet.getContributors())).append(");");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished insert Tweet");
    }

    public void inserttweetByCountry(Tweet tweet) {
        System.out.println("Started insert Tweet By Country");
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME_BY_COUNTRY).append(" (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) ")
                .append("VALUES ( ").append(tweet.getId()).append(", '")
                .append(tweet.getUsername()).append("', '")
                .append(tweet.getText()).append("', '")
                .append(tweet.getCreated_at()).append("', '")
                .append(tweet.getSource()).append("', ")
                .append(tweet.isTruncated()).append(", ")
                .append(tweet.isFavorited()).append(", ")
                .append(tweet.getGeoLocation().getLatitude()).append(", ")
                .append(tweet.getGeoLocation().getLongitude()).append(", '")
                .append(tweet.getLang()).append("', '")
                .append(tweet.getCountry()).append("', ")
                .append((tweet.getContributors())).append(");");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished insert Tweet By Country");
    }




    public List<Tweet> selectAll() {
        System.out.println("Started select all");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);
        final String query = sb.toString();
        ResultSet rs = session.execute(query);
        List <Tweet> tweets = new ArrayList<Tweet>();
        for (Row r:rs) {
            System.out.println(r);
            GeoLocation geo = new GeoLocation(r.getFloat("geo_location_latitude"),r.getFloat("geo_location_longitude"));
            Tweet s = new Tweet(r.getUUID("id"),r.getString("username"),r.getString("text"),
                    r.getDate("created_at"),r.getString("source"),r.getBool("isTruncated"),r.getBool("isFavourite"),
                    geo,r.getString("lang"),null,r.getString("country"));
            tweets.add(s);
        }
        System.out.println("Finished select all");
        return tweets;
    }

    public List<Tweet> selectByCountry(String country) {
        System.out.println("Started select by country");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_BY_COUNTRY).append(" WHERE country='").append(country).append("' ALLOW FILTERING");
        final String query = sb.toString();
        ResultSet rs = session.execute(query);
        List <Tweet> tweets = new ArrayList<Tweet>();
        for (Row r:rs) {
            System.out.println(r);
            GeoLocation geo = new GeoLocation(r.getFloat("geo_location_latitude"),r.getFloat("geo_location_longitude"));
            Tweet s = new Tweet(r.getUUID("id"),r.getString("username"),r.getString("text"),
                    r.getDate("created_at"),r.getString("source"),r.getBool("isTruncated"),r.getBool("isFavourite"),
                    geo,r.getString("lang"),null,r.getString("country"));
            tweets.add(s);
        }
        System.out.println("Finished select by country");
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
