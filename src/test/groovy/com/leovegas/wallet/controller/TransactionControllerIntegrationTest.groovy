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
class TransactionControllerIntegrationTest extends IntegrationSpecification {
    @Autowired
    private MockMvc mvc

    void setup() {
        mvc.perform(post("/players"))
    }

    def "post: '/transactions/1' when value is greater than 0 then add money to player"() {
        given:
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(0))
        when:
            def result = mvc.perform(post("/transactions/1")
                .param('playerId', '1')
                .param('value', '2'))
        then:
            result.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(1))
                .andExpect(jsonPath('$.playerId').value(1))
                .andExpect(jsonPath('$.value').value(2))
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(2))
    }

    def "post: '/transactions/1' when value is lower than 0 and player has enough money then subtract value from player wallet"() {
        given:
            mvc.perform(post("/transactions/1")
                .param('playerId', '1')
                .param('value', '20'))
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(20))
        when:
            def result = mvc.perform(post("/transactions/2")
                .param('playerId', '1')
                .param('value', '-1'))
        then:
            result.andExpect(status().isOk())
                .andExpect(jsonPath('$.id').value(2))
                .andExpect(jsonPath('$.playerId').value(1))
                .andExpect(jsonPath('$.value').value(-1))
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(19))
    }

    def "post: '/transactions/1' when value is lower than 0 and player has not enough money then do not subtract value from player wallet"() {
        given:
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(0))
        when:
            def result = mvc.perform(post("/transactions/1")
                .param('playerId', '1')
                .param('value', '-100'))
        then:
            result.andExpect(status().is(400))
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(0))
    }

    def "post: '/transactions/1' when value is 0 then do not create transaction"() {
        given:
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(0))
        when:
            def result = mvc.perform(post("/transactions/1")
                .param('playerId', '1')
                .param('value', '0'))
        then:
            result.andExpect(status().is(400))
            mvc.perform(get('/players/1')).andExpect(jsonPath('$.balance').value(0))
    }

    def "post: '/transactions' when transaction id is not provided then return 405"() {
        expect:
            mvc.perform(post("/transactions")
                .param('playerId', '1')
                .param('value', '10'))
                .andExpect(status().is(405))
    }

    def "post: '/transactions' when value is not provided then return 400"() {
        expect:
            mvc.perform(post("/transactions/1")
                .param('playerId', '1'))
                .andExpect(status().is(400))
    }

    def "post: '/transactions' when playerId is not provided then return 400"() {
        expect:
            mvc.perform(post("/transactions/1")
                .param('value', '1'))
                .andExpect(status().is(400))
    }

    def "post: '/transactions/1' when transaction id is not unique then return 400"() {
        given:
            mvc.perform(post("/transactions/1")
                .param('playerId', '1')
                .param('value', '10'))
        expect:
            mvc.perform(post("/transactions/1")
                .param('playerId', '1')
                .param('value', '10'))
                .andExpect(status().is(400))
    }

    def "post: '/transactions/1' when player does not exist then return 404"() {
        expect:
            mvc.perform(post("/transactions/1")
                .param('playerId', '0')
                .param('value', '10'))
                .andExpect(status().is(404))
    }

    def "get: '/transactions' when called then return player's transactions history"() {
        given: "create some transactions"
            mvc.perform(post("/transactions/1")
                .param('playerId', '1')
                .param('value', '10'))
                .andExpect(status().isOk())
            mvc.perform(post("/transactions/2")
                .param('playerId', '1')
                .param('value', '100'))
                .andExpect(status().isOk())
            mvc.perform(post("/transactions/3")
                .param('playerId', '1')
                .param('value', '1000'))
                .andExpect(status().isOk())
        expect:
            mvc.perform(get('/transactions').param('playerId', '1'))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$[0].id').value(1))
                .andExpect(jsonPath('$[0].playerId').value(1))
                .andExpect(jsonPath('$[0].value').value(10))
                .andExpect(jsonPath('$[1].id').value(2))
                .andExpect(jsonPath('$[1].playerId').value(1))
                .andExpect(jsonPath('$[1].value').value(100))
                .andExpect(jsonPath('$[2].id').value(3))
                .andExpect(jsonPath('$[2].playerId').value(1))
                .andExpect(jsonPath('$[2].value').value(1000))
    }

    def "get: '/transactions' when player id does not exist then return 400"() {
        expect:
            mvc.perform(get('/transactions').param('playerId', '-1'))
                .andExpect(status().is4xxClientError())
    }

    def "get: '/transactions' when player id is not provided then return 400"() {
        expect:
            mvc.perform(get('/transactions'))
                .andExpect(status().is4xxClientError())
    }
}
