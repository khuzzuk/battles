package pl.khuzzuk.battles;

public interface EventTypes {
    interface Container {
        String GET_CARD_REPO = "GET_CARD_REPO";
    }

    interface Stages {
        String FORMATION_READY = "FORMATION_READY";
        String BATTLE_START_PLAYER = "BATTLE_START_PLAYER";
        String BATTLE_START_OPPONENT = "BATTLE_START_OPPONENT";
    }
}
