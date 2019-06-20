package it.gangemi.concurrent.compose;

public class LongBean {

    private String key;
    private Long valueLong;

    public LongBean() {
    }

    private LongBean(String key, Long valueLong) {
        this.key = key;
        this.valueLong = valueLong;
    }

    public static LongBean build(String key, Long value) {
        return new LongBean(key,value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValueLong() {
        return valueLong;
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    @Override
    public String toString() {
        return "LongBean{" +
                "key='" + key + '\'' +
                ", valueLong=" + valueLong +
                '}';
    }
}
