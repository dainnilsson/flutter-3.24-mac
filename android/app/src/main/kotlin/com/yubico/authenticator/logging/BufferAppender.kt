/*
 * Copyright (C) 2023 Yubico.
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

package com.yubico.authenticator.logging

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.UnsynchronizedAppenderBase

class BufferAppender : UnsynchronizedAppenderBase<ILoggingEvent>() {

    private var encoder: PatternLayoutEncoder? = null

    private val buffer = arrayListOf<String>()

    public override fun append(event: ILoggingEvent) {
        if (!isStarted) {
            return
        }

        if (buffer.size > MAX_BUFFER_SIZE) {
            buffer.removeAt(0)
        }

        buffer.add(encoder!!.layout.doLayout(event))
    }

    fun getEncoder(): PatternLayoutEncoder? = encoder

    fun setEncoder(encoder: PatternLayoutEncoder?) {
        this.encoder = encoder
    }

    fun getLogBuffer(): ArrayList<String> {
        return buffer
    }

    companion object {
        private const val MAX_BUFFER_SIZE = 1000
    }
}