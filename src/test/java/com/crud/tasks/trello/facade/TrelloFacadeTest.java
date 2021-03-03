package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.services.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchEmptyList() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.size()).isEqualTo(0);

        trelloBoardDtos.forEach(trelloBoardDto -> {

            assertThat(trelloBoardDto.getId()).isEqualTo("1");
            assertThat(trelloBoardDto.getName()).isEqualTo("test");

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertThat(trelloListDto.getId()).isEqualTo("1");
                assertThat(trelloListDto.getName()).isEqualTo("test_list");
                assertThat(trelloListDto.isClosed()).isFalse();
            });
        });
    }

    @Test
    void shouldFilterCard() {
        //given
        TrelloValidator validator = new TrelloValidator();
        TrelloCard trelloCard = new TrelloCard("test","description", "pos","1");
        //when
        validator.validateCard(trelloCard);
    }

    @Test
    void shouldFilterList(){
        //given
        TrelloValidator validator = new TrelloValidator();
        TrelloBoard trelloBoard = new TrelloBoard("1", "test", new ArrayList<>());
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);

        //when
        validator.validateTrelloBoards(trelloBoards);
    }

    @Test
    void shouldCreateCard(){
        // Given
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("test","test", "test", new BadgesDto());
        TrelloCardDto newTrelloCardDto = new TrelloCardDto("test","list","list","list");
        TrelloCard trelloCard = new TrelloCard("test", "test", "test","test");
        when(trelloService.createTrelloCard(newTrelloCardDto)).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(newTrelloCardDto);
        when(trelloMapper.mapToCard(newTrelloCardDto)).thenReturn(trelloCard);

        //When
        CreatedTrelloCardDto newCard = trelloFacade.createCard(newTrelloCardDto);

        //Then
        Assertions.assertEquals("test", newCard.getShortUrl());
    }


}
