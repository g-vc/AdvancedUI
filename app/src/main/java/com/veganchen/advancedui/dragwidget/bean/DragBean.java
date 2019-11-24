package com.veganchen.advancedui.dragwidget.bean;

import java.io.Serializable;

public class DragBean implements Serializable {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
