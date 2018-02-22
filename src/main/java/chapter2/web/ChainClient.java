package chapter2.web;

import com.chain.exception.ChainException;
import com.chain.http.Client;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

final public class ChainClient {
    private static final String BLOCKCHAIN_URL = "https://aviete.nadqchain.com";
    private static final String BLOCKCHAIN_TOKEN = "client:e06c7d66f2f88853e5f2e3ed60b422a2d33655768425ba88013a42dc9cd4b812";

    private static ChainClient instance = null;
    private static Client      client = null;

    protected ChainClient() {
        try {
            client = new Client(BLOCKCHAIN_URL, BLOCKCHAIN_TOKEN);
            if (System.getenv().get("http_proxy") != null) {
                URL proxy = new URL(System.getenv().get("http_proxy"));
                client.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
                    proxy.getHost(), proxy.getPort())));
            }
        } catch (final ChainException ce) {
            ce.printStackTrace();
        } catch (final MalformedURLException me) {
            me.printStackTrace();
        }
    }

    public static ChainClient getInstance() {
        if(instance == null) {
            instance = new ChainClient();
        }
        return instance;
    }

    public Client getClient() {
        return client;
    }

}
