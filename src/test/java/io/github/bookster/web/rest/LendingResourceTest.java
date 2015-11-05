package io.github.bookster.web.rest;

import io.github.bookster.Application;
import io.github.bookster.domain.Lending;
import io.github.bookster.repository.LendingRepository;

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
 * Test class for the LendingResource REST controller.
 *
 * @see LendingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LendingResourceTest {

    private static final String DEFAULT_FROM = "AAAAA";
    private static final String UPDATED_FROM = "BBBBB";
    private static final String DEFAULT_DUE = "AAAAA";
    private static final String UPDATED_DUE = "BBBBB";

    @Inject
    private LendingRepository lendingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLendingMockMvc;

    private Lending lending;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LendingResource lendingResource = new LendingResource();
        ReflectionTestUtils.setField(lendingResource, "lendingRepository", lendingRepository);
        this.restLendingMockMvc = MockMvcBuilders.standaloneSetup(lendingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lendingRepository.deleteAll();
        lending = new Lending();
        lending.setFrom(DEFAULT_FROM);
        lending.setDue(DEFAULT_DUE);
    }

    @Test
    public void createLending() throws Exception {
        int databaseSizeBeforeCreate = lendingRepository.findAll().size();

        // Create the Lending

        restLendingMockMvc.perform(post("/api/lendings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lending)))
                .andExpect(status().isCreated());

        // Validate the Lending in the database
        List<Lending> lendings = lendingRepository.findAll();
        assertThat(lendings).hasSize(databaseSizeBeforeCreate + 1);
        Lending testLending = lendings.get(lendings.size() - 1);
        assertThat(testLending.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testLending.getDue()).isEqualTo(DEFAULT_DUE);
    }

    @Test
    public void getAllLendings() throws Exception {
        // Initialize the database
        lendingRepository.save(lending);

        // Get all the lendings
        restLendingMockMvc.perform(get("/api/lendings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lending.getId())))
                .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
                .andExpect(jsonPath("$.[*].due").value(hasItem(DEFAULT_DUE.toString())));
    }

    @Test
    public void getLending() throws Exception {
        // Initialize the database
        lendingRepository.save(lending);

        // Get the lending
        restLendingMockMvc.perform(get("/api/lendings/{id}", lending.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lending.getId()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.due").value(DEFAULT_DUE.toString()));
    }

    @Test
    public void getNonExistingLending() throws Exception {
        // Get the lending
        restLendingMockMvc.perform(get("/api/lendings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateLending() throws Exception {
        // Initialize the database
        lendingRepository.save(lending);

		int databaseSizeBeforeUpdate = lendingRepository.findAll().size();

        // Update the lending
        lending.setFrom(UPDATED_FROM);
        lending.setDue(UPDATED_DUE);

        restLendingMockMvc.perform(put("/api/lendings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lending)))
                .andExpect(status().isOk());

        // Validate the Lending in the database
        List<Lending> lendings = lendingRepository.findAll();
        assertThat(lendings).hasSize(databaseSizeBeforeUpdate);
        Lending testLending = lendings.get(lendings.size() - 1);
        assertThat(testLending.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testLending.getDue()).isEqualTo(UPDATED_DUE);
    }

    @Test
    public void deleteLending() throws Exception {
        // Initialize the database
        lendingRepository.save(lending);

		int databaseSizeBeforeDelete = lendingRepository.findAll().size();

        // Get the lending
        restLendingMockMvc.perform(delete("/api/lendings/{id}", lending.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lending> lendings = lendingRepository.findAll();
        assertThat(lendings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
