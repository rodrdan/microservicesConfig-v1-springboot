package cz.rodr.cards.service.impl;

import cz.rodr.cards.constants.CardConstants;
import cz.rodr.cards.dto.CardDto;
import cz.rodr.cards.entity.Card;
import cz.rodr.cards.exceptions.CardNotFoundException;
import cz.rodr.cards.exceptions.MobileNumberAlreadyRegisteredException;
import cz.rodr.cards.mapper.CardMapper;
import cz.rodr.cards.repository.CardRepository;
import cz.rodr.cards.service.ICardService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private CardRepository cardRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> cardOptional = cardRepository.findByMobileNumber(mobileNumber);
        if (cardOptional.isPresent()) {
            throw new MobileNumberAlreadyRegisteredException(
                    "Card with mobile number " + mobileNumber + " already exists.");
        }
        Card newCard = createNewCard(mobileNumber);
        cardRepository.save(newCard);
    }

    @Override
    public CardDto getCardDetails(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
            new CardNotFoundException("Card with mobile number " + mobileNumber + " was not found."));
        return CardMapper.mapToCardDto(card, new CardDto());
    }

    @Override
    @Transactional
    public boolean updateCard(CardDto cardDto) {
        Optional<Card> cardOptional = cardRepository.findByCardNumber(cardDto.getCardNumber());
        if (cardOptional.isEmpty()) {
            throw new CardNotFoundException(
                    "Card with number " + cardDto.getCardNumber() + " was not found.");
        }
        Card card = CardMapper.mapToCard(cardDto, cardOptional.get());
        cardRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new CardNotFoundException("Card with mobile number " + mobileNumber + " was not found."));
        cardRepository.delete(card);
        return true;
    }

    private Card createNewCard(String mobileNumber) {
        Card card = new Card();
        card.setMobileNumber(mobileNumber);
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomCardNumber));
        card.setCardType(CardConstants.CREDIT_CARD);
        card.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return card;
    }
}
