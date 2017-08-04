package services;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import play.Logger;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * ES client connected to a Holidu's elasticsearch instance,
 * Please be gentle with her.
 * <p>
 * Technically you should not need to change anything here
 *
 * @version 1.0.0
 */
public class ElasticsearchService {

    private static final String CLUSTER_NAME = "holidu-interview";
    private static final String NODE_IP = "52.214.199.196";
    private static final int PORT = 9300;

    private static Client client;
    private static int random;

    static {
        Settings.Builder settingsBuilder = Settings.builder()
                .put("cluster.name", CLUSTER_NAME);

        Settings settings = settingsBuilder.build();

        TransportClient transportClient = new PreBuiltTransportClient(settings);

        TransportAddress transportAddress = null;

        try {
            transportAddress = new InetSocketTransportAddress(InetAddress.getByName(NODE_IP), PORT);
        } catch (UnknownHostException e) {
            Logger.error(e.getMessage(), e);
        }

        transportClient.addTransportAddress(transportAddress);

        for (DiscoveryNode node : transportClient.connectedNodes()) {
            Logger.info("Connected to node " + node);
        }

        client = transportClient;
        random = new Random().nextInt();
        Logger.info("Created ElasticsearchService " + random);
    }

    @Inject
    public ElasticsearchService(ApplicationLifecycle lifecycle) {
        lifecycle.addStopHook(() -> {
            ElasticsearchService.shutdown();
            return null;
        });
    }

    public static Client get() {
        Logger.info("Getting client from " + random);
        return client;
    }

    private static void shutdown() {
        Logger.info("Setting client to null");
        client = null;
    }
}
