package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.AccountFavouriteDao;
import com.bank.ib.model.AccountFavourite;
import com.bank.ib.service.AccountFavouriteService;
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
public class AccountFavouriteServiceTest {

    @Autowired
    private AccountFavourite af;

    @Autowired
    private AccountFavouriteService accountFavouriteService;

    @MockBean
    private AccountFavouriteDao accountFavouriteDao;

    @Test
    public void GetAccountFavouriteTest() {
        when(accountFavouriteDao.read(testID)).thenReturn(af);

        Assertions.assertEquals(af, accountFavouriteService.read(testID));
    }

    @Test
    public void updateAccountFavouriteTest() {
        accountFavouriteService.update(af);

        verify(accountFavouriteDao, times(1)).update(af);
    }

    @Test
    public void deleteAccountFavouriteTest() {
        accountFavouriteService.remove(af);

        verify(accountFavouriteDao, times(1)).remove(af);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(accountFavouriteDao.countAll()).thenReturn(1L);
        when(accountFavouriteDao.getAll()).thenReturn(Stream.of(af).collect(Collectors.toList()));

        Assertions.assertEquals(accountFavouriteService.countAll(),
                accountFavouriteService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(accountFavouriteDao.canBeDeleted(af)).thenReturn(true);
        when(accountFavouriteDao.canBeInserted(af)).thenReturn(true);

        Assertions.assertTrue(accountFavouriteService.canBeDeleted(af));
        Assertions.assertTrue(accountFavouriteService.canBeInserted(af));
    }

}
