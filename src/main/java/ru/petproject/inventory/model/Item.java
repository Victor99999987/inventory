package ru.petproject.inventory.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "serviceable", nullable = false)
    private boolean serviceable;

    @Column(name = "inv_number", length = 50)
    private String invNumber;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "finished")
    private LocalDateTime finished;
}
