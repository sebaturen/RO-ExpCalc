package com.ro.network;

import com.ro.enums.ExpTypeEnum;
import com.ro.ROObjectController;
import com.ro.models.Account;
import com.ro.models.Character;

import java.math.BigInteger;
import java.util.*;

public class PacketDecryption {

    public PacketDecryption() {

    }

    /**
     * All packet is in reverse direction [mirror]
     */
    public void decryption(String packet, int port) {

        //System.out.println("[ALL] -> ("+ port +") "+ packet);
        // Exp Packet
        if (packet.contains(EXP_PACKET)) {
            newExp(packet, port);
        }

        // Total Exp Packet
        if (packet.contains(TOTAL_EXP_PACKET)) {
            newTotalExp(packet, port);
        }

        // Received Character
        if (packet.contains(RECEIVED_CHARACTERS_PACKET)) {
            receivedCharacter(packet, port);
        }

        // Received Character ID And Map
        if (packet.contains(RECEIVED_CHARACTER_ID_AND_MAP_PACKET)) {
            receivedCharacterIDAndMap(packet, port);
        }

        // Sync packet
        if (packet.startsWith(SYNC_REQUEST_PACKET)) {
            syncRequest(packet, port);
        }

        // Account ID
        if (packet.startsWith(ACCOUNT_ID_PACKET)) {
            accountId(packet, port);
        }

    }

    /**
     * 0A CC -> Exp packet [2]
     * xx xx xx xx -> Account ID [4]
     * zz zz zz zz zz zz zz zz -> Exp [8]
     * yy yy -> type (01 base | 02 job) [2]
     * size: 16
     * @param packet
     * @param port
     */
    private static final String EXP_PACKET = "CC 0A";
    private static final int EXP_PACKET_SIZE = 16;
    private void newExp(String packet, int port) {

        for (int index = packet.indexOf(EXP_PACKET);
             index >= 0;
             index = packet.indexOf(EXP_PACKET, index + 1))
        {
            if (packet.length() < index + (EXP_PACKET_SIZE*2)+(EXP_PACKET_SIZE-1)) {
                break;
            }

            String packContent = packet.substring(index, index + (EXP_PACKET_SIZE*2)+(EXP_PACKET_SIZE-1));
            String[] sepContent = packContent.split(" ");

            // Packet content
            List<String> accountIdList = Arrays.asList(Arrays.copyOfRange(sepContent, 2, 6));
            List<String> expList = Arrays.asList(Arrays.copyOfRange(sepContent, 6, 14));
            List<String> typeList = Arrays.asList(Arrays.copyOfRange(sepContent, 14, 16));
            // Reverse
            Collections.reverse(accountIdList);
            Collections.reverse(expList);
            Collections.reverse(typeList);

            // parse to info
            long acId = new BigInteger(String.join("", accountIdList), 16).longValue();
            long exp = new BigInteger(String.join("", expList), 16).longValue();
            ExpTypeEnum type = ExpTypeEnum.valueOf(new BigInteger(String.join("", typeList), 16).intValue());

            if (type != null && exp > 0) {

                // Check if pre-exist
                Account ac;
                if (ROObjectController.shared.hasAccount(acId)) {
                    ac = ROObjectController.shared.getAccount(acId);

                    Character ch = ac.getCharacterLogon();
                    ch.setPort(port);

                    switch (type) {
                        case JOB:
                            ch.getJobExp().setLastExp(exp);
                            break;
                        case BASE:
                            ch.getBaseExp().setLastExp(exp);
                            break;
                    }

                    System.out.println("Packet: "+ packet);
                    System.out.println("Char: "+ ch.getId());
                    System.out.println("Exp: "+ exp);
                    System.out.println("BASE Exp/Hour "+ ch.getBaseExp().getExpHour());
                    System.out.println("JOB Exp/Hour "+ ch.getJobExp().getExpHour());
                    System.out.println("Type: "+ type);
                    System.out.println("Lvl: ["+ ch.getBaseExp().getLvl() +"/"+ ch.getJobExp().getLvl() +"]");
                    System.out.println("---------");
                }
            }


        }
    }

    /**
     * 0A CB -> Total exp packet [2]
     * xx xx -> type (01 base | 02 job) [2]
     * zz zz zz zz zz zz zz zz -> Exp [8]
     * size: 12
     * @param packet
     * @param port
     */
    private static final String TOTAL_EXP_PACKET = "CB 0A";
    private static final int TOTAL_EXP_PACKET_SIZE = 12;
    private void newTotalExp(String packet, int port) {
        //System.out.println("[totalExp] port -> "+ port +" // "+ packet);
        for (int index = packet.indexOf(TOTAL_EXP_PACKET);
             index >= 0;
             index = packet.indexOf(TOTAL_EXP_PACKET, index + 1))
        {
            if (packet.length() < index + (TOTAL_EXP_PACKET_SIZE*2)+(TOTAL_EXP_PACKET_SIZE-1)) {
                break;
            }

            String packContent = packet.substring(index, index + (TOTAL_EXP_PACKET_SIZE*2)+(TOTAL_EXP_PACKET_SIZE-1));
            String[] sepContent = packContent.split(" ");

            // Packet content
            List<String> typeList = Arrays.asList(Arrays.copyOfRange(sepContent, 2, 4));
            List<String> expList = Arrays.asList(Arrays.copyOfRange(sepContent, 4, 12));
            Collections.reverse(typeList);
            Collections.reverse(expList);

            // parse to info
            ExpTypeEnum type = ExpTypeEnum.valueOf(new BigInteger(String.join("", typeList), 16).intValue());
            long exp = new BigInteger(String.join("", expList), 16).longValue();

            if (type != null && exp > 0) {

                // Check if pre-exist
                Character ch = ROObjectController.shared.getCharacter(port);
                if (ch != null) {
                    switch (type) {
                        case JOB:
                            ch.getJobExp().setTotalExp(exp);
                            break;
                        case BASE:
                            ch.getBaseExp().setTotalExp(exp);
                            break;
                    }
                    System.out.println("Type: "+ type);
                    System.out.println("Exp: "+ exp);
                    System.out.println("---------");
                }
            }

        }
    }

