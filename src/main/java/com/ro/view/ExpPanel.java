package com.ro.view;

import com.ro.models.Account;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Formatter;

public class ExpPanel {

    private static final String ACCOUNT_LABEL       = "Account: %d";
    private static final String EXP_OBTAIN_LABEL    = "Exp: %d/%d";
    private static final String BASE_EXP_HOUR_LABEL = "Base Exp/Hour: %,.1f";
    private static final String JOB_EXP_HOUR_LABEL  = "Job Exp/Hour: %,.1f";

    private Label accountLabel = new Label("Account: <unknown>");
    private Label lastExpLabel = new Label("Exp: <n>/<n>");
    private Label baseExpHourLabel = new Label("Base Exp/Hour: <n>");
    private Label jobExpHourLabel = new Label("Job Exp/Hour:  <n>");

    public VBox get(double parentWidth) {

        VBox expPanel = new VBox(5);
        expPanel.setPadding(
                new Insets(
                        MainPanel.MARGIN_BETWEEN,
                        MainPanel.MARGIN_RIGHT,
                        0,
                        MainPanel.MARGIN_LEFT
                )
        );

        expPanel.getChildren().addAll(
                accountLabel,
                lastExpLabel,
                baseExpHourLabel,
                jobExpHourLabel
        );

        return expPanel;
    }

    public void setAccountLabel(long acId) {
        Platform.runLater(() -> {
            // Make info
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(ACCOUNT_LABEL, acId);

            accountLabel.setText(sbuf.toString());
        });
    }

    public void setExpHour(Account ac) {
        Platform.runLater(() -> {
            // Now
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(EXP_OBTAIN_LABEL, ac.getBaseExp().getLastExp(), ac.getJobExp().getLastExp());
            lastExpLabel.setText(sbuf.toString());
            // BASE
            sbuf = new StringBuilder();
            fmt = new Formatter(sbuf);
            fmt.format(BASE_EXP_HOUR_LABEL, ac.getBaseExp().getExpHour());
            baseExpHourLabel.setText(sbuf.toString());
            // JOB
            sbuf = new StringBuilder();
            fmt = new Formatter(sbuf);
            fmt.format(JOB_EXP_HOUR_LABEL, ac.getJobExp().getExpHour());
            jobExpHourLabel.setText(sbuf.toString());

        });
    }
}
