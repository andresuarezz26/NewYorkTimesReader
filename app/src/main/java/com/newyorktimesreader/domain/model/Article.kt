package com.newyorktimesreader.domain.model

data class Article(
  val id: String,
  val title: String,
  val author: String,
  val imageUrl: String,
  val imageDescription: String,
  val abstract: String,
  val webUrl: String
)
