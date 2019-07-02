# Big_Data_and_Streaming

/usr/java/jdk-9/bin/java -javaagent:/opt/idea-IC-182.4129.33/lib/idea_rt.jar=44557:/opt/idea-IC-182.4129.33/bin -Dfile.encoding=UTF-8 -classpath /home/treinamento/Downloads/tweet_cassandra/target/classes:/home/treinamento/.m2/repository/com/datastax/cassandra/cassandra-driver-core/3.7.1/cassandra-driver-core-3.7.1.jar:/home/treinamento/.m2/repository/io/netty/netty-handler/4.0.56.Final/netty-handler-4.0.56.Final.jar:/home/treinamento/.m2/repository/io/netty/netty-buffer/4.0.56.Final/netty-buffer-4.0.56.Final.jar:/home/treinamento/.m2/repository/io/netty/netty-common/4.0.56.Final/netty-common-4.0.56.Final.jar:/home/treinamento/.m2/repository/io/netty/netty-transport/4.0.56.Final/netty-transport-4.0.56.Final.jar:/home/treinamento/.m2/repository/io/netty/netty-codec/4.0.56.Final/netty-codec-4.0.56.Final.jar:/home/treinamento/.m2/repository/com/google/guava/guava/19.0/guava-19.0.jar:/home/treinamento/.m2/repository/io/dropwizard/metrics/metrics-core/3.2.2/metrics-core-3.2.2.jar:/home/treinamento/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar:/home/treinamento/.m2/repository/com/github/jnr/jnr-ffi/2.1.7/jnr-ffi-2.1.7.jar:/home/treinamento/.m2/repository/com/github/jnr/jffi/1.2.16/jffi-1.2.16.jar:/home/treinamento/.m2/repository/org/ow2/asm/asm/5.0.3/asm-5.0.3.jar:/home/treinamento/.m2/repository/org/ow2/asm/asm-commons/5.0.3/asm-commons-5.0.3.jar:/home/treinamento/.m2/repository/org/ow2/asm/asm-analysis/5.0.3/asm-analysis-5.0.3.jar:/home/treinamento/.m2/repository/org/ow2/asm/asm-tree/5.0.3/asm-tree-5.0.3.jar:/home/treinamento/.m2/repository/org/ow2/asm/asm-util/5.0.3/asm-util-5.0.3.jar:/home/treinamento/.m2/repository/com/github/jnr/jnr-x86asm/1.0.2/jnr-x86asm-1.0.2.jar:/home/treinamento/.m2/repository/com/github/jnr/jnr-posix/3.0.44/jnr-posix-3.0.44.jar:/home/treinamento/.m2/repository/com/github/jnr/jnr-constants/0.9.9/jnr-constants-0.9.9.jar:/home/treinamento/.m2/repository/org/twitter4j/twitter4j-stream/4.0.6/twitter4j-stream-4.0.6.jar:/home/treinamento/.m2/repository/org/twitter4j/twitter4j-core/4.0.6/twitter4j-core-4.0.6.jar HelloTweet
Hello World
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
3.11.4
create Keyspace --init
CREATE KEYSPACE IF NOT EXISTS twitter WITH replication = {'class':'SimpleStrategy','replication_factor':1};
create Keyspace --end 

use Keyspace --init 
use Keyspace --end

createTable --init
CREATE TABLE IF NOT EXISTS tweets(id uuid PRIMARY KEY, username text,text text,created_at date,source text,isTruncated boolean,isFavourite boolean,geo_location_latitude float,geo_location_longitude float,lang text,country text,contributors list<float>);
createTable --end

createTableTweetsByCountry --init
CREATE TABLE IF NOT EXISTS tweetsByCountry(id uuid, username text,text text,created_at date,source text,isTruncated boolean,isFavourite boolean,geo_location_latitude float,geo_location_longitude float,lang text,country text,contributors list<float>, PRIMARY KEY((id,country)));
createTableTweetsByCountry --end

