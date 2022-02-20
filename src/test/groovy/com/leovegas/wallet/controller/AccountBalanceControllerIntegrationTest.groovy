package com.leovegas.wallet.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.leovegas.wallet.IntegrationSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

class AccountBalanceControllerIntegrationTest extends IntegrationSpecification {
    @Autowired
    private MockMvc mvc

    def "get: '/player/1/balance' returns player balance"() {
        given:
            mvc.perform(post('/players'))
        expect:
            mvc.perform(get("/players/1/balance"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == "0"
    }

    def "get: '/player/1/balance' when player does not exist then return 404"() {
        expect:
            mvc.perform(get("/players/2/balance"))
                .andExpect(status().is(404))
    }
}
