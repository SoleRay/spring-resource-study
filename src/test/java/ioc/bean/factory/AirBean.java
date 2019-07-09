package ioc.bean.factory;

public class AirBean {

    private String aqi;

    private int temp;

    private CloudBean cloudBean;

    protected AirBean(){

    }

    protected AirBean(String aqi) {
        this(aqi,0,null);
    }

    public AirBean(String aqi, int temp) {
        this(aqi,temp,null);
    }

    public AirBean(String aqi, int temp, CloudBean cloudBean) {
        this.aqi = aqi;
        this.temp = temp;
        this.cloudBean = cloudBean;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "AirBean{" +
                "aqi='" + aqi + '\'' +
                ", temp=" + temp +
                ", cloudBean=" + cloudBean +
                '}';
    }
}
