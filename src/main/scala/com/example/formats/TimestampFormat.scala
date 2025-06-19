package com.example.formats

import spray.json.{JsString, JsValue, JsonFormat}

import java.sql.Timestamp

object TimestampFormat {
  implicit object TimestampFormat extends JsonFormat[Timestamp] {
    def write(ts: java.sql.Timestamp) = JsString(ts.toString)

    def read(json: JsValue) = json match {
      case JsString(s) => java.sql.Timestamp.valueOf(s)
      case _ => throw new RuntimeException("Timestamp must be string")
    }
  }
}

