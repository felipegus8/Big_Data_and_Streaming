import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import com.datastax.driver.core.*;
import com.datastax.driver.core.utils.UUIDs;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import twitter4j.GeoLocation;
import twitter4j.Place;

public class ConsumerManager implements LifecycleManager,Serializable {

    private static Logger logger = LoggerFactory.getLogger(ConsumerManager.class.getName());
    private boolean isConsuming = false;
    private Thread ConsumerThread;

    public void start() {
        // Criar as propriedades do consumidor
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TweetDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "consumer_demo");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        isConsuming = true;

        // Criar o consumidor
        KafkaConsumer<String ,Tweet> consumer = new KafkaConsumer<String, Tweet>(properties);

        // Subscrever o consumidor para o nosso(s) tópico(s)
        consumer.subscribe(Collections.singleton("twitter-topic"));

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
        } finally {
            if (cluster != null) cluster.close();
        }

        // Ler as mensagens
        ConsumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isConsuming) {
                    ConsumerRecords<String, Tweet> poll = consumer.poll(Duration.ofMillis(10000));
                    for (ConsumerRecord record : poll) {
                        System.out.println("Entrou aqui");
                        Tweet readTweet = Tweet.class.cast(record.value());
                        logger.info(record.topic() + " - " + record.partition() + " - " + record.value());
                    }

                }
            }
        });
        ConsumerThread.start();
    }


    public void stop() {
        this.isConsuming = false;

    }
}