package com.udacity.bakeit;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.bakeit.ui.RecipeDetailActivity;

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
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetailsActivityUiTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> recipeDetailActivityActivityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void testClickOnNext() {
        //1 Find view
        //2 Perform action
        //3 Check view what it does.

        onView(withId(R.id.ingredient_next))
                .perform(click());

        onView(withId(R.id.steps_ingredients_container))
                .check(matches(isDisplayed()));
    }

}
