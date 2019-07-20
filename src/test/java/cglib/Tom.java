package cglib;

public class Tom implements Person {

    private int i=5;

    private void makeLove(){
        System.out.println("Tom make Love with his girlfriend...");
    }

    @Override
    public void eat() {
        System.out.println(i);
        System.out.println("Tom eat....");
    }

    @Override
    public void sleep() {
        i=6;
        makeLove();
        System.out.println("Tom sleep....");
    }

}
