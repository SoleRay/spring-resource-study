package ioc.bean.loop;

public class LoopBeanA {

    private LoopBeanB loopBeanB;

    public LoopBeanA(LoopBeanB loopBeanB) {
        this.loopBeanB = loopBeanB;
    }
}
