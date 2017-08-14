package pl.khuzzuk.battles.cards

import groovy.transform.CompileStatic
import lombok.Builder
import pl.khuzzuk.battles.equipment.Equipment
import pl.khuzzuk.battles.model.Speed

@Builder
@CompileStatic
class Card {
    int strength
    int defence
    Speed speed
    int cost
    List<Equipment> equipment
}
