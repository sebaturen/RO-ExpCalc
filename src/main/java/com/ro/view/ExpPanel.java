package com.ro.view;

import com.ro.models.Account;
import com.ro.models.Character;
import com.ro.models.Exp;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Formatter;

public class ExpPanel {

    private static final String CHARACTER_LABEL     = "Character: %d";
    private static final String EXP_OBTAIN_LABEL    = "Exp: %d/%d";
    private static final String TOTAL_EXP_LABEL     = "Total Exp: [%d/%d]";
    private static final String BASE_EXP_HOUR_LABEL = "Base Exp/Hour: %,.1f";
    private static final String JOB_EXP_HOUR_LABEL  = "Job Exp/Hour: %,.1f";
    private static final String LVL_LABEL           = "Lvl: [%d/%d]";

    private Label characterLabel = new Label("Character: <unknown>");
    private Label lastExpLabel = new Label("Exp: <n>/<n>");
    private Label totalExpLabel = new Label("Total Exp: [<n>/<n>]");
    private Label baseExpHourLabel = new Label("Base Exp/Hour: <n>");
    private Label jobExpHourLabel = new Label("Job Exp/Hour:  <n>");
    private Label lvlsLabel = new Label("Lvl: [b/b]");

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
                characterLabel,
                lastExpLabel,
                totalExpLabel,
                baseExpHourLabel,
                jobExpHourLabel,
                lvlsLabel
        );

        return expPanel;
    }

    public void setAccount(Account ac) {
        if (ac.getCharacterLogon() != null) {
            setCharacterLabel(ac.getCharacterLogon().getId());
            setLastExpLabel(ac.getCharacterLogon().getBaseExp(), ac.getCharacterLogon().getJobExp());
            setTotalExpLabel(ac.getCharacterLogon().getBaseExp(), ac.getCharacterLogon().getJobExp());
            setBaseExpHourLabel(ac.getCharacterLogon().getBaseExp());
            setJobExpHourLabel(ac.getCharacterLogon().getJobExp());
            setLvl(ac.getCharacterLogon());
        }
    }

    private void setCharacterLabel(long charId) {
        Platform.runLater(() -> {
            // Make info
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(CHARACTER_LABEL, charId);

            characterLabel.setText(sbuf.toString());
        });
    }

    private void setLvl(Character chr) {
        Platform.runLater(() -> {
            // Make info
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(LVL_LABEL, chr.getBaseExp().getLvl(), chr.getJobExp().getLvl());

            lvlsLabel.setText(sbuf.toString());
        });
    }

    private void setBaseExpHourLabel(Exp baseExp) {
        Platform.runLater(() -> {
            // Now
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(BASE_EXP_HOUR_LABEL, baseExp.getExpHour());
            baseExpHourLabel.setText(sbuf.toString());
        });
    }

    private void setJobExpHourLabel(Exp jobExp) {
        Platform.runLater(() -> {
            // Now
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(JOB_EXP_HOUR_LABEL, jobExp.getExpHour());
            jobExpHourLabel.setText(sbuf.toString());
        });
    }

    private void setLastExpLabel(Exp base, Exp job) {
        Platform.runLater(() -> {
            // Now
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(EXP_OBTAIN_LABEL, base.getLastExp(), job.getLastExp());
            lastExpLabel.setText(sbuf.toString());
        });
    }

    private void setTotalExpLabel(Exp base, Exp job) {
        Platform.runLater(() -> {
            // Now
            StringBuilder sbuf = new StringBuilder();
            Formatter fmt = new Formatter(sbuf);
            fmt.format(TOTAL_EXP_LABEL, base.getTotalExp(), job.getTotalExp());
            totalExpLabel.setText(sbuf.toString());
        });
    }
}
