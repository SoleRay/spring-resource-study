package ioc.bean.factory;

public class AirBeanFactory {

    public AirBeanFactory() {
    }

    public AirBean createAirBean(){
        return new GenericAirBean();
    }

    public AirBean createAirBean(String aqi,int temp,CloudBean cloudBean){
        return new GenericAirBean(aqi,temp,cloudBean);
    }

    private class GenericAirBean extends AirBean{

        public GenericAirBean() {
        }

        public GenericAirBean(String aqi) {
            super(aqi);
        }

        public GenericAirBean(String aqi, int temp) {
            super(aqi, temp);
        }

        public GenericAirBean(String aqi, int temp, CloudBean cloudBean) {
            super(aqi, temp, cloudBean);
        }
    }

}
