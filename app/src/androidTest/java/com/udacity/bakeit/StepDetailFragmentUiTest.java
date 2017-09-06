package com.udacity.bakeit;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakeit.ui.RecipeDetailActivity;
import com.udacity.bakeit.ui.RecipeStepDetailsFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by VijayaLakshmi.IN on 9/5/2017.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class StepDetailFragmentUiTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> rule = new ActivityTestRule<>(RecipeDetailActivity.class, true, false);

    private RecipeStepDetailsFragment fragment;
    private RecipeDetailActivity activity;

    @Before
    public void setup() {
        // Setup shared mock information or do your dependency injection
        fragment = new RecipeStepDetailsFragment();
    }


    @Test
    public void fragmentTest() {
        // Setup your test specific mocks here

        activity = rule.launchActivity(new Intent());
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.steps_ingredients_container, fragment)
                .commit();

        onView(withId(R.id.ingredient_next)).check(matches(isClickable()));
        onView(withId(R.id.ingredient_prev)).check(matches(isClickable()));
        onView(withId(R.id.no_video_content)).check(matches(isDisplayed()));
    }
}
