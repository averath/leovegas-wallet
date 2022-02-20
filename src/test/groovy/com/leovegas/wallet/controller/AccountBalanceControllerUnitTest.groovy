package com.leovegas.wallet.controller

import com.leovegas.wallet.repository.PlayerRepository
import spock.lang.Specification

class AccountBalanceControllerUnitTest extends Specification {
    def playerRepository = Mock(PlayerRepository)
    def subject = new AccountBalanceController(playerRepository)

    def "getBalance return my balance"() {
        expect:
            subject.getBalance() == 'My balance'
    }
}
