package com.ro;

import com.ro.models.Account;
import com.ro.models.Character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ROObjectController {

    public static ROObjectController shared = new ROObjectController();

    // Accounts map: [Port, Account info]
    private List<Account> accounts = new ArrayList<>();

    public ROObjectController() {
        // Current exp/hour info
        new Thread(() -> {
            while (true) {
                for (Account ac : accounts) {
                    main.mainPanel.setAccount(ac);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean hasAccount(int port) {
        for(Account ac : accounts) {
            if (ac.getPort() == port) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAccount(long acId) {
        for(Account ac : accounts) {
            if (ac.getId() == acId) {
                return true;
            }
        }
        return false;
    }

    public Account getAccount(int port) {
        for(Account ac : accounts) {
            if (ac.getPort() == port) {
                return ac;
            }
        }
        return null;
    }

    public Account getAccount(long acId) {
        for(Account ac : accounts) {
            if (ac.getId() == acId) {
                return ac;
            }
        }
        return null;
    }


    public void addAccount(Account ac) {
        accounts.add(ac);
    }

    public Character getCharacter(int port) {
        for(Account ac : accounts) {
            for(Character ch : ac.getCharacters()) {
                if(ch.getPort() == port) {
                    return ch;
                }
            }
        }
        return null;
    }

}
