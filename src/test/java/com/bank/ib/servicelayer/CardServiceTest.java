package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.CardDao;
import com.bank.ib.model.Card;
import com.bank.ib.service.CardService;
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
public class CardServiceTest {

    @Autowired
    private Card c;

    @Autowired
    private CardService cardService;

    @MockBean
    private CardDao cardDao;

    @Test
    public void GetCardTest() {
        when(cardDao.read(testID)).thenReturn(c);

        Assertions.assertEquals(c, cardService.read(testID));
    }

    @Test
    public void updateCardTest() {
        cardService.update(c);

        verify(cardDao, times(1)).update(c);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(cardDao.countAll()).thenReturn(1L);
        when(cardDao.getAll()).thenReturn(Stream.of(c).collect(Collectors.toList()));

        Assertions.assertEquals(cardService.countAll(),
                cardService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(cardDao.canBeDeleted(c)).thenReturn(true);
        when(cardDao.canBeInserted(c)).thenReturn(true);

        Assertions.assertTrue(cardService.canBeDeleted(c));
        Assertions.assertTrue(cardService.canBeInserted(c));
    }

}
