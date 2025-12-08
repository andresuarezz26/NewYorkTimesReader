package com.newyorktimesreader.domain

import com.newyorktimesreader.di.IoScheduler
import com.newyorktimesreader.domain.model.Article
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Use case used to get a list of articles
 */
interface GetArticlesUseCase {
  operator fun invoke(): Single<List<Article>>
}


class GetArticlesUseCaseImpl @Inject constructor(
  @IoScheduler private val ioSchedulers: Scheduler
): GetArticlesUseCase {

  override operator fun invoke(): Single<List<Article>> {
    return Single.just(
      listOf(
        Article(
          id = "nyt://article/067a379c-81e2-5465-bc6c-7411bbdee9b6",
          title = "Want to Avoid Texts From the Office? This Ring Could Help.",
          author = "By Tina Isaac-Goizé",
          imageUrl = "https://static01.nyt.com/images/2025/08/28/multimedia/28sp-jewelry-color-inyt-digi-03-cmkl/28sp-jewelry-color-inyt-digi-03-cmkl-articleLarge.jpg",
          imageDescription = "Katia de Lasteyrie, the founder of Spktrl, said the ring took two years to develop and was designed as “a tool of empowerment.”",
          abstract = "The Spktrl Light ring uses technology to trigger coded light displays through the diamond on its surface.",
          webUrl = "https://www.nytimes.com/2025/08/27/fashion/jewelry-technology-lab-grown-diamonds.html"
        ),
        Article(
          id = "nyt://article/50c58917-313c-52c6-a4e1-d732ef5e90b0",
          title = "Google Must Open Android to Other App Stores and Billing Options, Judge Rules",
          author = "By Nico Grant",
          imageUrl = "https://static01.nyt.com/images/2024/10/07/multimedia/07google-epic-qjvg/07google-epic-qjvg-articleLarge.jpg",
          imageDescription = "A federal judge on Monday set new rules defining how Google must make its Play app store more competitive, after a jury sided with Epic Games in December. Google is appealing the jury’s decision. ",
          abstract = "The internet giant was ordered by a federal judge to make a series of changes to address its anticompetitive conduct.",
          webUrl = "https://www.nytimes.com/2024/10/07/technology/google-app-store-epic-games-anticompetitive.html"
        )
      )
    ).subscribeOn(ioSchedulers)
  }
}