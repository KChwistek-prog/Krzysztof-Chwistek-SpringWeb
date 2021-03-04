package com.crud.tasks.domain;

import lombok.*;

@AllArgsConstructor
@Getter
public class TrelloCardDto {

    private final String name;
    private final String description;
    private final String pos;
    private final String listId;
}
