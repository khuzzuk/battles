package pl.khuzzuk.battles;

public interface EventTypes {
    interface Container {
        String GET_CARD_REPO = "GET_CARD_REPO";
        String GET_PLAY_STAGE = "GET_PLAY_STAGE";
    }

    interface Stages {
        String FORMATION_READY = "FORMATION_READY";
        String BATTLE_TABLE_READY = "BATTLE_TABLE_READY";
        String BATTLE_START_PLAYER = "BATTLE_START_PLAYER";
        String BATTLE_START_OPPONENT = "BATTLE_START_OPPONENT";
    }
}
