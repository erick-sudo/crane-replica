package com.compose.samples.replicas.crane.calendar.model

enum class AnimationDirection {
    FORWARDS,
    BACKWARDS;

    fun isBackwards() = this == BACKWARDS

    fun isForwards() = this == FORWARDS
}