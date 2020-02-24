package com.apurebase.kgraphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.charset
import io.ktor.request.contentType
import io.ktor.request.receiveStream
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import java.nio.charset.Charset


fun Route.graphql(block: SchemaBuilder.() -> Unit) {

    val schema = KGraphQL.schema(block)
    val gson = Gson()

    post("graphql") {
        val raw = context.receiveTextWithCorrectEncoding()
        val req = gson.fromJson<GraphqlRequest>(raw)

        // TODO: Support for Context!


        val result = schema.execute(req.finalQuery, gson.toJson(req.variables))

        call.respond(result)
    }
}




/**
 * Receive the request as String.
 * If there is no Content-Type in the HTTP header specified use ISO_8859_1 as default charset, see https://www.w3.org/International/articles/http-charset/index#charset.
 * But use UTF-8 as default charset for application/json, see https://tools.ietf.org/html/rfc4627#section-3
 *
 * Taken from github: https://github.com/ktorio/ktor/issues/384#issuecomment-458542686
 */
suspend fun ApplicationCall.receiveTextWithCorrectEncoding(): String {
    fun ContentType.defaultCharset(): Charset = when (this) {
        ContentType.Application.Json -> Charsets.UTF_8
        else -> Charsets.ISO_8859_1
    }

    val contentType = request.contentType()
    val suitableCharset = contentType.charset() ?: contentType.defaultCharset()
    return receiveStream().bufferedReader(charset = suitableCharset).readText()
}
