package com.newyorktimesreader.data.source.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class DiscoverServiceResponse(
  val status: String,
  val response: ArticleListResponse
) : Parcelable

@Parcelize
class ArticleListResponse(val docs: List<ArticleResponse>?) : Parcelable

@Parcelize
data class ArticleResponse(
  @SerializedName("_id")
  val id: String,
  val headline: HeadLineResponse?,
  val abstract: String?,
  val multimedia: MultimediaResponse?,
  val byline: ByLineResponse?,
  @SerializedName("web_url")
  val webUrl: String?
  ) : Parcelable

@Parcelize
data class HeadLineResponse(val main: String?) :
  Parcelable

@Parcelize
data class MultimediaResponse(val caption: String?, val default: DefaultMultimediaResponse) :
  Parcelable

@Parcelize
data class DefaultMultimediaResponse(val url: String?, val width: Int?, val height: Int?) :
  Parcelable

@Parcelize
class ByLineResponse(val original: String?) : Parcelable