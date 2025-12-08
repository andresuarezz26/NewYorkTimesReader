package com.newyorktimesreader.domain

import com.newyorktimesreader.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Use case used to get a single article from an article id with full details.
 */
interface GetArticleDetailUseCase {
  operator fun invoke(articleId: String): Single<Article>
}
class GetArticleDetailUseCaseImpl @Inject constructor(@IoScheduler private val ioSchedulers: Scheduler): GetArticleDetailUseCase {
  override fun invoke(articleId: String): Single<Article> {
    return Single.just( Article(
      id = "nyt://article/067a379c-81e2-5465-bc6c-7411bbdee9b6",
      title = "Want to Avoid Texts From the Office? This Ring Could Help.",
      author = "By Tina Isaac-Goizé",
      imageUrl = "https://static01.nyt.com/images/2025/08/28/multimedia/28sp-jewelry-color-inyt-digi-03-cmkl/28sp-jewelry-color-inyt-digi-03-cmkl-articleLarge.jpg",
      imageDescription = "Katia de Lasteyrie, the founder of Spktrl, said the ring took two years to develop and was designed as “a tool of empowerment.”",
      abstract = "The Spktrl Light ring uses technology to trigger coded light displays through the diamond on its surface.",
      webUrl = "https://www.nytimes.com/2025/08/27/fashion/jewelry-technology-lab-grown-diamonds.html",
      fullArticleContent = "French start-up Spktrl has developed a smart ring embedded with a 1.5-carat diamond that flashes in different colours to alert the wearer of their most pressing notifications, enabling more time spent phone-free.\n" +
          "\n" +
          "Spktrl was founded by Katia de Lasteyrie, a former innovation lead at luxury conglomerate LVMH, who set out to create a wearable tech object that is also a piece of high jewellery."
    )).subscribeOn(ioSchedulers)
  }
}