package com.ro;

import com.ro.models.Account;
import com.ro.network.PacketDecryption;
import com.ro.network.PacketInterceptor;
import com.ro.view.MainPanel;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class main extends Application {

    public static MainPanel mainPanel = new MainPanel();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(mainPanel, 460, 555);
        primaryStage.setTitle("RO Panel");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String... args) {

        PacketInterceptor pkInter = new PacketInterceptor();
        PacketDecryption pDecrypt = new PacketDecryption();

        // Packet capture thread
        new Thread(() -> {
            while(true) {
                try {
                    String[] sPacket = pkInter.getNextPacket();
                    new Thread(() -> pDecrypt.decryption(sPacket[0], Integer.parseInt(sPacket[1]))).start();
                } catch (Exception e) {
                    //e.printStackTrace();
                }

            }
        }).start();

        launch();
    }

}
