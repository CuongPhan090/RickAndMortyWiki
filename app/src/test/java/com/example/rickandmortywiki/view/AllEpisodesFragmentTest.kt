package com.example.rickandmortywiki.view

import android.view.ContextThemeWrapper
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.google.firebase.FirebaseApp
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode
import com.example.rickandmortywiki.R

@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(AndroidJUnit4::class)
class AllEpisodesFragmentTest {

    private val context = ContextThemeWrapper(ApplicationProvider.getApplicationContext(), R.style.Theme_RickAndMortyWiki)

    @Before
    fun setup() {
        FirebaseApp.initializeApp(context)
    }

    @Test
    fun `make sure binding get collected by Garbage Collector when the view is destroyed`() {
        val scenario = launchFragmentInContainer<AllEpisodesFragment>(
            themeResId = R.style.Theme_RickAndMortyWiki
        )

        scenario.onFragment{
            assertThat(it.binding).isNotNull()
            scenario.moveToState(Lifecycle.State.DESTROYED)
            assertThat(it.binding).isNull()
        }
    }

    @Test
    fun `test onClick`() {
        val navController = TestNavHostController(context)

        val scenario = launchFragmentInContainer<AllEpisodesFragment>(
            themeResId = R.style.Theme_RickAndMortyWiki
        )

        scenario.onFragment {
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.allEpisodesFragment)
            Navigation.setViewNavController(it.requireView(), navController)
        }

        scenario.onFragment {
            it.onEpisodeClick(1)
        }

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.episodeDetailBottomSheetFragment)
    }
}