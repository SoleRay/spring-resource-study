package cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;


public class MainTest {

    public static void main(String[] args) {
        Tom tom = new Tom();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Tom.class);
//        enhancer.setCallback(new PersonCallBack(tom));
        enhancer.setCallbacks(new Callback[]{new PersonCallBack(tom),new PersonCallBack2(tom)});
        enhancer.setCallbackFilter((method)->{

            if(method.getName().equals("eat")){
                return 0;
            }else {
                return 1;
            }
        });




        Person person = (Person) enhancer.create();
        person.sleep();
        person.eat();
    }
}
