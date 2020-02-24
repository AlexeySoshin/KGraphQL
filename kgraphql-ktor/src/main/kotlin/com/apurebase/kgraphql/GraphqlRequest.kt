package com.apurebase.kgraphql

data class GraphqlRequest(
    val query: String?,
    val mutation: String?,
    val variables: Map<String, Any?>?
) {
    val finalQuery get() = query ?: mutation!!
}
