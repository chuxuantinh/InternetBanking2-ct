package com.bank.ib.service;

import com.bank.ib.dao.GenericDao;
import com.bank.ib.model.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class AnnouncementServiceImpl extends GenericServiceImpl<Announcement, Integer>
        implements AnnouncementService {


    public AnnouncementServiceImpl() {

    }

    @Autowired
    public AnnouncementServiceImpl(
            @Qualifier("announcementDaoImpl") GenericDao<Announcement, Integer> genericDao) {
        super(genericDao);
    }

}
