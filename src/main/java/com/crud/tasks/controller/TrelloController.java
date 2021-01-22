package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.services.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloService trelloService;
    private final TrelloClient trelloClient;

    @GetMapping("getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloService.fetchTrelloBoards();

        Optional.of(trelloBoards).get().forEach(trelloBoardDto -> {
            if (trelloBoardDto.getId() != null && !trelloBoardDto.getName().isEmpty()) {
                System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                System.out.println("This board contains lists: ");
                trelloBoardDto.getLists().forEach(trelloList -> {
                    System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
                });
            }
        });
        return trelloBoards;
    }

    @PostMapping("createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloService.createTrelloCard(trelloCardDto);
        //return trelloClient.createNewCard(trelloCardDto);
    }
}
