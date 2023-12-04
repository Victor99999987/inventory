package ru.petproject.inventory.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDateTime movementDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_owner_id", nullable = false)
    private User fromOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_owner_id", nullable = false)
    private User toOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_user_id", nullable = false)
    private User fromUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_id", nullable = false)
    private User toUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_department_id", nullable = false)
    private Department fromDepartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_department_id", nullable = false)
    private Department toDepartment;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "movements_items",
            joinColumns = {@JoinColumn(name = "movement_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")}
    )
    private Set<Item> items = new HashSet<>();
}
