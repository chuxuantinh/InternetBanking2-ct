package com.bank.ib.dao;

import com.bank.ib.model.Address;
import org.springframework.stereotype.Repository;


@Repository
public class AddressDaoImpl extends GenericDaoJpaImpl<Address, Integer>
        implements AddressDao {

    @Override
    public boolean canBeDeleted(Address a) {

        return this.referenceNotExist("Client", "residenceAddress", a.getId().toString());
    }

}
