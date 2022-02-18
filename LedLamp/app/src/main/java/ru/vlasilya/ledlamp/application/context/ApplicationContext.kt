package ru.vlasilya.ledlamp.application.context

import android.app.Application

class ApplicationContext : Application() {
    private lateinit var dependencyGraph: DependencyGraph

    override fun onCreate() {
        super.onCreate()
        dependencyGraph = DependencyGraph(this)
    }

    fun dependencies(): DependencyGraph = dependencyGraph
}