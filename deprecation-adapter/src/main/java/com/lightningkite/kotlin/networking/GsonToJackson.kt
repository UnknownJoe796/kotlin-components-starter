@file:JvmName("Deprecated")
@file:JvmMultifileClass

package com.lightningkite.kotlin.networking

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.lightningkite.kotlin.networking.jackson.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody


@Deprecated("Migration to Jackson", ReplaceWith(
        "this.toJackson()",
        "com.lightningkite.kotlin.networking.jackson.toJackson"
))
fun Any?.toJsonElement(): JsonNode = toJackson()

@Deprecated("Migration to Jackson", ReplaceWith(
        "this.toJacksonArray()",
        "com.lightningkite.kotlin.networking.jackson.toJacksonArray"
))
fun <E> List<E>.toJsonArray(): ArrayNode = toJacksonArray()

@Deprecated("Migration to Jackson", ReplaceWith(
        "this.jacksonToNode()",
        "com.lightningkite.kotlin.networking.jackson.jacksonToNode"
))
fun Any.gsonToJson(): JsonNode = jacksonToNode()

@Deprecated("Migration to Jackson", ReplaceWith(
        "this.jacksonToString()",
        "com.lightningkite.kotlin.networking.jackson.jacksonToString"
))
fun Any.gsonToString(): String = jacksonToString()

@Deprecated("Migration to Jackson", ReplaceWith(
        "this.jacksonFromString<T>()",
        "com.lightningkite.kotlin.networking.jackson.jacksonFromString"
))
inline fun <reified T : Any> String.gsonFrom(): T? = jacksonFromString()

@Deprecated("Migration to Jackson", ReplaceWith(
        "this.jacksonFromString<T>(type)",
        "com.lightningkite.kotlin.networking.jackson.jacksonFromString"
))
fun <T : Any> String.gsonFrom(type: Class<T>): T? = jacksonFromString(type)


@Deprecated("Migration to Jackson", ReplaceWith(
        "jacksonObject(*pairs)",
        "com.lightningkite.kotlin.networking.jackson.jacksonObject"
))
fun jsonObject(vararg pairs: Pair<String, Any>): ObjectNode = jacksonObject(*pairs)

@Deprecated("Migration to Jackson", ReplaceWith(
        "jacksonArray(*elements)",
        "com.lightningkite.kotlin.networking.jackson.jacksonArray"
))
fun jsonArray(vararg elements: Any?): ArrayNode = jacksonArray(*elements)


@Deprecated("Migration to Jackson", ReplaceWith(
        "this.lambdaJackson<T>(type)",
        "com.lightningkite.kotlin.networking.jackson.lambdaJackson"
))
inline fun <reified T : Any> Request.Builder.lambdaGson(client: OkHttpClient = defaultClient) = lambdaJackson<T>(client)

@Deprecated("Migration to Jackson", ReplaceWith(
        "this.lambdaJackson<T>(type)",
        "com.lightningkite.kotlin.networking.jackson.lambdaJackson"
))
fun <T : Any> Request.Builder.lambdaGson(client: OkHttpClient = defaultClient, type: TypeReference<T>) = lambdaJackson(type, client)


@Deprecated("Migration to Jackson", ReplaceWith(
        "this.jacksonToRequestBody()",
        "com.lightningkite.kotlin.networking.jackson.jacksonToRequestBody"
))
fun <T : Any> T.gsonToRequestBody(): RequestBody = jacksonToRequestBody()