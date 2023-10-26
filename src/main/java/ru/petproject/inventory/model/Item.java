package ru.petproject.inventory.model;

import java.time.LocalDate;

public class Item {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private boolean serviceable;
    private String invNumber;
    private LocalDate created;
    private LocalDate finished;
}
