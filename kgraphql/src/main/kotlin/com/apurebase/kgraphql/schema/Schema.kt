package com.apurebase.kgraphql.schema

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.configuration.SchemaConfiguration
import com.apurebase.kgraphql.schema.introspection.__Schema
import kotlinx.coroutines.runBlocking

interface Schema<CONF: SchemaConfiguration> : __Schema {

    val configuration: CONF

    suspend fun execute(request: String, variables: String?, context: Context = Context(emptyMap())) : String
    fun executeBlocking(request: String, variables: String? = null, context: Context = Context(emptyMap()))
            = runBlocking { execute(request, variables, context) }


    @Deprecated("This will be removed in future release", ReplaceWith("execute(request, null, context)"))
    suspend fun execute(request: String, context: Context = Context(emptyMap())) = execute(request, null, context)

    @Deprecated("This will be removed in future release", ReplaceWith("executeBlocking(request, variables"))
    fun executeBlocking(request: String, variables: String?) = runBlocking { execute(request, variables) }
}
