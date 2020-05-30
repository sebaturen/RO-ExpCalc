package com.ro.view;

import com.ro.models.Account;
import javafx.scene.layout.VBox;

public class MainPanel extends VBox {

    // Constant
    public static final byte MARGIN_LEFT 	= 12;
    public static final byte MARGIN_RIGHT	= 12;
    public static final byte MARGIN_BETWEEN	= 5;
    public static final byte MARGIN_TOP		= 8;
    public static final byte MARGIN_BOTTOM	= 8;

    private ExpPanel expPanel;

    public MainPanel() {
        this.expPanel = new ExpPanel();
    }

    public void setAccountId(long id) {
        this.expPanel.setAccountLabel(id);
    }

    public void setExpHour(Account ac) {
        expPanel.setExpHour(ac);
    }

    /** PAINT ZONE!: **/
    public void paint() {

        //Clear
        this.getChildren().clear();

        //Add zones:
        this.getChildren().add(expPanel.get(super.getWidth()));
    }

    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        paint();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        paint();
    }
}
