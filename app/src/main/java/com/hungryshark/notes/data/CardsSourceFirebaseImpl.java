package com.hungryshark.notes.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardsSourceFirebaseImpl implements CardsSource {

    private static final String CARDS_COLLECTION = "notes";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(CARDS_COLLECTION);
    private List<CardNote> cardsData = new ArrayList<>();

    @Override
    public CardsSource init(final CardsSourceResponse cardsSourceResponse) {
        collection.orderBy(CardDataMapping.Fields.TITLE, Query.Direction.ASCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cardsData = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            CardNote cardData = CardDataMapping.toCardData(id, doc);
                            cardsData.add(cardData);
                        }
                        cardsSourceResponse.initialized(CardsSourceFirebaseImpl.this);
                    }
                })
                .addOnFailureListener(e -> {
                });
        return this;
    }

    public CardNote getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null) {
            return 0;
        }
        return cardsData.size();
    }

    public void addCardData(final CardNote cardData) {
        collection.add(CardDataMapping.toDocument(cardData)).addOnSuccessListener(documentReference -> cardData.setId(documentReference.getId()));
    }

    @Override
    public void clearCardData() {

    }

    public void updateCardData(int position, CardNote cardData) {
        String id = cardData.getId();
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }

    public void deleteCardData(int position) {
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }
}