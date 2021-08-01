package com.entreprisecorp.messagereact

import android.app.Application

class ReactMessage : Application() {

    val reactMessageDatasource: ReactMessageDatasource by lazy {
        ReactMessageDatasource(this)
    }
}