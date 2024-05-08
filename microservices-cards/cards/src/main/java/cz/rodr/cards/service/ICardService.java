package cz.rodr.cards.service;

import cz.rodr.cards.dto.CardDto;

public interface ICardService {

    /**
     *
     * @param mobileNumber - Mobile number to register with a new card
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber - Mobile number attached to card
     * @return CardDto object
     */
    CardDto getCardDetails(String mobileNumber);

    /**
     *
     * @param cardDto - CardDto object
     * @return - boolean indicating if card was updated successfully
     */
    boolean updateCard(CardDto cardDto);

    /**
     *
     * @param mobileNumber - Mobile number attached to card
     * @return - boolean indicating if card was deleted successfully
     */
    boolean deleteCard(String mobileNumber);
}
