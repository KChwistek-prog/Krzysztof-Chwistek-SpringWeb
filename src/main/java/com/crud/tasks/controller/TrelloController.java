package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.Optional;

//@RestController
@RequestMapping("/v1/trello")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloClient trelloClient;
    private final TrelloBoardDto trelloBoardDto;

    @GetMapping("getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

            trelloBoards.forEach(trelloBoardDto -> {
                if(trelloBoardDto.getId() != null && !trelloBoardDto.getName().isEmpty() && trelloBoardDto.getName().contains("Kodilla")) {
                    System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                }
            });

            System.out.println("This board contains lists: ");
            Optional.of(trelloBoards).stream()
                    .map(Optional::ofNullable)
                    .map(Optional::get)
                    .map(e -> trelloBoardDto)
                    .forEach(trelloBoardDto -> {
                        if(trelloBoardDto.getId() != null && !trelloBoardDto.getName().isEmpty() && trelloBoardDto.getName().contains("Kodilla")) {
                            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                        }
                    });

//            System.out.println("This board contains lists: ");
//            trelloBoardDto.getLists().forEach(trelloList -> {
//                System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
//            });

    }

    @PostMapping("createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }



}
