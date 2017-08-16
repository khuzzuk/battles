package pl.khuzzuk.battles.cards;

import lombok.Builder;
import pl.khuzzuk.battles.equipment.Equipment;
import pl.khuzzuk.battles.model.Speed;

import java.util.List;

@Builder
public class Card {
    private int strength;
    private int defence;
    private Speed speed;
    private int cost;
    private List<Equipment> equipment;
}