inserttweet --init
INSERT INTO tweets (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d4ce950-9cc6-11e9-996e-6391e00efab8, 'User 1', 'Teste 1', '2019-07-20', 'null', true, true, 20.0, 20.0, 'null', 'Brazil', null);
inserttweet --end
insertTweetByCountry --init
INSERT INTO tweetsByCountry (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d4ce950-9cc6-11e9-996e-6391e00efab8, 'User 1', 'Teste 1', '2019-07-20', 'null', true, true, 20.0, 20.0, 'null', 'Brazil', null);
insertTweetByCountry --end

inserttweet --init
INSERT INTO tweets (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d4dfac0-9cc6-11e9-996e-6391e00efab8, 'User 2', 'Teste 2', '2019-07-21', 'null', false, false, 30.0, 30.0, 'null', 'USA', null);
inserttweet --end
insertTweetByCountry --init
INSERT INTO tweetsByCountry (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d4dfac0-9cc6-11e9-996e-6391e00efab8, 'User 2', 'Teste 2', '2019-07-21', 'null', false, false, 30.0, 30.0, 'null', 'USA', null);
insertTweetByCountry --end

inserttweet --init
INSERT INTO tweets (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d4f8160-9cc6-11e9-996e-6391e00efab8, 'User 3', 'Teste 3', '2019-07-22', 'null', true, true, 40.0, 40.0, 'null', 'Italy', null);
inserttweet --end
insertTweetByCountry --init
INSERT INTO tweetsByCountry (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d4f8160-9cc6-11e9-996e-6391e00efab8, 'User 3', 'Teste 3', '2019-07-22', 'null', true, true, 40.0, 40.0, 'null', 'Italy', null);
insertTweetByCountry --end

inserttweet --init
INSERT INTO tweets (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d501da0-9cc6-11e9-996e-6391e00efab8, 'User 4', 'Teste 4', '2019-07-23', 'null', true, true, 50.0, 50.0, 'null', 'Brazil', null);
inserttweet --end
insertTweetByCountry --init
INSERT INTO tweetsByCountry (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d501da0-9cc6-11e9-996e-6391e00efab8, 'User 4', 'Teste 4', '2019-07-23', 'null', true, true, 50.0, 50.0, 'null', 'Brazil', null);
insertTweetByCountry --end

inserttweet --init
INSERT INTO tweets (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d510800-9cc6-11e9-996e-6391e00efab8, 'User 5', 'Teste 5', '2019-07-24', 'null', true, true, 60.0, 60.0, 'null', 'USA', null);
inserttweet --end
insertTweetByCountry --init
INSERT INTO tweetsByCountry (id,username,text,created_at,source,isTruncated,isFavourite,geo_location_latitude,geo_location_longitude,lang,country,contributors) VALUES ( 5d510800-9cc6-11e9-996e-6391e00efab8, 'User 5', 'Teste 5', '2019-07-24', 'null', true, true, 60.0, 60.0, 'null', 'USA', null);
insertTweetByCountry --end

select all --init
SELECT * FROM tweets
Row[5d501da0-9cc6-11e9-996e-6391e00efab8, NULL, Brazil, 2019-07-23, 50.0, 50.0, true, true, null, null, Teste 4, User 4]
Row[5d4dfac0-9cc6-11e9-996e-6391e00efab8, NULL, USA, 2019-07-21, 30.0, 30.0, false, false, null, null, Teste 2, User 2]
Row[5d4ce950-9cc6-11e9-996e-6391e00efab8, NULL, Brazil, 2019-07-20, 20.0, 20.0, true, true, null, null, Teste 1, User 1]
Row[5d4f8160-9cc6-11e9-996e-6391e00efab8, NULL, Italy, 2019-07-22, 40.0, 40.0, true, true, null, null, Teste 3, User 3]
Row[5d510800-9cc6-11e9-996e-6391e00efab8, NULL, USA, 2019-07-24, 60.0, 60.0, true, true, null, null, Teste 5, User 5]
select all --end

