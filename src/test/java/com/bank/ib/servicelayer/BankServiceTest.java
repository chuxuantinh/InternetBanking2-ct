package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.BankDao;
import com.bank.ib.model.Bank;
import com.bank.ib.service.BankService;
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
public class BankServiceTest {

    @Autowired
    private Bank b;

    @Autowired
    private BankService bankService;

    @MockBean
    private BankDao bankDao;

    @Test
    public void GetBankTest() {
        when(bankDao.read(testID)).thenReturn(b);

        Assertions.assertEquals(b, bankService.read(testID));
    }

    @Test
    public void updateBankTest() {
        bankService.update(b);

        verify(bankDao, times(1)).update(b);
    }

    @Test
    public void deleteBankTest() {
        bankService.remove(b);

        verify(bankDao, times(1)).remove(b);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(bankDao.countAll()).thenReturn(1L);
        when(bankDao.getAll()).thenReturn(Stream.of(b).collect(Collectors.toList()));

        Assertions.assertEquals(bankService.countAll(),
                bankService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(bankDao.canBeDeleted(b)).thenReturn(true);
        when(bankDao.canBeInserted(b)).thenReturn(true);

        Assertions.assertTrue(bankService.canBeDeleted(b));
        Assertions.assertTrue(bankService.canBeInserted(b));
    }

}
