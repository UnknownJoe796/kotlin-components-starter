package com.lightningkite.kotlin.async

import com.lightningkite.kotlin.anko.async.UIThread
import java.util.*
import java.util.concurrent.*


@Deprecated("Use 'invokeOn' instead.", ReplaceWith(
        "invokeOn(Async)",
        "com.lightningkite.kotlin.async.Async",
        "com.lightningkite.kotlin.async.invokeOn"
))
fun <T> (() -> T).invokeAsync() = invokeOn(Async)

@Deprecated("Use 'invokeOn' instead.", ReplaceWith(
        "invokeFuture(Async)",
        "com.lightningkite.kotlin.async.Async",
        "com.lightningkite.kotlin.async.invokeOn"
))
fun <T> (() -> T).invokeAsyncFuture(): Future<T> = invokeFuture<T>(Async)

@Deprecated("Use 'invokeOn' instead.", ReplaceWith(
        "invokeOn(UIThread)",
        "com.lightningkite.kotlin.anko.async.UIThread",
        "com.lightningkite.kotlin.async.invokeOn"
))
fun (() -> Unit).invokeUIThread() = invokeOn(UIThread)

@Deprecated("Use 'invokeOn' instead.", ReplaceWith(
        "invokeOn(UIThread)",
        "com.lightningkite.kotlin.anko.async.UIThread",
        "com.lightningkite.kotlin.async.invokeOn"
))
@JvmName("invokeUIThreadT")
inline fun <T> (() -> T).invokeUIThread() = invokeOn(UIThread)

@Deprecated("Use 'invokeOn' instead.", ReplaceWith(
        "this.thenOn(UIThread, callback).invokeOn(Async)",
        "com.lightningkite.kotlin.async.Async",
        "com.lightningkite.kotlin.anko.async.UIThread",
        "com.lightningkite.kotlin.async.invokeOn",
        "com.lightningkite.kotlin.async.thenOn"
))
@JvmName("invokeUIThreadT")
fun <T> (() -> T).invokeAsync(callback: (T) -> Unit)
        = this.thenOn(UIThread, callback).invokeOn(Async)

/**
 * Runs [action] asynchronously.
 * @param action The lambda to run asynchronously.
 */
@Deprecated("Use 'Async.execute' instead.", ReplaceWith(
        "Async.execute(action)",
        "com.lightningkite.kotlin.async.Async",
        "com.lightningkite.kotlin.anko.async.UIThread",
        "com.lightningkite.kotlin.async.invokeOn",
        "com.lightningkite.kotlin.async.thenOn"
))
inline fun <T> doAsync(crossinline action: () -> T) = Async.execute { action() }

/**
 * Runs [action] asynchronously with its result being dealt with on the UI thread in [uiThread].
 * @param action The lambda to run asynchronously.
 * @param uiThread The lambda to run with the result of [action] on the UI thread.
 */
@Deprecated("Use 'Async.execute' instead.")
fun <T> doAsync(action: () -> T, uiThread: (T) -> Unit) {
    Async.execute({
        try {
            val result = action()
            UIThread.execute {
                uiThread(result)
            }
        } catch (e: Exception) {
            UIThread.execute {
                throw e
            }
        }
    })
}

/**
 * Posts [action] to the main thread.
 * @param action The lambda to run asynchronously.
 */
@Deprecated("Use 'UIThread.execute' instead.", ReplaceWith(
        "Async.execute(action)",
        "com.lightningkite.kotlin.anko.async.UIThread"
))
fun doUiThread(action: () -> Unit) = UIThread.execute(action)

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
fun <A, B> parallel(a: () -> A, b: () -> B): () -> Pair<A, B> {
    return {
        val futureA = FutureTask {
            a.invoke()
        }
        val futureB = FutureTask {
            b.invoke()
        }
        Thread(futureA).start()
        Thread(futureB).start()
        futureA.get() to futureB.get()
    }
}

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
fun <A, B, C> parallel(a: () -> A, b: () -> B, c: () -> C): () -> Triple<A, B, C> {
    return {
        val futureA = FutureTask {
            a.invoke()
        }
        val futureB = FutureTask {
            b.invoke()
        }
        val futureC = FutureTask {
            c.invoke()
        }
        Thread(futureA).start()
        Thread(futureB).start()
        Thread(futureC).start()
        Triple(futureA.get(), futureB.get(), futureC.get())
    }
}

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
fun <T> parallelNonblocking(tasks: List<() -> T>, onAllComplete: (List<T>) -> Unit) {
    if (tasks.isEmpty()) onAllComplete(listOf())
    val items = ArrayList<T>()
    for (task in tasks) {
        task.invokeAsync {
            items.add(it)
            if (items.size == tasks.size) {
                onAllComplete(items)
            }
        }
    }
}

