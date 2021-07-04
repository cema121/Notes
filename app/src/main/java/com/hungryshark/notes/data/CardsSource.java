package com.hungryshark.notes.data;

public interface CardsSource {
    CardsSource init(CardsSourceResponse cardsSourceResponse);

    CardNote getCardData(int position);

    int size();

    void deleteCardData(int position);

    void updateCardData(int position, CardNote cardData);

    void addCardData(CardNote cardData);

    void clearCardData();

}

