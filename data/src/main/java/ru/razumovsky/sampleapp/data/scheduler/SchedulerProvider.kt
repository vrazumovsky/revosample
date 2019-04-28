package ru.razumovsky.sampleapp.data.scheduler

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun new(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

    fun trampoline(): Scheduler

    fun single(): Scheduler
}