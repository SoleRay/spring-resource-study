package ioc.bean;

public class PeopleBean {

    private PeopleBean(){

    }

    public static PeopleBean getPeopleBean(){
        return new PeopleBean();
    }
}
