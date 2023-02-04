package com.sixbert.authenticekuku;


import java.util.List;

public class Filter {
    public static final Integer INDEX_TYPE = 0;
    public static final Integer INDEX_QTY = 1;
    public static final Integer INDEX_PRICE = 2;
    //

    private String name;
    private List<String> values;
    private List<String> selected;

    public Filter(String name, List<String> values, List<String> selected) {
        this.name = name;
        this.values = values;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }
}
