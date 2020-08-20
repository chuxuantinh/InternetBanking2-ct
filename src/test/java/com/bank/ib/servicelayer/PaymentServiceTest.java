package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.PaymentDao;
import com.bank.ib.model.Payment;
import com.bank.ib.service.PaymentService;
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
public class PaymentServiceTest {

    @Autowired
    private Payment p;

    @Autowired
    private PaymentService paymentService;

    @MockBean
    private PaymentDao paymentDao;

    @Test
    public void GetPaymentTest() {
        when(paymentDao.read(testID)).thenReturn(p);

        Assertions.assertEquals(p, paymentService.read(testID));
    }

    @Test
    public void updatePaymentTest() {
        paymentService.update(p);

        verify(paymentDao, times(1)).update(p);
    }

    @Test
    public void deletePaymentTest() {
        paymentService.remove(p);

        verify(paymentDao, times(1)).remove(p);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(paymentDao.countAll()).thenReturn(1L);
        when(paymentDao.getAll()).thenReturn(Stream.of(p).collect(Collectors.toList()));

        Assertions.assertEquals(paymentService.countAll(),
                paymentService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(paymentDao.canBeDeleted(p)).thenReturn(true);
        when(paymentDao.canBeInserted(p)).thenReturn(true);

        Assertions.assertTrue(paymentService.canBeDeleted(p));
        Assertions.assertTrue(paymentService.canBeInserted(p));
    }


}
