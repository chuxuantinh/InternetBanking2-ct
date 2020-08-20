package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.ClientDao;
import com.bank.ib.model.Client;
import com.bank.ib.service.ClientService;
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
public class ClientServiceTest {

    @Autowired
    private Client ct;

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientDao clientDao;

    @Test
    public void GetClientTest() {
        when(clientDao.read(testID)).thenReturn(ct);

        Assertions.assertEquals(ct, clientService.read(testID));
    }

    @Test
    public void updateClientTest() {
        clientService.update(ct);

        verify(clientDao, times(1)).update(ct);
    }

    @Test
    public void deleteClientTest() {
        clientService.remove(ct);

        verify(clientDao, times(1)).remove(ct);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(clientDao.countAll()).thenReturn(1L);
        when(clientDao.getAll()).thenReturn(Stream.of(ct).collect(Collectors.toList()));

        Assertions.assertEquals(clientService.countAll(),
                clientService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(clientDao.canBeDeleted(ct)).thenReturn(true);
        when(clientDao.canBeInserted(ct)).thenReturn(true);

        Assertions.assertTrue(clientService.canBeDeleted(ct));
        Assertions.assertTrue(clientService.canBeInserted(ct));
    }

}
