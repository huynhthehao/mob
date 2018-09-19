package vn.homecredit.hcvn.util;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class TestSchedulerProvider implements SchedulerProvider{

    private final TestScheduler testScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    @Override
    public Scheduler computation() {
        return testScheduler;
    }

    @Override
    public Scheduler io() {
        return testScheduler;
    }

    @Override
    public Scheduler ui() {
        return testScheduler;
    }
}
