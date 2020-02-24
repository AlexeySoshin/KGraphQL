package com.apurebase.kgraphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import io.ktor.application.install
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.routing.Routing
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import me.lazmaid.kraph.Kraph
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class KtorFeatureTest {

    private fun withServer(block: SchemaBuilder.() -> Unit): (Kraph.() -> Unit) -> String {
        return {
            withTestApplication({
                install(Routing) {
                    graphql { block() }
                }
            }) {
                handleRequest {
                    uri = "graphql"
                    method = HttpMethod.Post
                    addHeader(HttpHeaders.ContentType, "application/json;charset=UTF-8")
                    setBody(Kraph { it(this) }.toRequestString())
                }.response.content!!
            }
        }
    }

    @Test
    fun `Some random test`() {
        val server = withServer {
            query("hello") {
                resolver { -> "World!" }
            }
        }

        server {
            query {
                field("hello")
            }
        } shouldBeEqualTo "{\"data\":{\"hello\":\"World!\"}}"

    }
}
