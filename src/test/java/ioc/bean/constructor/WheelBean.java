package ioc.bean.constructor;

public class WheelBean {

    private String name;

    public WheelBean(){

    }

    public WheelBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
