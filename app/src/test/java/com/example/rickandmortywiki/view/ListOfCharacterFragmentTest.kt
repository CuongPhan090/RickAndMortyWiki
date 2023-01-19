package com.example.rickandmortywiki.view

import android.view.ContextThemeWrapper
import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.FragmentListOfCharacterBinding
import com.google.common.truth.Truth.assertThat
import com.google.firebase.FirebaseApp
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(AndroidJUnit4::class)
class ListOfCharacterFragmentTest {

    private val context = ContextThemeWrapper(
        ApplicationProvider.getApplicationContext(), R.style.Theme_RickAndMortyWiki
    )

    @Before
    fun setup() {
        FirebaseApp.initializeApp(context)
    }

    @Test
    fun `test onClick`() {
        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Create a graphical Fragment Scenario for the ListOfCharacterFragment screen
        val scenario = launchFragmentInContainer<ListOfCharacterFragment>(
            themeResId = R.style.Theme_RickAndMortyWiki
        )

        scenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() API
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController's state
        scenario.onFragment {
            it.onCharacterClick(0)
        }

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.characterDetailFragment)
    }
}