selectAllFromByCountry--init
SELECT * FROM tweetsByCountry
Row[5d4ce950-9cc6-11e9-996e-6391e00efab8, Brazil, NULL, 2019-07-20, 20.0, 20.0, true, true, null, null, Teste 1, User 1]
Row[5d510800-9cc6-11e9-996e-6391e00efab8, USA, NULL, 2019-07-24, 60.0, 60.0, true, true, null, null, Teste 5, User 5]
Row[5d4f8160-9cc6-11e9-996e-6391e00efab8, Italy, NULL, 2019-07-22, 40.0, 40.0, true, true, null, null, Teste 3, User 3]
Row[5d501da0-9cc6-11e9-996e-6391e00efab8, Brazil, NULL, 2019-07-23, 50.0, 50.0, true, true, null, null, Teste 4, User 4]
Row[5d4dfac0-9cc6-11e9-996e-6391e00efab8, USA, NULL, 2019-07-21, 30.0, 30.0, false, false, null, null, Teste 2, User 2]
selectAllFromByCountry --end

selectByCountry --init
SELECT * FROM tweetsByCountry WHERE country='USA' ALLOW FILTERING
Row[5d510800-9cc6-11e9-996e-6391e00efab8, USA, NULL, 2019-07-24, 60.0, 60.0, true, true, null, null, Teste 5, User 5]
Row[5d4dfac0-9cc6-11e9-996e-6391e00efab8, USA, NULL, 2019-07-21, 30.0, 30.0, false, false, null, null, Teste 2, User 2]
selectByCountry --end

deleteTweet --init
DELETE FROM tweets WHERE id = 5d4ce950-9cc6-11e9-996e-6391e00efab8;
deleteTweet --end

select all --init
SELECT * FROM tweets
Row[5d501da0-9cc6-11e9-996e-6391e00efab8, NULL, Brazil, 2019-07-23, 50.0, 50.0, true, true, null, null, Teste 4, User 4]
Row[5d4dfac0-9cc6-11e9-996e-6391e00efab8, NULL, USA, 2019-07-21, 30.0, 30.0, false, false, null, null, Teste 2, User 2]
Row[5d4f8160-9cc6-11e9-996e-6391e00efab8, NULL, Italy, 2019-07-22, 40.0, 40.0, true, true, null, null, Teste 3, User 3]
Row[5d510800-9cc6-11e9-996e-6391e00efab8, NULL, USA, 2019-07-24, 60.0, 60.0, true, true, null, null, Teste 5, User 5]
select all --end

delete tweetByCountry --init
DELETE FROM tweetsByCountry WHERE id = 5d4dfac0-9cc6-11e9-996e-6391e00efab8 AND country = 'USA';
delete tweetByCountry --end

selectAllFromByCountry--init
SELECT * FROM tweetsByCountry
Row[5d4ce950-9cc6-11e9-996e-6391e00efab8, Brazil, NULL, 2019-07-20, 20.0, 20.0, true, true, null, null, Teste 1, User 1]
Row[5d510800-9cc6-11e9-996e-6391e00efab8, USA, NULL, 2019-07-24, 60.0, 60.0, true, true, null, null, Teste 5, User 5]
Row[5d4f8160-9cc6-11e9-996e-6391e00efab8, Italy, NULL, 2019-07-22, 40.0, 40.0, true, true, null, null, Teste 3, User 3]
Row[5d501da0-9cc6-11e9-996e-6391e00efab8, Brazil, NULL, 2019-07-23, 50.0, 50.0, true, true, null, null, Teste 4, User 4]
selectAllFromByCountry --end

deleteTable --init
DROP TABLE IF EXISTS tweets
deleteTable --end

deleteTable --init
DROP TABLE IF EXISTS tweetsByCountry
deleteTable --end

delete Keyspace --init
DROP KEYSPACE twitter
delete Keyspace --end


Process finished with exit code 0
