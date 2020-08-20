package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.PaymentInfoDao;
import com.bank.ib.model.PaymentInfo;
import com.bank.ib.service.PaymentInfoService;
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
public class PaymentInfoServiceTest {

    @Autowired
    private PaymentInfo pi;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @MockBean
    private PaymentInfoDao paymentInfoDao;

    @Test
    public void GetPaymentInfoTest() {
        when(paymentInfoDao.read(testID)).thenReturn(pi);

        Assertions.assertEquals(pi, paymentInfoService.read(testID));
    }

    @Test
    public void updatePaymentInfoTest() {
        paymentInfoService.update(pi);

        verify(paymentInfoDao, times(1)).update(pi);
    }

    @Test
    public void deletePaymentInfoTest() {
        paymentInfoService.remove(pi);

        verify(paymentInfoDao, times(1)).remove(pi);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(paymentInfoDao.countAll()).thenReturn(1L);
        when(paymentInfoDao.getAll()).thenReturn(Stream.of(pi).collect(Collectors.toList()));

        Assertions.assertEquals(paymentInfoService.countAll(),
                paymentInfoService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(paymentInfoDao.canBeDeleted(pi)).thenReturn(true);
        when(paymentInfoDao.canBeInserted(pi)).thenReturn(true);

        Assertions.assertTrue(paymentInfoService.canBeDeleted(pi));
        Assertions.assertTrue(paymentInfoService.canBeInserted(pi));
    }

}
