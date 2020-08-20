package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.AccountTypeDao;
import com.bank.ib.model.AccountType;
import com.bank.ib.service.AccountTypeService;
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
public class AccountTypeServiceTest {

    @Autowired
    private AccountType at;

    @Autowired
    private AccountTypeService accountTypeService;

    @MockBean
    private AccountTypeDao accountTypeDao;

    @Test
    public void GetAccountTypeTest() {
        when(accountTypeDao.read(testID)).thenReturn(at);

        Assertions.assertEquals(at, accountTypeService.read(testID));
    }

    @Test
    public void updateAccountTypeTest() {
        accountTypeService.update(at);

        verify(accountTypeDao, times(1)).update(at);
    }

    @Test
    public void deleteAccountTypeTest() {
        accountTypeService.remove(at);

        verify(accountTypeDao, times(1)).remove(at);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(accountTypeDao.countAll()).thenReturn(1L);
        when(accountTypeDao.getAll()).thenReturn(Stream.of(at).collect(Collectors.toList()));

        Assertions.assertEquals(accountTypeService.countAll(),
                accountTypeService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(accountTypeDao.canBeDeleted(at)).thenReturn(true);
        when(accountTypeDao.canBeInserted(at)).thenReturn(true);

        Assertions.assertTrue(accountTypeService.canBeDeleted(at));
        Assertions.assertTrue(accountTypeService.canBeInserted(at));
    }

}
