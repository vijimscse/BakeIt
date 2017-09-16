package com.udacity.bakeit;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakeit.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by VijayaLakshmi.IN on 9/5/2017.
 * Tests UI for {@link MainActivity}
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityUiTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }


    @Test
    public void testRecipeListItemClickToLaunchRecipeDetails() {
        //1 Find view
        //2 Perform action
        //3 Check view what it does.
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.steps_ingredients_container))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
