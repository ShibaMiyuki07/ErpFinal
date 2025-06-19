package com.example.models

case class Provider
(
  providerId : Option[Int],
  providerName : String,
  location : String,
  email : String,
  phone : String
)
