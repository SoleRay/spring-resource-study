package ioc.bean.method;

public class PeopleBean {

    private String name;

    private int gender;

    private PetBean petBean;

    private PeopleBean(){

    }

    public PeopleBean(String name, int gender, PetBean petBean) {
        this.name = name;
        this.gender = gender;
        this.petBean = petBean;
    }

    public static PeopleBean getPeopleBean(){
        return new PeopleBean();
    }

    public static PeopleBean getPeopleBean(String name, int gender, PetBean petBean){
        return new PeopleBean(name,gender, petBean);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public PetBean getPetBean() {
        return petBean;
    }

    public void setPetBean(PetBean petBean) {
        this.petBean = petBean;
    }

    @Override
    public String toString() {
        return "PeopleBean{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", petBean=" + petBean +
                '}';
    }
}
