package com.ivieleague.kotlin_components_starter

import com.lightningkite.kotlin.networking.OkHttpApi
import com.lightningkite.kotlin.networking.lambdaGson

/**
 * Created by josep on 11/10/2016.
 */
object ExampleAPI : OkHttpApi("https://jsonplaceholder.typicode.com") {
    val getPosts = requestBuilder("/posts").get().lambdaGson<List<Post>>()
}

class Post(
        var userId: Long = -1,
        var id: Long = -1,
        var title: String = "",
        var body: String = ""
)