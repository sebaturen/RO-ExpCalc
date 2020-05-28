import models.Account;

import java.util.List;

public class ROObjectController {

    private List<Account> accounts;

    public ROObjectController() {

    }

    public Account getAccount(int id) {
        return accounts.get(id);
    }

    public void addAccount(Account a) {
        accounts.add(a.getId(), a);
    }

}
