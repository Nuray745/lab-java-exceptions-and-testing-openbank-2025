package org.ironhack.collections.lab;

import org.ironhack.collections.lab.Model.Doctor;
import org.ironhack.collections.lab.Service.DoctorService;
import org.ironhack.collections.lab.Exception.DoctorNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class DoctorControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private DoctorService doctorService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetDoctorById_notFound() throws Exception {
        when(doctorService.findById(999L)).thenThrow(new DoctorNotFoundException(999L));

        mockMvc.perform(get("/doctors/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Doctor with id 999 not found")));
    }

    @Test
    public void testCreateDoctor_badRequest() throws Exception {
        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"specialty\":\"Cardiology\"}")) // name missing
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllDoctors_success() throws Exception {
        Doctor d1 = new Doctor(1L, "John Doe", "Cardiology");
        when(doctorService.findAll()).thenReturn(Arrays.asList(d1));

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"John Doe\",\"specialty\":\"Cardiology\"}]"));
    }
}