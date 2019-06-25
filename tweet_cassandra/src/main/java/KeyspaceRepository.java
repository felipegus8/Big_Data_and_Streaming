import com.datastax.driver.core.Session;
public class KeyspaceRepository {
    private Session session;
    public KeyspaceRepository(Session session) {
        this.session = session;
    }

    public void createKeyspace(String keyspaceName, String replicationStrategy,int numberOfReplicas) {
        System.out.println("Started create Keyspace");
        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append(keyspaceName).append(" WITH replication = {").append("'class':'")
                .append(replicationStrategy)
                .append("','replication_factor':")
                .append(numberOfReplicas).append("};");
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished create Keyspace");
    }

    public void useKeyspace(String keyspace) {
        System.out.println("Started use Keyspace");
        session.execute("USE " + keyspace);
        System.out.println("Finished use Keyspace");
    }

    public void deleteKeyspace(String keyspaceName) {
        System.out.println("Started delete Keyspace");
        StringBuilder sb = new StringBuilder("DROP KEYSPACE ").append(keyspaceName);
        final String query = sb.toString();
        session.execute(query);
        System.out.println("Finished delete Keyspace");
    }
}
