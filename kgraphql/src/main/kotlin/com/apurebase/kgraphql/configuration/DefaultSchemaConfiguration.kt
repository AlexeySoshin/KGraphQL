package com.apurebase.kgraphql.configuration

import com.apurebase.kgraphql.schema.execution.Executor
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineDispatcher

open class DefaultSchemaConfiguration(
    override val useCachingDocumentParser: Boolean,
    override val documentParserCacheMaximumSize: Long,
    override val objectMapper: ObjectMapper,
    override val useDefaultPrettyPrinter: Boolean,
    override val coroutineDispatcher: CoroutineDispatcher,
    override val executor: Executor,
    override val timeout: Long?
) : SchemaConfiguration
