package com.apurebase.kgraphql

import com.apurebase.kgraphql.configuration.SchemaConfiguration
import com.apurebase.kgraphql.schema.Schema
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder


class KGraphQL {
    companion object {
        fun schema(init: SchemaBuilder.() -> Unit): Schema<SchemaConfiguration> {
            return SchemaBuilder()
                .apply(init)
                .build()
        }
    }
}
