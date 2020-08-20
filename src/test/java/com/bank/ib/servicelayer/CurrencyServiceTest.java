package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.CurrencyDao;
import com.bank.ib.model.Currency;
import com.bank.ib.service.CurrencyService;
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
public class CurrencyServiceTest {

    @Autowired
    private Currency c;

    @Autowired
    private CurrencyService currencyService;

    @MockBean
    private CurrencyDao currencyDao;

    @Test
    public void GetCurrencyTest() {
        when(currencyDao.read(testID)).thenReturn(c);

        Assertions.assertEquals(c, currencyService.read(testID));
    }

    @Test
    public void updateCurrencyTest() {
        currencyService.update(c);

        verify(currencyDao, times(1)).update(c);
    }

    @Test
    public void deleteCurrencyTest() {
        currencyService.remove(c);

        verify(currencyDao, times(1)).remove(c);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(currencyDao.countAll()).thenReturn(1L);
        when(currencyDao.getAll()).thenReturn(Stream.of(c).collect(Collectors.toList()));

        Assertions.assertEquals(currencyService.countAll(),
                currencyService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(currencyDao.canBeDeleted(c)).thenReturn(true);
        when(currencyDao.canBeInserted(c)).thenReturn(true);

        Assertions.assertTrue(currencyService.canBeDeleted(c));
        Assertions.assertTrue(currencyService.canBeInserted(c));
    }

}
