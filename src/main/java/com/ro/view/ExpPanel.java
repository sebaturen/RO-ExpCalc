package com.ro.view;

import com.ro.models.Exp;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Formatter;

public class ExpPanel {

    private static final String ACCOUNT_LABEL = "Account: %d";
    private static final String BASE_EXP_HOUR = "Base Exp/Hour: %.1f";
    private static final String JOB_EXP_HOUR = "Job Exp/Hour: %.1f";

    private Label accountLabel = new Label("Account: <unknown>");
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

    public void setExpHour(Exp exp) {
        Platform.runLater(() -> {
            // Make info
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);

            switch (exp.getType()) {
                case BASE:
                    fmt.format(BASE_EXP_HOUR, exp.getExpHour());
                    baseExpHourLabel.setText(sbuf.toString());
                    break;
                case JOB:
                    fmt.format(JOB_EXP_HOUR, exp.getExpHour());
                    jobExpHourLabel.setText(sbuf.toString());
                    break;
            }

        });
    }
}
