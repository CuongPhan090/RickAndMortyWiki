package com.example.rickandmortywiki.view

import android.view.ContextThemeWrapper
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.model.domain.Character
import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.repository.SharedRepository
import com.google.common.truth.Truth.assertThat
import com.google.firebase.FirebaseApp
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(AndroidJUnit4::class)
class EpisodeDetailBottomSheetFragmentTest {

    private val context = ContextThemeWrapper(
        ApplicationProvider.getApplicationContext(),
        R.style.Theme_RickAndMortyWiki
    )
    private val sharedRepository = spyk(SharedRepository())

    @Before
    fun setup() {
        FirebaseApp.initializeApp(context)
    }

    @Test
    fun `make sure binding is collected by Garbage Collector when the view is destroyed`() {
        val scenario = launchFragment<EpisodeDetailBottomSheetFragment>(
            fragmentArgs = bundleOf("episode_id" to 1),
            themeResId = R.style.Theme_RickAndMortyWiki
        )

        scenario.onFragment { fragment ->
            assertThat(fragment.binding).isNotNull()
            scenario.moveToState(Lifecycle.State.DESTROYED)
            assertThat(fragment.binding).isNull()
        }
    }

    @Test
    fun `test onClick`() {
        val navController = TestNavHostController(context).apply {
            setGraph(R.navigation.nav_graph)
            setCurrentDestination(R.id.episodeDetailBottomSheetFragment)
        }

        val scenario = launchFragmentInContainer<EpisodeDetailBottomSheetFragment>(
            fragmentArgs = bundleOf("episode_id" to 1),
            themeResId = R.style.Theme_RickAndMortyWiki
        )
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
            fragment.onEpisodeDetailClick(1)
        }

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.characterDetailFragment)
    }

    @Test
    fun `test bind data with empty episode`() {
        coEvery { sharedRepository.getEpisode("1") } returns Episode()

        val scenario = launchFragment<EpisodeDetailBottomSheetFragment>(
            fragmentArgs = bundleOf("episode_id" to 1),
            themeResId = R.style.Theme_RickAndMortyWiki
        )

        scenario.onFragment { fragment ->
            assertThat(fragment.binding?.episodeName?.text).isEqualTo("")
            assertThat(fragment.binding?.episodeAirDay?.text).isEqualTo("")
            assertThat(fragment.binding?.seasonEpisodeNumber?.text).isEqualTo("")
        }
    }

    @Test
    fun `test bind data with mock episode data`() {
        coEvery { sharedRepository.getEpisode("1") } returns getMockedEpisode()

//        val scenario = launchFragment<EpisodeDetailBottomSheetFragment>(
//            fragmentArgs = bundleOf("episode_id" to 1),
//            themeResId = R.style.Theme_RickAndMortyWiki
//        )


//        scenario.onFragment { fragment ->
//            assertThat(fragment.binding?.episodeName?.text).isEqualTo("Pilot")
//            assertThat(fragment.binding?.episodeAirDay?.text).isEqualTo("December 2, 2013")
//            assertThat(fragment.binding?.seasonEpisodeNumber?.text).isEqualTo("Season 1 Episode 1")
//            onView(withId(R.id.character_carousel_image_view)).check(matches(isDisplayed()))
//            onView(withId(R.id.character_name_text_view)).check(matches(isDisplayed()))
//        }

    }

    private fun getMockedEpisode() =
        Episode(
            id = 1,
            name = "Pilot132",
            airDate = "December 2, 2013",
            season = 1,
            episode = 1,
            characters = listOf(
                Character(gender = "Male", name = "Morty Smith"),
                Character(gender = "Male", name = "Rick Sanchez")
            )
        )
}