import com.datastax.driver.core.LocalDate;

import java.util.Date;
import java.util.UUID;
import com.datastax.driver.core.LocalDate;
import sun.reflect.generics.scope.Scope;
import twitter4j.*;

import javax.jws.soap.SOAPBinding;

public class Tweet {
    private UUID id;
    private String username;
    private String text;
    private LocalDate created_at;
    private String source;
    private boolean isTruncated;
    private GeoLocation geoLocation;
    private boolean isFavorited;
    private boolean isRetweeted;
    private int favoriteCount;
    private boolean isRetweet;
    private long[] contributors;
    private int retweetCount;
    private String lang;
    private String country;

    public Tweet(UUID id,String username, String text, LocalDate created_at) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.created_at = created_at;
    }

    public Tweet(UUID id,String username, String text, LocalDate created_at,String source, boolean isTruncated, boolean isFavorited,GeoLocation geoLocation,String lang, long[] contributors,String country) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.created_at = created_at;
        this.source = source;
        this.isTruncated = isTruncated;
        this.isFavorited = isFavorited;
        this.geoLocation = geoLocation;
        this.lang = lang;
        this.contributors = contributors;
        this.country = country;
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


    public String getText() {
        return text;
    }
    public String getCountry() {
        return country;
    }
    public LocalDate getCreated_at() {
        return created_at;
    }

    public String getSource() {
        return source;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }


    public long[] getContributors() {
        return contributors;
    }

    public String getLang() {
        return lang;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }


    public void setSource(String source) {
        this.source = source;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }


    public void setTruncated(boolean truncated) {
        isTruncated = truncated;
    }

    public void setContributors(long[] contributors) {
        this.contributors = contributors;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }



    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public void setRetweeted(boolean retweeted) {
        isRetweeted = retweeted;
    }
    public void setCountry(String country) {
        this.country=country;
}
    public boolean isFavorited() {
        return isFavorited;
    }

    public boolean isRetweet() {
        return isRetweet;
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public String toString() {
        return "@" + this.username + ": " + this.text;
    }
}