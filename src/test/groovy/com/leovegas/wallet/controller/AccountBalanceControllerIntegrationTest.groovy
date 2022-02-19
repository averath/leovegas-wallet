package com.leovegas.wallet.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.leovegas.wallet.IntegrationSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

class AccountBalanceControllerIntegrationTest extends IntegrationSpecification {
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
