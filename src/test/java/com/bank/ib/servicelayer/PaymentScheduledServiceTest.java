package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.PaymentScheduledDao;
import com.bank.ib.model.PaymentScheduled;
import com.bank.ib.service.PaymentScheduledService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.bank.ib.TestBeans.testID;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = InternetBanking2Application.class)
@Import(TestBeans.class)
public class PaymentScheduledServiceTest {

    @Autowired
    private PaymentScheduled ps;

    @Autowired
    private PaymentScheduledService paymentScheduledService;

    @MockBean
    private PaymentScheduledDao paymentScheduledDao;

    @Test
    public void GetPaymentScheduledTest() {
        when(paymentScheduledDao.read(testID)).thenReturn(ps);

        Assertions.assertEquals(ps, paymentScheduledService.read(testID));
    }

    @Test
    public void updatePaymentScheduledTest() {
        paymentScheduledService.update(ps);

        verify(paymentScheduledDao, times(1)).update(ps);
    }

    @Test
    public void deletePaymentScheduledTest() {
        paymentScheduledService.remove(ps);

        verify(paymentScheduledDao, times(1)).remove(ps);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(paymentScheduledDao.countAll()).thenReturn(1L);
        when(paymentScheduledDao.getAll()).thenReturn(Stream.of(ps).collect(Collectors.toList()));

        Assertions.assertEquals(paymentScheduledService.countAll(),
                paymentScheduledService.getAll().size());
    }


    @Test
    public void recordCanDelInsTest() {
        when(paymentScheduledDao.canBeDeleted(ps)).thenReturn(true);
        when(paymentScheduledDao.canBeInserted(ps)).thenReturn(true);

        Assertions.assertTrue(paymentScheduledService.canBeDeleted(ps));
        Assertions.assertTrue(paymentScheduledService.canBeInserted(ps));
    }

}
