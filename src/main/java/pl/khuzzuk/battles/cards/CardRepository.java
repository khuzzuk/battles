package pl.khuzzuk.battles.cards;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.khuzzuk.battles.model.Reach;
import pl.khuzzuk.battles.model.Speed;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardRepository {
    private Map<String, Card> cards;
    public static final String COMITATENSES_V_MACEDONIA = "Com. V Maced.";
    public static final String AUX_P_CORNUTI = "Aux.P.Cornuti";
    public static final String AUX_PALATINA = "Auxilia Palatina";
    public static final String CATAPHRACTARII = "Cataphractarii";
    public static final String CLIBANARII = "Clibanarii";
    public static final String CLIBANARII_PARTHII = "Clibanarii Parthii";
    public static final String COMITES_CLIBANARII = "Comites Clibanarii";
    public static final String EQUITES_DALMATAE = "Equites Dalmatae";
    public static final String EQUITES_SAGITARII = "Equites Sagitarii";
    private CardStyleRepository styleRepository;

    public static CardRepository get() {
        CardRepository repository = new CardRepository();
        try {
            repository.styleRepository = CardStyleRepository.get();
            repository.loadCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return repository;
    }

    private void loadCards() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("cards/cards.dat"))){
            cards = bufferedReader.lines()
                    .filter(line -> !line.startsWith("#"))
                    .map(line -> line.split(";"))
                    .map(this::from)
                    .collect(Collectors.toMap(Card::getName, card -> card));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Card from(String[] data) {
        return Card.builder()
                .name(data[0])
                .strength(Integer.valueOf(data[1]))
                .defence(Integer.valueOf(data[2]))
                .speed(Speed.valueOf(data[3]))
                .reach(Reach.valueOf(data[4]))
                .morale(Integer.valueOf(data[5]))
                .cost(Integer.valueOf(data[6]))
                .style(styleRepository.get(data[8]))
                .build();
    }

    public Card getCard(String name) {
        return cards.computeIfAbsent(name, key -> Card.builder().name(key).build());
    }
}
