package aop.advisor;

import java.util.List;

public interface AdvisorRegistry {

    void registerAdvisor(Advisor ad);

    List<Advisor> getAdvisors();
}
