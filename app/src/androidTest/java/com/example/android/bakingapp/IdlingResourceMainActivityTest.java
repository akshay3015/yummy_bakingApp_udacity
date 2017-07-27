package com.example.android.bakingapp;

import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.example.android.bakingapp.recepielist.FragmentRecipeList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;

/**
 * Created by akshayshahane on 24/07/17.
 */
@RunWith(AndroidJUnit4.class)
public class IdlingResourceMainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);





    public IdlingResource startTiming(long time) {
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(time);
        Espresso.registerIdlingResources(idlingResource);
        return idlingResource;
    }

    public void stopTiming(IdlingResource idlingResource) {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void onClickOnRecipeOpensDetailsScreen() {
        IdlingResource idlingResource;

        idlingResource = startTiming(4000);
        onView(withRecyclerView(R.id.rv_fragment_recipes).atPosition(0))
                .check(matches(isDisplayed()));

        onView(withRecyclerView(R.id.rv_fragment_recipes).atPositionOnView(0, R.id.tv_recipe_name))
                .check(matches(isDisplayed()))
                .check(matches(withText("Nutella Pie")));

        onView(withRecyclerView(R.id.rv_fragment_recipes).atPositionOnView(0, R.id.tv_servings))
                .check(matches(isDisplayed()))
                .check(matches(withText("Servings : 8")));

        onView(withRecyclerView(R.id.rv_fragment_recipes).atPosition(0))
                .perform(click());


        stopTiming(idlingResource);


    }




    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
}
