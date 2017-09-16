package com.udacity.bakeit;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakeit.ui.MainActivity;
import com.udacity.bakeit.ui.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by VijayaLakshmi.IN on 9/5/2017.
 * Tests intent launch for {@link RecipeDetailActivity}
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityIntentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testClickOnRecipeListShouldLaunchRecipeDetail() {
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(hasComponent(RecipeDetailActivity.class.getName()));
    }
}
