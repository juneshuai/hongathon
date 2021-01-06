package com.samle.kakao3

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KaKaoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "485ec676d7f3d36255729b251df39478")
    }
}