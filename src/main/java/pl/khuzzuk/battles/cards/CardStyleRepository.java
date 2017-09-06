package pl.khuzzuk.battles.cards;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardStyleRepository {
    Map<String, CardStyle> cardStyles;

    public static CardStyleRepository get() throws IOException {
        CardStyleRepository repository = new CardStyleRepository();
        HashMap<String, CardStyle> styles = new HashMap<>();
        BufferedReader reader = Files.newBufferedReader(Paths.get("cards/styles.dat"));
        List<String> lines = reader.lines()
                .collect(Collectors.toList());
        reader.close();
        lines.stream().map(s -> s.split(";"))
                .forEach(line -> styles.put(line[0], new CardStyle(line[1])));
        repository.cardStyles = styles;
        return repository;
    }

    CardStyle get(String name) {
        return cardStyles.get(name);
    }
}
