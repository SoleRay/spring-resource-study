package ioc.bean;

public class AirBeanFactory {

    public AirBeanFactory() {
    }

    public AirBean createAirBean(){
        return new GenericAirBean();
    }

    private class GenericAirBean extends AirBean{

    }

}
