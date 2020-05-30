package com.ro.network;

import com.ro.enums.ExpTypeEnum;
import com.ro.ROObjectController;
import com.ro.models.Account;

import java.math.BigInteger;
import java.util.*;

public class PacketDecryption {

    public PacketDecryption() {

    }

    /**
     * All packet is in reverse direction [mirror]
     */
    public void decryption(String packet, int port) {
        // Exp Packet
        if (packet.contains(EXP_PACKET)) {
            newExp(packet, port);
        }

        // Total Exp Packet
        if (packet.contains(TOTAL_EXP_PACKET)) {
            newTotalExp(packet, port);
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

            System.out.println(packContent);

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
                Account currentAc;
                if (ROObjectController.shared.hasAccount(acId)) {
                    currentAc = ROObjectController.shared.getAccount(acId);
                    currentAc.setPort(port);
                } else {
                    currentAc = new Account(acId);
                    currentAc.setPort(port);
                    ROObjectController.shared.addAccount(acId, currentAc);
                }

                switch (type) {
                    case JOB:
                        currentAc.setJobExp(exp);
                        break;
                    case BASE:
                        currentAc.setBaseExp(exp);
                        break;
                }

                System.out.println("Account: "+ acId);
                System.out.println("Exp: "+ exp);
                System.out.println("BASE Exp/Hour "+ currentAc.getBaseExp().getExpHour());
                System.out.println("JOB Exp/Hour "+ currentAc.getJobExp().getExpHour());
                System.out.println("Type: "+ type);
                System.out.println("---------");
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
        for (int index = packet.indexOf(TOTAL_EXP_PACKET);
             index >= 0;
             index = packet.indexOf(TOTAL_EXP_PACKET, index + 1))
        {
            /*String packContent = packet.substring(index, index + (TOTAL_EXP_PACKET_SIZE*2)+(TOTAL_EXP_PACKET_SIZE-1));
            String[] sepContent = packContent.split(" ");
            // Packet content
            List<String> type = Arrays.asList(Arrays.copyOfRange(sepContent, 2, 4));
            List<String> exp = Arrays.asList(Arrays.copyOfRange(sepContent, 4, 12));
            Collections.reverse(type);
            Collections.reverse(exp);
/*
            System.out.println(packContent);
            System.out.println("Type: "+ type);
            System.out.println("Exp: "+ exp);
            System.out.println("---------");*/
        }
    }

}
