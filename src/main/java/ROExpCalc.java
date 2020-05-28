import network.PacketInterceptor;

public class ROExpCalc {

    public static ROObjectController roController = new ROObjectController();

    public static void main(String... args) {

        PacketInterceptor pkInter = new PacketInterceptor();
        PacketDecryption pDecrypt = new PacketDecryption();

        while(true) {

            try {
                String[] sPacket = pkInter.getNextPacket();
                new Thread(() -> pDecrypt.decryption(sPacket[0], Integer.parseInt(sPacket[1]))).start();
            } catch (Exception e) {
                //e.printStackTrace();
            }

        }

    }

}
