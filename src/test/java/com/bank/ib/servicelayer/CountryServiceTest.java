package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.CountryDao;
import com.bank.ib.model.Country;
import com.bank.ib.service.CountryService;
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
public class CountryServiceTest {

    @Autowired
    private Country c;

    @Autowired
    private CountryService countryService;

    @MockBean
    private CountryDao countryDao;

    @Test
    public void GetCountryTest() {
        when(countryDao.read(testID)).thenReturn(c);

        Assertions.assertEquals(c, countryService.read(testID));
    }

    @Test
    public void updateCountryTest() {
        countryService.update(c);

        verify(countryDao, times(1)).update(c);

    }

    @Test
    public void deleteCountryTest() {
        countryService.remove(c);

        verify(countryDao, times(1)).remove(c);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(countryDao.countAll()).thenReturn(1L);
        when(countryDao.getAll()).thenReturn(Stream.of(c).collect(Collectors.toList()));

        Assertions.assertEquals(countryService.countAll(),
                countryService.getAll().size());
    }

    @Test
    public void canBeDeletedTest() {
        when(countryDao.canBeDeleted(c)).thenReturn(true);
        when(countryDao.canBeInserted(c)).thenReturn(true);

        Assertions.assertTrue(countryService.canBeDeleted(c));
        Assertions.assertTrue(countryService.canBeInserted(c));
    }


}
