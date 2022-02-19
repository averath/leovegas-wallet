package com.leovegas.wallet.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
@WebMvcTest
class AccountBalanceControllerIntegrationTest extends Specification {
    @Autowired
    private MockMvc mvc

    def "get: '/' when performed then the response has status 200 and content is 'My balance'"() {
        expect:
            mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == "My balance"
    }
}
