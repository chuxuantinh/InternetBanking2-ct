package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.AccountDao;
import com.bank.ib.model.Account;
import com.bank.ib.service.AccountService;
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
public class AccountServiceTest {

    @Autowired
    private Account a;

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountDao accountDao;

    @Test
    public void GetAccountTest() {
        when(accountDao.read(testID)).thenReturn(a);

        Assertions.assertEquals(a, accountService.read(testID));
    }

    @Test
    public void updateAccountTest() {
        accountService.update(a);

        verify(accountDao, times(1)).update(a);
    }

    @Test
    public void deleteAccountTest() {
        accountService.remove(a);

        verify(accountDao, times(1)).remove(a);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(accountDao.countAll()).thenReturn(1L);
        when(accountDao.getAll()).thenReturn(Stream.of(a).collect(Collectors.toList()));

        Assertions.assertEquals(accountService.countAll(),
                accountService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(accountDao.canBeDeleted(a)).thenReturn(true);
        when(accountDao.canBeInserted(a)).thenReturn(true);

        Assertions.assertTrue(accountService.canBeDeleted(a));
        Assertions.assertTrue(accountService.canBeInserted(a));
    }

}
