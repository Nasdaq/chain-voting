package chapter1.voting;

import com.chain.api.Transaction;
import com.chain.exception.ChainException;
import com.chain.http.Client;
import com.chain.signing.HsmSigner;

import static chapter1.voting.VotingCommission.*;

public final class Vote {

    public static void main(String[] args) throws ChainException {
        final Client client = new Client(BLOCKCHAIN_URL, BLOCKCHAIN_TOKEN);

        initializeSigner(client, "VotingSystem");

        vote(client, "vote", 1, "AccountA", "AccountB");

        printUnspentOutputs(client);
    }

    public static void vote(final Client client,
                             final String assetAlias,
                             final int assetAmount,
                             final String fromAccountAlias,
                             final String toAccountAlias) throws ChainException {
        System.out.println("\nSpending "+assetAmount + " of " + assetAlias + " from account " + fromAccountAlias + " to account " + toAccountAlias);
        Transaction.Template spending = new Transaction.Builder()
                .addAction(new Transaction.Action.SpendFromAccount()
                        .setAssetAlias(assetAlias)
                        .setAccountAlias(fromAccountAlias)
                        .setAmount(assetAmount))
                .addAction(new Transaction.Action.ControlWithAccount()
                        .setAccountAlias(toAccountAlias)
                        .setAssetAlias(assetAlias)
                        .setAmount(assetAmount)
                ).build(client);

        Transaction.submit(client, HsmSigner.sign(spending));
    }
}
