package ru.petproject.inventory.model;

import java.time.LocalDate;

public class Moving {
    private Long id;
    private LocalDate movingDate;
    private Item item;
    private User fromOwner;
    private User toOwner;
    private Department fromDepartment;
    private Department toDepartment;
    private String description;
}
