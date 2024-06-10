package com.mohaberabi.sibha.core.util

interface AppError


sealed interface DataError : AppError {

    enum class Local : DataError {
        UNKNOWN, DISK_FULL, IOEXCEPTION,


    }
}


enum class CommonError : AppError {
    UNKNOWN,
    IOEXCEPTION,

}