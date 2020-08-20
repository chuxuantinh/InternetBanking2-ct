package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.AddressDao;
import com.bank.ib.model.Address;
import com.bank.ib.service.AddressService;
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
public class AddressServiceTest {

    @Autowired
    private Address a;

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressDao addressDao;

    @Test
    public void GetAddressTest() {
        when(addressDao.read(testID)).thenReturn(a);

        Assertions.assertEquals(a, addressService.read(testID));
    }

    @Test
    public void updateAddressTest() {
        addressService.update(a);

        verify(addressDao, times(1)).update(a);
    }

    @Test
    public void deleteAddressTest() {
        addressService.remove(a);

        verify(addressDao, times(1)).remove(a);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(addressDao.countAll()).thenReturn(1L);
        when(addressDao.getAll()).thenReturn(Stream.of(a).collect(Collectors.toList()));

        Assertions.assertEquals(addressService.countAll(),
                addressService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(addressDao.canBeDeleted(a)).thenReturn(true);
        when(addressDao.canBeInserted(a)).thenReturn(true);

        Assertions.assertTrue(addressService.canBeDeleted(a));
        Assertions.assertTrue(addressService.canBeInserted(a));
    }

}
