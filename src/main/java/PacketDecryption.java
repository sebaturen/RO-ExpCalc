import java.util.Arrays;

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
            String packContent = packet.substring(index, index + (EXP_PACKET_SIZE*2)+(EXP_PACKET_SIZE-1));
            String[] sepContent = packContent.split(" ");
            // Packet content
            String[] accountId = Arrays.copyOfRange(sepContent, 2, 5+1);
            String[] exp = Arrays.copyOfRange(sepContent, 6, 13+1);
            String[] type = Arrays.copyOfRange(sepContent, 13, 14+1);

            System.out.println(packContent);
            System.out.println("Account: "+ accountId);
            System.out.println("Exp: "+ exp);
            System.out.println("Type: "+ type);
            System.out.println("---------");
        }
    }

    /**
     * 0A CB -> Total exp packet
     * xx xx -> type (01 base | 02 job)
     * zz zz zz zz zz zz zz zz -> Exp
     * @param packet
     * @param port
     */
    private static final String TOTAL_EXP_PACKET = "CB 0A";
    private void newTotalExp(String packet, int port) {
        String[] pkContent = packet.split(" ");
        //ROExpCalc.roController.getAccount(123);
    }

}
