package com.example.android.bakingapp;

import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;

import com.example.android.bakingapp.recipedetails.FragmentRecipeDetails;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by akshayshahane on 25/07/17.
 */

public class FragmentDetailsTestPhone {

    @Rule
    public FragmentTestRule<FragmentRecipeDetails> mFragmentTestRule = new FragmentTestRule<>(FragmentRecipeDetails.class);




    @Test
    public void fragmentDetailsHasBackButtonAndHasMenuToAddWidgetAndAlsoOpensFrgamentSteps() {

        // Launch the activity to make the fragment visible
        mFragmentTestRule.launchActivity(null);

        // Then use Espresso to test the Fragment

        onView(withContentDescription("Navigate up")).check(matches(isDisplayed()));

        onView(withContentDescription(R.string.add_to_widegt)).check(matches(isDisplayed()));

        onView(withId(R.id.rv_steps)).perform(scrollTo())
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_step))
                .check(matches(isDisplayed()));
    }

}
