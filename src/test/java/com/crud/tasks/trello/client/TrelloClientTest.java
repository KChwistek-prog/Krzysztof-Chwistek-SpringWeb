package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {
    TrelloMapper trelloMapper = new TrelloMapper();

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Test
    void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("test");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        // When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        // Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    void shouldCreateCard() throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test Description",
                "top",
                "test_id"
        );
        URI uri = new URI("http://test.com/cards?key&token&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "test task",
                "http://test.com",
                null
        );

        when(restTemplate.postForObject(uri, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);
        // When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        // Then
        assertEquals("1", newCard.getId());
        assertEquals("test task", newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    void shouldReturnEmptyList() throws URISyntaxException{
        //Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
        when(trelloConfig.getTrelloUser()).thenReturn("test");
        URI uri = new URI("http://test.com/members/test/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(0, trelloBoards.size());
    }

    @Test
    void testMapToBoard(){
        //given
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "mapToBoardTestName", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "mapToBoardTestName2", new ArrayList<>());
        List<TrelloBoardDto> list = new ArrayList<>();
        list.add(trelloBoardDto1);
        list.add(trelloBoardDto2);
        //when
        List<TrelloBoard> result = trelloMapper.mapToBoards(list);
        //then
        assertEquals("mapToBoardTestName", result.get(0).getName());
        assertEquals("mapToBoardTestName2", result.get(1).getName());
    }

    @Test
    void testMapToBoardsDto(){
        //given
        TrelloBoard trelloBoard1 = new TrelloBoard("1", "mapToBoardsDtoTestName", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "mapToBoardsDtoTestName2", new ArrayList<>());
        List<TrelloBoard> list = new ArrayList<>();
        list.add(trelloBoard1);
        list.add(trelloBoard2);
        //when
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(list);
        //then
        assertEquals("mapToBoardsDtoTestName", result.get(0).getName());
        assertEquals("mapToBoardsDtoTestName2", result.get(1).getName());
    }

    @Test
    void testMapToList(){
        //given
        TrelloListDto trelloListDto = new TrelloListDto("1", "mapToListTestName",true);
        TrelloListDto trelloListDto2 = new TrelloListDto("2", "mapToListTestName2", true);
        List<TrelloListDto> list = new ArrayList<>();
        list.add(trelloListDto);
        list.add(trelloListDto2);
        //when
        List<TrelloList> result = trelloMapper.mapToList(list);
        //then
        assertEquals("mapToListTestName", result.get(0).getName());
        assertEquals("mapToListTestName2", result.get(1).getName());
    }

    @Test
    void testMapToListDto(){
        //given
        TrelloList trelloList1 = new TrelloList("1", "mapToListDtoTestName",true);
        TrelloList trelloList2 = new TrelloList("2", "mapToListDtoTestName2", true);
        List<TrelloList> list = new ArrayList<>();
        list.add(trelloList1);
        list.add(trelloList2);
        //when
        List<TrelloListDto> result = trelloMapper.mapToListDto(list);
        //then
        assertEquals("mapToListDtoTestName", result.get(0).getName());
        assertEquals("mapToListDtoTestName2", result.get(1).getName());
    }

    @Test
    void testMapToCardDto(){
        //given
        TrelloCard trelloCard = new TrelloCard("name","mapToCardDtoTestDescritpion", "top", "1");
        //when
        TrelloCardDto result = trelloMapper.mapToCardDto(trelloCard);
        //then
        assertEquals("mapToCardDtoTestDescritpion",result.getDescription());
    }

    @Test
    void testMapToCard(){
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name","mapToCardTestDescritpion", "top", "1");
        //when
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);
        //then
        assertEquals("mapToCardTestDescritpion",result.getDescription());
    }
}