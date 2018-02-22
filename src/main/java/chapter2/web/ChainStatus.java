package chapter2.web;

import com.chain.api.Account;
import com.chain.api.Asset;
import com.chain.api.Balance;
import com.chain.api.MockHsm;
import com.chain.api.UnspentOutput;
import com.chain.exception.ChainException;
import com.chain.http.Client;

import java.util.ArrayList;
import java.util.List;

final public class ChainStatus {

    public static void main(final String[] args) throws ChainException {
        final Client client = ChainClient.getInstance().getClient();
        printAccounts(client);
        printKeys(client);
        printAssets(client);
        printBalances(client);
        printUnspentOutputs(client);
    }


    public static void printAccounts(final Client client) throws ChainException {
        final Account.Items checkingAccounts = new Account.QueryBuilder().execute(client);
        System.out.println("\nAccounts:");
        for (final Account account: checkingAccounts.list){
            System.out.println("\t"+account.alias);
        }
    }

    public static void printKeys(final Client client) throws ChainException {
        final MockHsm.Key.Items keys = new MockHsm.Key.QueryBuilder().execute(client);
        System.out.println("\nKeys:");
        for (final MockHsm.Key key: keys.list){
            System.out.println("\t" +key.alias);
        }
    }

    public static void printAssets(final Client client) throws ChainException {
        final Asset.Items assets = new Asset.QueryBuilder()
                 .setFilter("tags.type=$1")
                 .addFilterParameter("vote")
                 .execute(client);
        System.out.println("\nAssets:");
        for (final Asset asset: assets.list){
            System.out.println("\t" +asset.alias);
        }
    }

    public static void printBalances(final Client client) throws ChainException {
        final Balance.Items balances = new Balance.QueryBuilder()
                .setFilter("asset_alias=$2")
                .addFilterParameter("vote")
                .execute(client);
        System.out.println("\nBalances:");
        for (final Balance balance: balances.list){
            System.out.println("\t" + balance.amount);
        }
    }

    public static void printUnspentOutputs(final Client client) throws ChainException {
        final UnspentOutput.Items unspentOutputs = new UnspentOutput.QueryBuilder().
                execute(client);
        System.out.println("\nUnspent Outputs:");
        for (final UnspentOutput unspentOutput: unspentOutputs.list){
            System.out.println("\t" + unspentOutput.accountAlias+": "+unspentOutput.assetAlias + ": " + unspentOutput.amount + " purpose: " + unspentOutput.purpose);
        }
    }

    public static List<Response> getUnspentOutputs(final Client client) throws ChainException {
        List<Response> responses = new ArrayList<Response>();
        final UnspentOutput.Items unspentOutputs = new UnspentOutput.QueryBuilder().execute(client);
        for (final UnspentOutput unspentOutput: unspentOutputs.list){
            responses.add(new Response(unspentOutput.accountAlias, unspentOutput.assetAlias,  unspentOutput.amount, unspentOutput.purpose));
        }
        return responses;
    }
}
