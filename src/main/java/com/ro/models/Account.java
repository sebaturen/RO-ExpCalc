package com.ro.models;

import com.ro.enums.ExpTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private long id;
    private int port;
    private long characterLogonId;
    private List<Character> characters = new ArrayList<>();

    public Account(int port) {
        this.port = port;
    }

    public Account(long id) {
        this.id = id;
    }

    //------------------------
    // GET / SET
    //------------------------
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addCharacter(Character c) {
        this.characters.add(c);
    }

    public void setPortCharacter(long cId, int port) {
        for(Character cr : characters) {
            if (cr.getId() == cId) {
                cr.setPort(port);
            } else {
                cr.setPort(0);
            }
        }
    }

    public boolean hasCharacter(long cId) {
        for(Character cr : characters) {
            if (cr.getId() == cId) {
                return true;
            }
        }
        return false;
    }

    public Character getCharacter(int port) {
        for (Character ch : characters) {
            if (ch.getPort() == port) {
                return ch;
            }
        }
        return null;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getCharacterLogonId() {
        return characterLogonId;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacterLogon(long characterLogon) {
        this.characterLogonId = characterLogon;
    }

    public Character getCharacterLogon() {
        for(Character ch : characters) {
            if (ch.getId() == this.characterLogonId) {
                return ch;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", characters=" + characters +
                '}';
    }
}
