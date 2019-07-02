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
        System.out.println("createTable --init");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("id long PRIMARY KEY, ")
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
        System.out.println(sb);
        session.execute(query);
        System.out.println("createTable --end\n");
    }

    public void createTableTweetsByCountry() {
        System.out.println("createTableTweetsByCountry --init");
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME_BY_COUNTRY).append("(")
                .append("id long, ")
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
        System.out.println(sb);

        session.execute(query);
        System.out.println("createTableTweetsByCountry --end\n");
    }



    public void inserttweet(Tweet tweet) {
        System.out.println("inserttweet --init");
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
        System.out.println(sb);
        session.execute(query);
        System.out.println("inserttweet --end");
    }

    public void inserttweetByCountry(Tweet tweet) {
        System.out.println("insertTweetByCountry --init");
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
        System.out.println(sb);
        session.execute(query);
        System.out.println("insertTweetByCountry --end\n");
    }




    public List<Tweet> selectAll() {
        System.out.println("select all --init");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);
        final String query = sb.toString();
        System.out.println(sb);
        ResultSet rs = session.execute(query);
        List <Tweet> tweets = new ArrayList<Tweet>();
        for (Row r:rs) {
            System.out.println(r);
            GeoLocation geo = new GeoLocation(r.getFloat("geo_location_latitude"),r.getFloat("geo_location_longitude"));
            Tweet s = new Tweet(r.getLong("id"),r.getString("username"),r.getString("text"),
                    r.getTimestamp("created_at"),r.getString("source"),r.getBool("isTruncated"),r.getBool("isFavourite"),
                    geo,r.getString("lang"),null,r.getString("country"));
            tweets.add(s);
        }
        System.out.println("select all --end\n");
        return tweets;
    }

    public List<Tweet> selectAllFromByCountry() {
        System.out.println("selectAllFromByCountry--init");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_BY_COUNTRY);
        final String query = sb.toString();
        System.out.println(sb);
        ResultSet rs = session.execute(query);
        List <Tweet> tweets = new ArrayList<Tweet>();
        for (Row r:rs) {
            System.out.println(r);
            GeoLocation geo = new GeoLocation(r.getFloat("geo_location_latitude"),r.getFloat("geo_location_longitude"));
            Tweet s = new Tweet(r.getLong("id"),r.getString("username"),r.getString("text"),
                    r.getTimestamp("created_at"),r.getString("source"),r.getBool("isTruncated"),r.getBool("isFavourite"),
                    geo,r.getString("lang"),null,r.getString("country"));
            tweets.add(s);
        }
        System.out.println("selectAllFromByCountry --end\n");
        return tweets;
    }

    public List<Tweet> selectByCountry(String country) {
        System.out.println("selectByCountry --init");
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_BY_COUNTRY).append(" WHERE country='").append(country).append("' ALLOW FILTERING");
        final String query = sb.toString();
        System.out.println(sb);
        ResultSet rs = session.execute(query);
        List <Tweet> tweets = new ArrayList<Tweet>();
        for (Row r:rs) {
            System.out.println(r);
            GeoLocation geo = new GeoLocation(r.getFloat("geo_location_latitude"),r.getFloat("geo_location_longitude"));
            Tweet s = new Tweet(r.getLong("id"),r.getString("username"),r.getString("text"),
                    r.getTimestamp("created_at"),r.getString("source"),r.getBool("isTruncated"),r.getBool("isFavourite"),
                    geo,r.getString("lang"),null,r.getString("country"));
            tweets.add(s);
        }
        System.out.println("selectByCountry --end\n");
        return tweets;
    }


    public void deletetweet(UUID id) {
        System.out.println("deleteTweet --init");
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME).append(" WHERE id = ").append(id).append(";");
        final String query = sb.toString();
        System.out.println(sb);
        session.execute(query);
        System.out.println("deleteTweet --end\n");
    }

    public void deletetweetByCountry(UUID id,String country) {
        System.out.println("delete tweetByCountry --init");
        StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME_BY_COUNTRY).append(" WHERE id = ").append(id).append(" AND country = '").append(country).append("';");
        final String query = sb.toString();
        System.out.println(sb);
        session.execute(query);
        System.out.println("delete tweetByCountry --end\n");
    }

    public void deleteTable(String tableName) {
        System.out.println("deleteTable --init");
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName);
        final String query = sb.toString();
        System.out.println(sb);
        session.execute(query);
        System.out.println("deleteTable --end\n");
    }
}
