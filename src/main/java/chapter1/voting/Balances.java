package chapter1.voting;

import com.chain.api.Account;
import com.chain.api.Balance;
import com.chain.exception.ChainException;
import com.chain.http.Client;
import java.util.Collections;
import static chapter1.voting.VotingCommission.*;

public final class Balances {

    public static void main(String[] args) throws ChainException {
        final Client client = new Client(BLOCKCHAIN_URL, BLOCKCHAIN_TOKEN);

        Account.Items checkingAccounts = new Account.QueryBuilder()
                .setFilter("tags.type=$1")
                .addFilterParameter("results")
                .execute(client);

        while (checkingAccounts.hasNext()) {
            Account account = checkingAccounts.next();
            printAccountBalance(client, account.alias);
        }
    }

    public static void printAccountBalance(Client client, String accountAlias) throws ChainException {
        Balance.Items balances = new Balance.QueryBuilder()
                .setFilter("account_alias=$1")
                .setFilterParameters(Collections.singletonList(accountAlias))
                .execute(client);

        while (balances.hasNext()) {
            Balance balance = balances.next();
            balance.sumBy.get("asset_alias");
            System.out.println(accountAlias + ": " + balance.amount);
        }
    }
}