@JvmName("parallelBlockingShorthand")
@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
fun <T> List<() -> T>.parallel(): () -> List<T> = parallel(this)

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
fun <T> parallel(tasks: List<() -> T>): () -> List<T> {
    val numCores = Runtime.getRuntime().availableProcessors()
    if (tasks.isEmpty()) return { listOf() }
    else if (tasks.size < numCores) {
        return {
            try {
                val results = tasks.subList(0, tasks.size - 1).map {
                    val future = FutureTask {
                        it.invoke()
                    }
                    Thread(future) to future
                }.map { it.first.start(); it.second }.map { it.get() }.toMutableList()
                results += tasks.last().invoke()
                results
            } catch (e: Exception) {
                e.printStackTrace()
                tasks.map { it() }
            }
        }
    } else {
        return {
            val pool = ThreadPoolExecutor(
                    Runtime.getRuntime().availableProcessors(),
                    Runtime.getRuntime().availableProcessors(),
                    1,
                    TimeUnit.SECONDS,
                    LinkedBlockingQueue<Runnable>()
            )
            try {
                val results = tasks.subList(0, tasks.size - 1).map {
                    pool.submit(it)
                }.map { it.get() }.toMutableList()
                results += tasks.last().invoke()
                results
            } catch (e: Exception) {
                e.printStackTrace()
                tasks.map { it() }
            }
        }
    }
}


//Weird async stuff below

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
inline fun <T> List<T>.withEachAsync(doTask: T.(() -> Unit) -> Unit, crossinline onAllComplete: () -> Unit) {
    if (isEmpty()) {
        onAllComplete()
        return
    }
    var itemsToGo = size
    for (item in this) {
        item.doTask {
            itemsToGo--
            if (itemsToGo <= 0) {
                onAllComplete()
            }
        }
    }
}

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
inline fun <T, MUTABLE, RESULT> List<T>.withReduceAsync(
        doTask: T.((RESULT) -> Unit) -> Unit,
        initialValue: MUTABLE,
        crossinline combine: MUTABLE.(RESULT) -> Unit,
        crossinline onAllComplete: (MUTABLE) -> Unit
) {
    if (isEmpty()) {
        onAllComplete(initialValue)
        return
    }
    var total = initialValue
    var itemsToGo = size
    for (item in this) {
        item.doTask {
            combine(total, it)
            itemsToGo--
            if (itemsToGo <= 0) {
                onAllComplete(total)
            }
        }
    }
}

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
inline fun parallelAsyncs(tasks: Collection<(() -> Unit) -> Unit>, crossinline onComplete: () -> Unit) {
    if (tasks.isEmpty()) {
        onComplete()
        return
    }
    var itemsToGo = tasks.size
    for (item in tasks) {
        item {
            itemsToGo--
            if (itemsToGo <= 0) {
                onComplete()
            }
        }
    }
}

@Deprecated("Place in your program, not the library.  Not used frequently enough for library status.")
inline fun parallelAsyncs(vararg tasks: (() -> Unit) -> Unit, crossinline onComplete: () -> Unit) {
    if (tasks.isEmpty()) {
        onComplete()
        return
    }
    var itemsToGo = tasks.size
    for (item in tasks) {
        item {
            itemsToGo--
            if (itemsToGo <= 0) {
                onComplete()
            }
        }
    }
}