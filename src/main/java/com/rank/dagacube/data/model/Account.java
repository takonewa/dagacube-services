package com.rank.dagacube.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@ToString(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double balance;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    private Player player;
    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

    public boolean hasEnoughFunds(double amount) {
        return (this.balance >= amount);
    }
}
