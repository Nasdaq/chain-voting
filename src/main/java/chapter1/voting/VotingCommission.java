package chapter1.voting;

import com.chain.api.Account;
import com.chain.api.MockHsm;
import com.chain.api.Transaction;
import com.chain.api.UnspentOutput;
import com.chain.exception.ChainException;
import com.chain.http.Client;
import com.chain.signing.HsmSigner;

public class VotingCommission {
  private static final String BLOCKCHAIN_URL = "https://aviete.nadqchain.com";
  private static final String BLOCKCHAIN_TOKEN = null;

  public static void main(String[] args) throws ChainException {
    final Client client = new Client(BLOCKCHAIN_URL, BLOCKCHAIN_TOKEN);

    initializeSigner(client, "VotingSystem");

    Account.Items checkingAccounts = new Account.QueryBuilder()
        .setFilter("tags.type=$1")
        .addFilterParameter("voter")
        .execute(client);

    while (checkingAccounts.hasNext()) {
      Account account = checkingAccounts.next();
      issue(client, "vote", 1, account.alias);
    }

    printUnspentOutputs(client);
  }

  public static MockHsm.Key getKey(final Client client, final String alias) throws ChainException {
    final MockHsm.Key.Items keys = new MockHsm.Key.QueryBuilder().execute(client);
    for (final MockHsm.Key key: keys.list){
      if (alias.equals(key.alias)) {
        return key;
      }
    }
    return null;
  }

  public static void initializeSigner(final Client client, final String alias) throws ChainException {
    final MockHsm.Key key = getKey(client, alias);
    HsmSigner.addKey(key, MockHsm.getSignerClient(client));
  }

  public static void issue(final Client client,
      final String assetAlias,
      final int assetAmount,
      final String toAccountAlias) throws ChainException {
    System.out.println("\nTransfering "+assetAmount + " of " + assetAlias + " to account " + toAccountAlias);
    Transaction.Template spending = new Transaction.Builder()
        .addAction(new Transaction.Action.Issue()
            .setAssetAlias(assetAlias)
            .setAmount(assetAmount))
        .addAction(new Transaction.Action.ControlWithAccount()
            .setAccountAlias(toAccountAlias)
            .setAssetAlias(assetAlias)
            .setAmount(assetAmount)
        ).build(client);

    Transaction.submit(client, HsmSigner.sign(spending));
  }

  public static void printUnspentOutputs(final Client client) throws ChainException {
    final UnspentOutput.Items unspentOutputs = new UnspentOutput.QueryBuilder().execute(client);
    System.out.println("\nUnspent Outputs:");
    for (final UnspentOutput unspentOutput: unspentOutputs.list){
      System.out.println("\t" + unspentOutput.accountAlias+": "+unspentOutput.assetAlias + ": " + unspentOutput.amount + " purpose: " + unspentOutput.purpose);
    }
  }
}

