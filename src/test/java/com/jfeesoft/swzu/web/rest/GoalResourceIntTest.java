package com.jfeesoft.swzu.web.rest;

import com.jfeesoft.swzu.OfficeRestApp;

import com.jfeesoft.swzu.domain.Goal;
import com.jfeesoft.swzu.domain.Task;
import com.jfeesoft.swzu.repository.GoalRepository;
import com.jfeesoft.swzu.service.GoalService;
import com.jfeesoft.swzu.service.dto.GoalDTO;
import com.jfeesoft.swzu.service.mapper.GoalMapper;
import com.jfeesoft.swzu.web.rest.errors.ExceptionTranslator;
import com.jfeesoft.swzu.service.dto.GoalCriteria;
import com.jfeesoft.swzu.service.GoalQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.jfeesoft.swzu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GoalResource REST controller.
 *
 * @see GoalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OfficeRestApp.class)
public class GoalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_VERSION = 1L;
    private static final Long UPDATED_VERSION = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalMapper goalMapper;
    
    @Autowired
    private GoalService goalService;

    @Autowired
    private GoalQueryService goalQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGoalMockMvc;

    private Goal goal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GoalResource goalResource = new GoalResource(goalService, goalQueryService);
        this.restGoalMockMvc = MockMvcBuilders.standaloneSetup(goalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goal createEntity(EntityManager em) {
        Goal goal = new Goal()
            .name(DEFAULT_NAME)
            .dateFrom(DEFAULT_DATE_FROM)
            .dateTo(DEFAULT_DATE_TO)
            .version(DEFAULT_VERSION)
            .status(DEFAULT_STATUS);
        return goal;
    }

    @Before
    public void initTest() {
        goal = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoal() throws Exception {
        int databaseSizeBeforeCreate = goalRepository.findAll().size();

        // Create the Goal
        GoalDTO goalDTO = goalMapper.toDto(goal);
        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalDTO)))
            .andExpect(status().isCreated());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
        Goal testGoal = goalList.get(goalList.size() - 1);
        assertThat(testGoal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoal.getDateFrom()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testGoal.getDateTo()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testGoal.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testGoal.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createGoalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goalRepository.findAll().size();

        // Create the Goal with an existing ID
        goal.setId(1L);
        GoalDTO goalDTO = goalMapper.toDto(goal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalMockMvc.perform(post("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGoals() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList
        restGoalMockMvc.perform(get("/api/goals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", goal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(goal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.dateFrom").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.dateTo").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllGoalsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where name equals to DEFAULT_NAME
        defaultGoalShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the goalList where name equals to UPDATED_NAME
        defaultGoalShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGoalsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGoalShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the goalList where name equals to UPDATED_NAME
        defaultGoalShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGoalsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where name is not null
        defaultGoalShouldBeFound("name.specified=true");

        // Get all the goalList where name is null
        defaultGoalShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoalsByDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateFrom equals to DEFAULT_DATE_FROM
        defaultGoalShouldBeFound("dateFrom.equals=" + DEFAULT_DATE_FROM);

        // Get all the goalList where dateFrom equals to UPDATED_DATE_FROM
        defaultGoalShouldNotBeFound("dateFrom.equals=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllGoalsByDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateFrom in DEFAULT_DATE_FROM or UPDATED_DATE_FROM
        defaultGoalShouldBeFound("dateFrom.in=" + DEFAULT_DATE_FROM + "," + UPDATED_DATE_FROM);

        // Get all the goalList where dateFrom equals to UPDATED_DATE_FROM
        defaultGoalShouldNotBeFound("dateFrom.in=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllGoalsByDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateFrom is not null
        defaultGoalShouldBeFound("dateFrom.specified=true");

        // Get all the goalList where dateFrom is null
        defaultGoalShouldNotBeFound("dateFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoalsByDateFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateFrom greater than or equals to DEFAULT_DATE_FROM
        defaultGoalShouldBeFound("dateFrom.greaterOrEqualThan=" + DEFAULT_DATE_FROM);

        // Get all the goalList where dateFrom greater than or equals to UPDATED_DATE_FROM
        defaultGoalShouldNotBeFound("dateFrom.greaterOrEqualThan=" + UPDATED_DATE_FROM);
    }

    @Test
    @Transactional
    public void getAllGoalsByDateFromIsLessThanSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateFrom less than or equals to DEFAULT_DATE_FROM
        defaultGoalShouldNotBeFound("dateFrom.lessThan=" + DEFAULT_DATE_FROM);

        // Get all the goalList where dateFrom less than or equals to UPDATED_DATE_FROM
        defaultGoalShouldBeFound("dateFrom.lessThan=" + UPDATED_DATE_FROM);
    }


    @Test
    @Transactional
    public void getAllGoalsByDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateTo equals to DEFAULT_DATE_TO
        defaultGoalShouldBeFound("dateTo.equals=" + DEFAULT_DATE_TO);

        // Get all the goalList where dateTo equals to UPDATED_DATE_TO
        defaultGoalShouldNotBeFound("dateTo.equals=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllGoalsByDateToIsInShouldWork() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateTo in DEFAULT_DATE_TO or UPDATED_DATE_TO
        defaultGoalShouldBeFound("dateTo.in=" + DEFAULT_DATE_TO + "," + UPDATED_DATE_TO);

        // Get all the goalList where dateTo equals to UPDATED_DATE_TO
        defaultGoalShouldNotBeFound("dateTo.in=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllGoalsByDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateTo is not null
        defaultGoalShouldBeFound("dateTo.specified=true");

        // Get all the goalList where dateTo is null
        defaultGoalShouldNotBeFound("dateTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoalsByDateToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateTo greater than or equals to DEFAULT_DATE_TO
        defaultGoalShouldBeFound("dateTo.greaterOrEqualThan=" + DEFAULT_DATE_TO);

        // Get all the goalList where dateTo greater than or equals to UPDATED_DATE_TO
        defaultGoalShouldNotBeFound("dateTo.greaterOrEqualThan=" + UPDATED_DATE_TO);
    }

    @Test
    @Transactional
    public void getAllGoalsByDateToIsLessThanSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where dateTo less than or equals to DEFAULT_DATE_TO
        defaultGoalShouldNotBeFound("dateTo.lessThan=" + DEFAULT_DATE_TO);

        // Get all the goalList where dateTo less than or equals to UPDATED_DATE_TO
        defaultGoalShouldBeFound("dateTo.lessThan=" + UPDATED_DATE_TO);
    }


    @Test
    @Transactional
    public void getAllGoalsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where version equals to DEFAULT_VERSION
        defaultGoalShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the goalList where version equals to UPDATED_VERSION
        defaultGoalShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllGoalsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultGoalShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the goalList where version equals to UPDATED_VERSION
        defaultGoalShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllGoalsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where version is not null
        defaultGoalShouldBeFound("version.specified=true");

        // Get all the goalList where version is null
        defaultGoalShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoalsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where version greater than or equals to DEFAULT_VERSION
        defaultGoalShouldBeFound("version.greaterOrEqualThan=" + DEFAULT_VERSION);

        // Get all the goalList where version greater than or equals to UPDATED_VERSION
        defaultGoalShouldNotBeFound("version.greaterOrEqualThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllGoalsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where version less than or equals to DEFAULT_VERSION
        defaultGoalShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the goalList where version less than or equals to UPDATED_VERSION
        defaultGoalShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }


    @Test
    @Transactional
    public void getAllGoalsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where status equals to DEFAULT_STATUS
        defaultGoalShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the goalList where status equals to UPDATED_STATUS
        defaultGoalShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllGoalsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultGoalShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the goalList where status equals to UPDATED_STATUS
        defaultGoalShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllGoalsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList where status is not null
        defaultGoalShouldBeFound("status.specified=true");

        // Get all the goalList where status is null
        defaultGoalShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllGoalsByTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        Task task = TaskResourceIntTest.createEntity(em);
        em.persist(task);
        em.flush();
        goal.addTask(task);
        goalRepository.saveAndFlush(goal);
        Long taskId = task.getId();

        // Get all the goalList where task equals to taskId
        defaultGoalShouldBeFound("taskId.equals=" + taskId);

        // Get all the goalList where task equals to taskId + 1
        defaultGoalShouldNotBeFound("taskId.equals=" + (taskId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGoalShouldBeFound(String filter) throws Exception {
        restGoalMockMvc.perform(get("/api/goals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].dateFrom").value(hasItem(DEFAULT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].dateTo").value(hasItem(DEFAULT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restGoalMockMvc.perform(get("/api/goals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGoalShouldNotBeFound(String filter) throws Exception {
        restGoalMockMvc.perform(get("/api/goals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGoalMockMvc.perform(get("/api/goals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingGoal() throws Exception {
        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        int databaseSizeBeforeUpdate = goalRepository.findAll().size();

        // Update the goal
        Goal updatedGoal = goalRepository.findById(goal.getId()).get();
        // Disconnect from session so that the updates on updatedGoal are not directly saved in db
        em.detach(updatedGoal);
        updatedGoal
            .name(UPDATED_NAME)
            .dateFrom(UPDATED_DATE_FROM)
            .dateTo(UPDATED_DATE_TO)
            .version(UPDATED_VERSION)
            .status(UPDATED_STATUS);
        GoalDTO goalDTO = goalMapper.toDto(updatedGoal);

        restGoalMockMvc.perform(put("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalDTO)))
            .andExpect(status().isOk());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeUpdate);
        Goal testGoal = goalList.get(goalList.size() - 1);
        assertThat(testGoal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoal.getDateFrom()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testGoal.getDateTo()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testGoal.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testGoal.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingGoal() throws Exception {
        int databaseSizeBeforeUpdate = goalRepository.findAll().size();

        // Create the Goal
        GoalDTO goalDTO = goalMapper.toDto(goal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalMockMvc.perform(put("/api/goals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(goalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Goal in the database
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        int databaseSizeBeforeDelete = goalRepository.findAll().size();

        // Get the goal
        restGoalMockMvc.perform(delete("/api/goals/{id}", goal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Goal> goalList = goalRepository.findAll();
        assertThat(goalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Goal.class);
        Goal goal1 = new Goal();
        goal1.setId(1L);
        Goal goal2 = new Goal();
        goal2.setId(goal1.getId());
        assertThat(goal1).isEqualTo(goal2);
        goal2.setId(2L);
        assertThat(goal1).isNotEqualTo(goal2);
        goal1.setId(null);
        assertThat(goal1).isNotEqualTo(goal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoalDTO.class);
        GoalDTO goalDTO1 = new GoalDTO();
        goalDTO1.setId(1L);
        GoalDTO goalDTO2 = new GoalDTO();
        assertThat(goalDTO1).isNotEqualTo(goalDTO2);
        goalDTO2.setId(goalDTO1.getId());
        assertThat(goalDTO1).isEqualTo(goalDTO2);
        goalDTO2.setId(2L);
        assertThat(goalDTO1).isNotEqualTo(goalDTO2);
        goalDTO1.setId(null);
        assertThat(goalDTO1).isNotEqualTo(goalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(goalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(goalMapper.fromId(null)).isNull();
    }
}