    /**
     * 09 9D -> Received Character [2]
     * [zz zz] -> Packet size [2]
     * [xx ... xx] -> Character information [155]
     *       -> [yy yy yy]  -> Character ID [3]
     *       -> [ii ... ii] -> unknown [21]
     *       -> [hh hh]     -> Job lvl [2]
     *       -> [jj ... jj] -> unknown [46]
     *       -> [kk kk]     -> Base lvl [2]
     *       -> [ll ... ll] -> unknown [81]
     * [... n-2 n-1 n] -> Other character information
     */
    private static final String RECEIVED_CHARACTERS_PACKET = "9D 09";
    private static final int RECEIVED_CHARACTERS_PACKET_SIZE = 155;
    private void receivedCharacter(String packet, int port) {

        //System.out.println("[allChars] port -> "+ port +" // "+ packet);
        // Remove packet identifier and size
        packet = packet.substring(12);
        int charSize = (RECEIVED_CHARACTERS_PACKET_SIZE*2)+(RECEIVED_CHARACTERS_PACKET_SIZE);
        String[] charactes = packet.split("(?<=\\G.{"+ charSize +"})");

        for(String character : charactes) {
            String[] charContent = character.split(" ");

            if (charContent.length < RECEIVED_CHARACTERS_PACKET_SIZE) {
                break;
            }

            List<String> charIdList = Arrays.asList(Arrays.copyOfRange(charContent, 0, 3));
            List<String> jobLvlList = Arrays.asList(Arrays.copyOfRange(charContent, 24, 26));
            List<String> baseLvlList = Arrays.asList(Arrays.copyOfRange(charContent, 72, 74));
            // Reverse
            Collections.reverse(charIdList);
            Collections.reverse(jobLvlList);
            Collections.reverse(baseLvlList);

            // parse to info
            long cId = new BigInteger(String.join("", charIdList), 16).longValue();
            long cJobLvl = new BigInteger(String.join("", jobLvlList), 16).longValue();
            long cBaseLvl = new BigInteger(String.join("", baseLvlList), 16).longValue();

            Account ac;
            if (ROObjectController.shared.hasAccount(port)) {
                ac = ROObjectController.shared.getAccount(port);
            } else {
                ac = new Account(port);
                ROObjectController.shared.addAccount(ac);
            }

            Character ch = new Character(cId);
            ch.getBaseExp().setLvl(cBaseLvl);
            ch.getJobExp().setLvl(cJobLvl);

            ac.addCharacter(ch);
            System.out.println(ch);

        }

    }

    /**
     * 00 71 -> Received character id and map [2]
     * xx xx xx -> Character ID [3]
     * ?? ... ?? -> Other info [23]
     * @param packet
     * @param port
     */
    private static final String RECEIVED_CHARACTER_ID_AND_MAP_PACKET = "71 00";
    private static final int RECEIVED_CHARACTER_ID_AND_MAP_PACKET_SIZE = 28;
    private void receivedCharacterIDAndMap(String packet, int port) {

        String[] sepContent = packet.split(" ");
        if (sepContent.length == 28) {

            // Packet content
            List<String> charLoginIdList = Arrays.asList(Arrays.copyOfRange(sepContent, 2, 5));
            // Reverse
            Collections.reverse(charLoginIdList);

            // Parse to info
            long charLoginId = new BigInteger(String.join("", charLoginIdList), 16).longValue();

            Account ac = ROObjectController.shared.getAccount(port);
            if (ac.hasCharacter(charLoginId)) {
                ac.setCharacterLogon(charLoginId);
            }

        }

    }

    /**
     * 01 87 -> Sync packet [2]
     * xx xx xx xx -> Account ID [4]
     */
    private static final String SYNC_REQUEST_PACKET = "87 01";
    private void syncRequest(String packet, int port) {
        accountId(packet, port);
    }

    /**
     * 02 83 -> Account ID Packet [2]
     * xx xx xx xx -> Account ID [4]
     */
    private static final String ACCOUNT_ID_PACKET = "83 02";
    private void accountId(String packet, int port) {

        String[] sepContent = packet.split(" ");
        if (sepContent.length == 6) {

            // Packet content
            List<String> accountIdList = Arrays.asList(Arrays.copyOfRange(sepContent, 2, 6));
            // Reverse
            Collections.reverse(accountIdList);

            // Parse to info
            long accountId = new BigInteger(String.join("", accountIdList), 16).longValue();

            if (ROObjectController.shared.hasAccount(port)) {
                ROObjectController.shared.getAccount(port).setId(accountId);
            }

        }


    }

}
