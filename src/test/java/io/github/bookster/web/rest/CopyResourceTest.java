package io.github.bookster.web.rest;

import io.github.bookster.Application;
import io.github.bookster.domain.Copy;
import io.github.bookster.repository.CopyRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CopyResource REST controller.
 *
 * @see CopyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CopyResourceTest {


    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final Boolean DEFAULT_AVAILABLE = false;
    private static final Boolean UPDATED_AVAILABLE = true;

    @Inject
    private CopyRepository copyRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCopyMockMvc;

    private Copy copy;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CopyResource copyResource = new CopyResource();
        ReflectionTestUtils.setField(copyResource, "copyRepository", copyRepository);
        this.restCopyMockMvc = MockMvcBuilders.standaloneSetup(copyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        copyRepository.deleteAll();
        copy = new Copy();
        copy.setVerified(DEFAULT_VERIFIED);
        copy.setAvailable(DEFAULT_AVAILABLE);
    }

    @Test
    public void createCopy() throws Exception {
        int databaseSizeBeforeCreate = copyRepository.findAll().size();

        // Create the Copy

        restCopyMockMvc.perform(post("/api/copys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(copy)))
                .andExpect(status().isCreated());

        // Validate the Copy in the database
        List<Copy> copys = copyRepository.findAll();
        assertThat(copys).hasSize(databaseSizeBeforeCreate + 1);
        Copy testCopy = copys.get(copys.size() - 1);
        assertThat(testCopy.getVerified()).isEqualTo(DEFAULT_VERIFIED);
        assertThat(testCopy.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
    }

    @Test
    public void getAllCopys() throws Exception {
        // Initialize the database
        copyRepository.save(copy);

        // Get all the copys
        restCopyMockMvc.perform(get("/api/copys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(copy.getId())))
                .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
                .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE.booleanValue())));
    }

    @Test
    public void getCopy() throws Exception {
        // Initialize the database
        copyRepository.save(copy);

        // Get the copy
        restCopyMockMvc.perform(get("/api/copys/{id}", copy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(copy.getId()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE.booleanValue()));
    }

    @Test
    public void getNonExistingCopy() throws Exception {
        // Get the copy
        restCopyMockMvc.perform(get("/api/copys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCopy() throws Exception {
        // Initialize the database
        copyRepository.save(copy);

		int databaseSizeBeforeUpdate = copyRepository.findAll().size();

        // Update the copy
        copy.setVerified(UPDATED_VERIFIED);
        copy.setAvailable(UPDATED_AVAILABLE);

        restCopyMockMvc.perform(put("/api/copys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(copy)))
                .andExpect(status().isOk());

        // Validate the Copy in the database
        List<Copy> copys = copyRepository.findAll();
        assertThat(copys).hasSize(databaseSizeBeforeUpdate);
        Copy testCopy = copys.get(copys.size() - 1);
        assertThat(testCopy.getVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testCopy.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
    }

    @Test
    public void deleteCopy() throws Exception {
        // Initialize the database
        copyRepository.save(copy);

		int databaseSizeBeforeDelete = copyRepository.findAll().size();

        // Get the copy
        restCopyMockMvc.perform(delete("/api/copys/{id}", copy.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Copy> copys = copyRepository.findAll();
        assertThat(copys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
