package ioc.bean;

public class CarBean {

    private String carName;

    private WheelBean wheelBean;

    public CarBean() {
        System.out.println("created instance with default constructor...");
    }

    public CarBean(String carName, WheelBean wheelBean) {
        this.carName = carName;
        this.wheelBean = wheelBean;

        System.out.println("created instance with args-constructor...");
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public WheelBean getWheelBean() {
        return wheelBean;
    }

    public void setWheelBean(WheelBean wheelBean) {
        this.wheelBean = wheelBean;
    }
}
