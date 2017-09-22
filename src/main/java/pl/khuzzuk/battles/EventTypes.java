package pl.khuzzuk.battles;

public interface EventTypes {
    enum Container {
        GET_CARD_REPO, GET_PLAY_STAGE, GET_CARD_SELECTION_CONTROLLER, GET_OPPONENT_CARD_SELECTION_CONTROLLER
    }

    enum Stages {
        FORMATION_READY, BATTLE_TABLE_READY, BATTLE_START_PLAYER, BATTLE_START_OPPONENT
    }

    enum User {
        SELECT_CARD, SELECT_OPPONENT_CARD
    }
}
