package com.apurebase.kgraphql.schema.dsl

import com.apurebase.kgraphql.configuration.DefaultSchemaConfiguration
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.apurebase.kgraphql.schema.execution.Executor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface SchemaConfigurationDSL {
    var useDefaultPrettyPrinter: Boolean
    var useCachingDocumentParser: Boolean
    var objectMapper: ObjectMapper
    var documentParserCacheMaximumSize: Long
    var acceptSingleValueAsArray: Boolean
    var coroutineDispatcher: CoroutineDispatcher
    var executor: Executor
    var timeout: Long?
}

open class DefaultSchemaConfigurationDSL: SchemaConfigurationDSL {
    override var useDefaultPrettyPrinter: Boolean = false
    override var useCachingDocumentParser: Boolean = true
    override var objectMapper: ObjectMapper = jacksonObjectMapper()
    override var documentParserCacheMaximumSize: Long = 1000L
    override var acceptSingleValueAsArray: Boolean = true
    override var coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
    override var executor: Executor = Executor.Parallel
    override var timeout: Long? = null

    internal fun update(block: DefaultSchemaConfigurationDSL.() -> Unit) = block()

    internal fun build(): DefaultSchemaConfiguration {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, acceptSingleValueAsArray)
        return DefaultSchemaConfiguration(
            useCachingDocumentParser,
            documentParserCacheMaximumSize,
            objectMapper,
            useDefaultPrettyPrinter,
            coroutineDispatcher,
            executor,
            timeout
        )
    }
}
