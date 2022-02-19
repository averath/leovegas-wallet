package com.leovegas.wallet.controller


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.leovegas.wallet.IntegrationSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

class PlayerControllerIntegrationTest extends IntegrationSpecification {
    @Autowired
    private MockMvc mvc

    def "post: '/players' when performed then create player with new id"() {
        expect:
            mvc.perform(post("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(1))
                .andExpect(jsonPath('$.balance').value(0))
    }
}
