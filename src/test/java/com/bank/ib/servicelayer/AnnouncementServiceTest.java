package com.bank.ib.servicelayer;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.dao.AnnouncementDao;
import com.bank.ib.model.Announcement;
import com.bank.ib.service.AnnouncementService;
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
public class AnnouncementServiceTest {

    @Autowired
    private Announcement a;

    @Autowired
    private AnnouncementService announcementService;

    @MockBean
    private AnnouncementDao announcementDao;

    @Test
    public void GetAnnouncementTest() {
        when(announcementDao.read(testID)).thenReturn(a);

        Assertions.assertEquals(a, announcementService.read(testID));
    }

    @Test
    public void updateAnnouncementTest() {
        announcementService.update(a);

        verify(announcementDao, times(1)).update(a);
    }

    @Test
    public void deleteAnnouncementTest() {
        announcementService.remove(a);

        verify(announcementDao, times(1)).remove(a);
    }

    @Test
    public void getAllAndCountAllTest() {
        when(announcementDao.countAll()).thenReturn(1L);
        when(announcementDao.getAll()).thenReturn(Stream.of(a).collect(Collectors.toList()));

        Assertions.assertEquals(announcementService.countAll(),
                announcementService.getAll().size());
    }

    @Test
    public void recordCanDelInsTest() {
        when(announcementDao.canBeDeleted(a)).thenReturn(true);
        when(announcementDao.canBeInserted(a)).thenReturn(true);

        Assertions.assertTrue(announcementService.canBeDeleted(a));
        Assertions.assertTrue(announcementService.canBeInserted(a));
    }

}
