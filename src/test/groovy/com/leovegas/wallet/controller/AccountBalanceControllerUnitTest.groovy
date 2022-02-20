package com.leovegas.wallet.controller

import com.leovegas.wallet.exception.PlayerNotFoundException
import com.leovegas.wallet.model.Player
import com.leovegas.wallet.repository.PlayerRepository
import spock.lang.Specification

class AccountBalanceControllerUnitTest extends Specification {
    def playerRepository = Mock(PlayerRepository)
    def subject = new AccountBalanceController(playerRepository)

    def "getBalance return balance of player"() {
        given:
            def player = new Player(id: 1, balance: 100)
            playerRepository.findById(1) >> Optional.of(player)
        expect:
            subject.getBalance(1) == 100
    }

    def "getBalance when player does not exist then throw player not found exception"() {
        given:
            playerRepository.findById(1) >> Optional.empty()
        when:
            subject.getBalance(1)
        then:
            def e = thrown(PlayerNotFoundException)
            e.message == 'Player with given id does not exist'
    }
}
