package pl.khuzzuk.battles.cards;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Wither;
import pl.khuzzuk.battles.equipment.Equipment;
import pl.khuzzuk.battles.model.Reach;
import pl.khuzzuk.battles.model.Speed;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class Card {
    private String name;
    private int strength;
    private int defence;
    private Speed speed;
    private Reach reach;
    @Wither
    private int morale;
    private int cost;
    private List<Equipment> equipment;
    private CardStyle style;
}
