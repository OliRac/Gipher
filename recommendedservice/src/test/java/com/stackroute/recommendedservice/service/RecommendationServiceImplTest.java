package com.stackroute.recommendedservice.service;

import com.stackroute.recommendedservice.entity.UserTerms;
import com.stackroute.recommendedservice.exception.UserNotFoundException;
import com.stackroute.recommendedservice.repository.UserTermsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceImplTest {

    @Mock
    private UserTermsRepository userTermsRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
        userTermsRepository.deleteAll();
    }

    @Test
    public void saveUserTermsShouldReturnUserTerms() {
        UserTerms mockUserTerms = new UserTerms();
        mockUserTerms.setTerms(new HashMap<String, Integer>());
        when(userTermsRepository.save(any())).thenReturn(mockUserTerms);
        UserTerms result = recommendationService.saveUserTerms(mockUserTerms);
        assertEquals(mockUserTerms, result);
    }

    @Test
    public void addTermShouldReturnTermForExistingUser() {
        String termToAdd = "term";
        UserTerms mockUserTerms = new UserTerms();
        mockUserTerms.setTerms(new HashMap<String, Integer>());
        when(userTermsRepository.findUserTermsByUserId(anyInt())).thenReturn(mockUserTerms);
        String result = recommendationService.addTerm(1, termToAdd);
        assertEquals(termToAdd, result);
    }

    @Test
    public void addTermShouldReturnTermForNewUser() {
        String termToAdd = "term";
        when(userTermsRepository.findUserTermsByUserId(anyInt())).thenReturn(null);
        String result = recommendationService.addTerm(1, termToAdd);
        assertEquals(termToAdd, result);
    }

    @Test
    public void getTrendingShouldReturnAString() {
        mockRestTemplateExchange("body");
        assertNotNull(recommendationService.getTrending());
    }

    @Test
    public void getRecommendationShouldThrowUserDoesNotExistException() {
        assertThrows(UserNotFoundException.class, () -> {
            recommendationService.getRecommendation(0);
        });
    }

    @Test
    public void getRecommendationShouldReturnRecommendations() {
        /*mock userTerm*/
        UserTerms mockUserTerms = new UserTerms();
        mockUserTerms.setUserId(0);
        mockUserTerms.setTerms(new HashMap<>());
        when(userTermsRepository.findUserTermsByUserId(anyInt())).thenReturn(mockUserTerms);

        /*mock term/suggestion data for proper unit test*/
        String [] sortedTerms = {"term1", "term2", "term3"};
        var termsToUse = List.of("termToUse1", "termToUse2");
        var suggestedTerms = List.of("suggestion1", "suggestion2","suggestion3","suggestion4");

        /*Need to use a spy in order to prevent outside method calls*/
        RecommendationServiceImpl spyService = Mockito.spy(recommendationService);

        doReturn(sortedTerms).when(spyService).getSortedTerms(anyMap());
        doReturn(termsToUse).when(spyService).getTermsToUse(any(), anyInt());
        doReturn(suggestedTerms).when(spyService).getSuggestedSearchTerms(anyList(), anyInt());

        /*mock rest template exchange*/
        mockRestTemplateExchange("body");

        String result = spyService.getRecommendation(0);

        /*For each suggestion, a restTemplate.exchange call is made
        * these are currently mocked in a way that their body is just comprised of the string "body"
        * thus, the expected result is "[body,body,body,body]"*/
        assertEquals("[body,body,body,body]", result);
    }

    @Test
    public void queryTenorShouldReturnBodyNoLimit() {
        mockRestTemplateExchange("body");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String result = recommendationService.queryTenor("query", HttpMethod.GET, headers, -1);
        assertEquals("body", result);
    }

    @Test
    public void queryTenorShouldReturnBodyWithLimit() {
        mockRestTemplateExchange("body");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String result = recommendationService.queryTenor("query", HttpMethod.GET, headers, 5);
        assertEquals("body", result);
    }

    @Test
    public void getSortedTermsShouldReturnSortedStringArray() {
        //could add more cases...
        var termsMap = Map.of("a", 3, "b", 2, "c", 1, "d", 0);
        String [] expected = {"d", "c", "b", "a"};
        var result = recommendationService.getSortedTerms(termsMap);
        assertArrayEquals(expected, result);
    }

    @Test
    public void getTermsToUseShouldReturnStringListOfMostUsed() {
        String [] sortedTerms = {"term1", "term2", "term3"};
        var result = recommendationService.getTermsToUse(sortedTerms, 2);
        var expected = List.of("term3", "term2");
        assertEquals(expected,result);
    }

    @Test
    public void getTermsToUseShouldReturnInputIfSmallerThanMax() {
        String [] sortedTerms = {"term1", "term2", "term3"};
        var result = recommendationService.getTermsToUse(sortedTerms, 4);
        var expected = List.of("term1", "term2", "term3");
        assertEquals(expected,result);
    }

    @Test
    public void getSuggestedSearchTermsShouldReturnTwoPerTerm() {
        var terms = List.of("term1", "term2");
        mockRestTemplateExchange("{'results':['one', 'two', 'three', 'four']}");
        var result = recommendationService.getSuggestedSearchTerms(terms, 2);
        //method will fetch 2 suggestions out of the 4 tenor responds with (for each term)
        var expected = List.of("one", "two", "one", "two");
        assertEquals(expected, result);
    }

    @Test
    public void getSuggestedSearchTermsShouldReturnOnePerTerm() {
        var terms = List.of("term1", "term2");
        mockRestTemplateExchange("{'results':['one']}");
        var result = recommendationService.getSuggestedSearchTerms(terms, 2);
        //if there are ever not enough suggestions, method will just add all to result
        var expected = List.of("one", "one");
        assertEquals(expected, result);
    }

    /*Helper to mock a call to restTemplate.exchange()*/
    private void mockRestTemplateExchange(String returnString) {
        var response = new ResponseEntity<String>(returnString, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), ArgumentMatchers.<Class<String>>any())).thenReturn(response);
    }
}