package com.dangerfield.gitjob.data.util

import kotlinx.coroutines.*
import org.jsoup.Jsoup

/*
Asynchonously maps all items in an Iterable
 */
suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

fun String.removeHtml(): String {
    return Jsoup.parse(this).text()
}
