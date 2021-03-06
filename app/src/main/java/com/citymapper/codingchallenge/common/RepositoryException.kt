package com.citymapper.codingchallenge.common

class RepositoryException(
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause)
