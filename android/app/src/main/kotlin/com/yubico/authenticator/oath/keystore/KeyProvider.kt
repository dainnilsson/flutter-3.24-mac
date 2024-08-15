/*
 * Copyright (C) 2022-2023 Yubico.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yubico.authenticator.oath.keystore

import android.annotation.TargetApi
import android.os.Build
import android.security.keystore.KeyProperties
import com.yubico.authenticator.compatUtil
import com.yubico.yubikit.oath.AccessKey

interface KeyProvider {
    fun hasKey(deviceId: String): Boolean
    fun getKey(deviceId: String): AccessKey?
    fun putKey(deviceId: String, secret: ByteArray)
    fun removeKey(deviceId: String)
    fun clearAll()
}

fun getAlias(deviceId: String) = "$deviceId,0"

val KEY_ALGORITHM_HMAC_SHA1 = compatUtil.from(Build.VERSION_CODES.M) {
    getHmacSha1AlgorithmNameM()
}.otherwise("HmacSHA1")

@TargetApi(Build.VERSION_CODES.M)
fun getHmacSha1AlgorithmNameM() = KeyProperties.KEY_ALGORITHM_HMAC_SHA1