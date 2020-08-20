package com.bank.ib.controller;

import com.bank.ib.model.Announcement;
import com.bank.ib.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/announcement/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AnnouncementController extends AbstractController<Announcement> {

    @Autowired
    public AnnouncementController(@Qualifier("announcementServiceImpl") GenericService<Announcement, Integer> service) {

        super(service, Comparator.comparing(Announcement::getInsertDate).reversed());
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Announcement>> getAllAnnouncement() {
        return this.standardGetAllRecord();
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createAnnouncement(@RequestBody Announcement a) {

        return this.standardCreateRecord(a);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAnnouncement(@PathVariable("id") int id, @RequestBody Announcement a) {

        return this.standardUpdateRecord(id, a);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeAnnouncement(@PathVariable("id") int id) {

        return this.standardRemoveRecord(id);
    }

}
