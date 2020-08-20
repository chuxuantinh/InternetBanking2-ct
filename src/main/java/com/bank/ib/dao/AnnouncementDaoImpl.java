package com.bank.ib.dao;

import com.bank.ib.model.Announcement;
import org.springframework.stereotype.Repository;

@Repository
public class AnnouncementDaoImpl extends GenericDaoJpaImpl<Announcement, Integer>
        implements AnnouncementDao {

    @Override
    public boolean canBeInserted(Announcement a) {

        return this.recordNotExist("mainText", a.getMainText(), a.getId());
    }

}
