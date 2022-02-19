package com.leovegas.wallet.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ComponentsIntegrationTest extends Specification {
    @Autowired(required = false)
    AccountBalanceController accountBalanceController

    def "accountBalanceController is created"() {
        expect:
            accountBalanceController
    }
}
