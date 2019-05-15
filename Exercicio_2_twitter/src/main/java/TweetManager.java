import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.Serializable;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TweetManager implements LifecycleManager, Serializable {

    String _consumerKey = System.getenv().get("TWITTER_CONSUMER_KEY");
    String _consumerSecret = System.getenv().get("TWITTER_CONSUMER_SECRET");
    String _accessToken = System.getenv().get("TWITTER_ACCESS_TOKEN");
    String _accessTokenSecret = System.getenv().get("TWITTER_ACCESS_TOKEN_SECRET");
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    TwitterStream twitterStream;
    private static final Logger logger = Logger.getLogger(TweetCollectorResource.class.getName());
    StatusListener listener = new StatusListener(){
        public void onStatus(Status status) {
            Tweet newTweet = new Tweet(status.getUser().getName(),status.getText(),status.getCreatedAt());
            logger.log(Level.INFO,newTweet.getUsername() + " : " + newTweet.getTweetContent() + ":" + newTweet.getTweetdate());

        }
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
        public void onException(Exception ex) {
            ex.printStackTrace();
        }
        public void onScrubGeo(long l, long l1) {}
        public void onStallWarning(StallWarning stallWarning) {}
    };

    public void start() {
        System.out.println("Chamou a classe");
        configurationBuilder.setOAuthConsumerKey(_consumerKey).setOAuthConsumerSecret(_consumerSecret).setOAuthAccessToken(_accessToken).setOAuthAccessTokenSecret(_accessTokenSecret);
        this.configurationBuilder.setDebugEnabled(true);
        TwitterStreamFactory tf = new TwitterStreamFactory(this.configurationBuilder.build());
        this.twitterStream = tf.getInstance();
        this.twitterStream.addListener(listener);
        this.twitterStream.filter(getQuery());
    }

    public void stop() {
       this.twitterStream.shutdown();

    }

    public FilterQuery getQuery() {
       String trackedTerms = "federer,messi";
       FilterQuery query = new FilterQuery();
       query.track(trackedTerms.split(","));
       return query;
    }
}

