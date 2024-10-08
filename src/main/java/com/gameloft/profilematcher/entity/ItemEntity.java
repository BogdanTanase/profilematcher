package com.gameloft.profilematcher.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String itemName;
    private Integer quantity;
}
