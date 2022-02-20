package com.leovegas.wallet.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.leovegas.wallet.IntegrationSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    def "get: '/players/1' when player exists then return that player"() {
        given:
            mvc.perform(post("/players"))
        expect:
            mvc.perform(get("/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(1))
                .andExpect(jsonPath('$.balance').value(0))
    }

    def "get: '/players/2' when player does not exist then return 404 not found"() {
        expect:
            mvc.perform(get("/players/2"))
                .andExpect(status().is(404))
    }
}
