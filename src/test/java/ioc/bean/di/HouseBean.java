package ioc.bean.di;

public class HouseBean {

    private int high;

    private int low;

    private DoorBean doorBean;

    @Override
    public String toString() {
        return "HouseBean{" +
                "high=" + high +
                ", low=" + low +
                ", doorBean=" + doorBean +
                '}';
    }
}
