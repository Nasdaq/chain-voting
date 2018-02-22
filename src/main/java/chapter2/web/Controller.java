package chapter2.web;

import com.chain.exception.ChainException;
import com.chain.http.Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
final public class Controller {
    @RequestMapping("/responses")
    public List<Response> responses()  throws ChainException {
        final Client client = ChainClient.getInstance().getClient();
        return ChainStatus.getUnspentOutputs(client);
    }
}
