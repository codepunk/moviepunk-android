package com.codepunk.moviepunk.util.log

import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Tree

class FormattingDebugTree : DebugTree() {

    private val fqcnIgnore = listOf(
        Timber::class.java.name,
        Timber.Forest::class.java.name,
        Tree::class.java.name,
        DebugTree::class.java.name,
        FormattingDebugTree::class.java.name
    )

    private val Throwable.significantElement: StackTraceElement
        get() = stackTrace.first { it.className !in fqcnIgnore }

    override fun createStackElementTag(element: StackTraceElement): String? {
        val split = super.createStackElementTag(element)
            ?.split("$")
            ?.getOrNull(0)
        return "MP|$split"
    }


    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) =
        (t ?: Throwable()).significantElement.run {
            super.log(
                priority = priority,
                tag = tag,
                message = "(${fileName}:${lineNumber}) $message",
                t = t
            )
        }

}