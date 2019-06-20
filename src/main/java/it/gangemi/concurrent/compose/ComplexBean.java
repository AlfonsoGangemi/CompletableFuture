package it.gangemi.concurrent.compose;

public class ComplexBean {

    private String key;
    private String valueString;
    private Long valueLong;

    public ComplexBean() {
    }

    private ComplexBean(String key, String valueString, Long valueLong) {
        this.key = key;
        this.valueString = valueString;
        this.valueLong = valueLong;
    }

    public static ComplexBean build(String key) {
        return new ComplexBean(key, null, null);
    }

    public static ComplexBean build(StringBean stringBean) {
        return new ComplexBean(stringBean.getKey(),stringBean.getValueString(),null);
    }

    public void setValueLong(Long valueLong) {
        this.valueLong = valueLong;
    }

    @Override
    public String toString() {
        return "ComplexBean{" +
                "key='" + key + '\'' +
                ", valueString='" + valueString + '\'' +
                ", valueLong=" + valueLong +
                '}';
    }
}
