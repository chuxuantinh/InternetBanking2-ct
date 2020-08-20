package com.bank.ib.integration;

import com.bank.ib.InternetBanking2Application;
import com.bank.ib.TestBeans;
import com.bank.ib.model.Announcement;
import com.bank.ib.service.AnnouncementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = InternetBanking2Application.class)
@Import(TestBeans.class)
@WebAppConfiguration
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser(roles = {"ADMIN"})
public class AnnouncementIntegrationTest {

    @Autowired
    Announcement announcement;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getAllAnnouncementTest() throws Exception {

        this.announcementService.create(announcement);

        this.mockMvc.perform(get("/admin/announcement/"))
                .andExpect(status().isOk());

    }

    @Test
    public void createAnnouncementTest() throws Exception {

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(announcement);

        this.mockMvc.perform(post("/admin/announcement/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateAnnouncementTest() throws Exception {

        this.announcementService.create(announcement);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(announcement);

        this.mockMvc.perform(put("/admin/announcement/" + announcement.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void removeAnnouncementTest() throws Exception {

        this.announcementService.create(announcement);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(announcement);

        this.mockMvc.perform(delete("/admin/announcement/" + announcement.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent());
    }


} 
