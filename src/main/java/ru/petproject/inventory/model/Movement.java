package ru.petproject.inventory.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_owner_id")
    private User fromOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_owner_id")
    private User toOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_department_id")
    private Department fromDepartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_department_id")
    private Department toDepartment;

    @Column(name = "description")
    private String description;
}
