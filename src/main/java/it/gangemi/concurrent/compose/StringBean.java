package it.gangemi.concurrent.compose;

import java.util.UUID;

public class StringBean {

    private String key;
    private String valueString;

    public StringBean() {
    }

    private StringBean(String key, String valueString) {
        this.key = key;
        this.valueString = valueString;
    }

    public static StringBean build(String key, String value) {
        return new StringBean(key,value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    @Override
    public String toString() {
        return "StringBean{" +
                "key='" + key + '\'' +
                ", valueString='" + valueString + '\'' +
                '}';
    }
}
