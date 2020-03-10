package com.apurebase.kgraphql.configuration

import com.apurebase.kgraphql.schema.execution.Executor
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineDispatcher

class KtorSchemaConfiguration(
    useCachingDocumentParser: Boolean,
    documentParserCacheMaximumSize: Long,
    objectMapper: ObjectMapper,
    useDefaultPrettyPrinter: Boolean,
    coroutineDispatcher: CoroutineDispatcher,
    executor: Executor,
    timeout: Long
): DefaultSchemaConfiguration(
    useCachingDocumentParser,
    documentParserCacheMaximumSize,
    objectMapper,
    useDefaultPrettyPrinter,
    coroutineDispatcher,
    executor,
    timeout
)
