package vn.homecredit.hcvn.ui.base;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;

import vn.homecredit.hcvn.data.repository.AccountRepository;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public abstract class TestBaseViewModel<T extends BaseViewModel> {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public T viewModel;
    private MutableLiveData messageData;
    private MutableLiveData messageIdData;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initData();
        messageData = viewModel.getMessageData();
        messageIdData = viewModel.getMessageIdData();
    }

    public abstract void initData();

    public void assertMessage(String expected) {
        assertEquals(expected, messageData.getValue());
    }
    public void assertMessage(int expected) {
        assertEquals(expected, messageIdData.getValue());
    }


}
