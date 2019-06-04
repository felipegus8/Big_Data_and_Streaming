import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import java.io.Serializable;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Properties;


public class TweetManager implements LifecycleManager, Serializable {

    String _consumerKey = System.getenv().get("TWITTER_CONSUMER_KEY");
    String _consumerSecret = System.getenv().get("TWITTER_CONSUMER_SECRET");
    String _accessToken = System.getenv().get("TWITTER_ACCESS_TOKEN");
    String _accessTokenSecret = System.getenv().get("TWITTER_ACCESS_TOKEN_SECRET");
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    TwitterStream twitterStream;
    private static final Logger logger = Logger.getLogger(TweetCollectorResource.class.getName());
    
    private KafkaProducer<String, String> Producer;

    StatusListener listener = new StatusListener() {
        public void onStatus(Status status) {
            Tweet newTweet = new Tweet(status.getUser().getName(), status.getText(), status.getCreatedAt());
            logger.log(Level.INFO, newTweet.getUsername() + " : " + newTweet.getTweetContent() + ":" + newTweet.getTweetdate());
            ProducerRecord<String, String> Record = new ProducerRecord<String, String>
                    ("twitter-topic", "@" + newTweet.getUsername() + ":" + " " + newTweet.getTweetContent());
            Producer.send(Record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        System.out.println("Succesfully sent to Kafka");
                    } else
                        System.out.println("Error sending to Kafka: " + e);
                }
            });
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
        configProducer();
        configurationBuilder.setOAuthConsumerKey(_consumerKey).setOAuthConsumerSecret(_consumerSecret).setOAuthAccessToken(_accessToken).setOAuthAccessTokenSecret(_accessTokenSecret);
        this.configurationBuilder.setDebugEnabled(true);
        TwitterStreamFactory tf = new TwitterStreamFactory(this.configurationBuilder.build());
        this.twitterStream = tf.getInstance();
        this.twitterStream.addListener(listener);
        this.twitterStream.filter(getQuery());
    }

    public void stop() {
       this.twitterStream.shutdown();
       Producer.close();
    }

    public FilterQuery getQuery() {
       String trackedTerms = "federer,messi";
       FilterQuery query = new FilterQuery();
       query.track(trackedTerms.split(","));
       return query;
    }
    
    private void configProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer = new KafkaProducer<String, String>(properties);
    }

}

