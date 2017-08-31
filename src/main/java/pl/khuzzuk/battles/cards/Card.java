package pl.khuzzuk.battles.cards;

import lombok.Builder;
import lombok.Getter;
import pl.khuzzuk.battles.equipment.Equipment;
import pl.khuzzuk.battles.model.Speed;

import java.util.List;

@Builder
@Getter
public class Card {
    private String name;
    private int strength;
    private int defence;
    private Speed speed;
    private int cost;
    private List<Equipment> equipment;
    private CardStyle style;
}
