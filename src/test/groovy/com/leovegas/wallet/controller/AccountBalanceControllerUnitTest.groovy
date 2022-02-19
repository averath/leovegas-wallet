package com.leovegas.wallet.controller

import spock.lang.Specification

class AccountBalanceControllerUnitTest extends Specification {
    def subject = new AccountBalanceController()

    def "getBalance return my balance"() {
        expect:
            subject.getBalance() == 'My balance'
    }
}
