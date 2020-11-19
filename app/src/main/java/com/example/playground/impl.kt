package com.example.playground

import io.reactivex.Single

class MapperRepositoryImpl : MapperRepository {
    override fun getData(id: String): Single<String> {
        return Single.just("success")
    }
}

class UrlInfoImpl : UrlInfo {
    override var userUrl: String = ""
        get() = "my_url"
}