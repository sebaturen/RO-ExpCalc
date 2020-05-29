package com.ro;

import com.ro.models.Account;

import java.util.HashMap;
import java.util.Map;

public class ROObjectController {

    public static ROObjectController shared = new ROObjectController();

    // Accounts map: [Account ID, Account info]
    private Map<Long, Account> accounts = new HashMap<>();

    public ROObjectController() {

        // Current exp/hour info
        new Thread(() -> {
            while (true) {
                for (Map.Entry<Long, Account> ac : ROObjectController.shared.getAccounts().entrySet()) {
                    main.mainPanel.setAccountId(ac.getValue().getId());
                    main.mainPanel.setExpHour(ac.getValue().getBaseExp());
                    main.mainPanel.setExpHour(ac.getValue().getJobExp());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean hasAccount(Long id) {
        return accounts.containsKey(id);
    }

    public Account getAccount(Long id) {
        return accounts.get(id);
    }

    public Map<Long, Account> getAccounts() {
        return this.accounts;
    }

    public void addAccount(Long id, Account a) {
        accounts.put(id, a);
    }

}
