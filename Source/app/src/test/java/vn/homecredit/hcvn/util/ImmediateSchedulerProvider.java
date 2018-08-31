package vn.homecredit.hcvn.util;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;
import vn.homecredit.hcvn.utils.rx.SchedulerProvider;

public class ImmediateSchedulerProvider implements SchedulerProvider{

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
