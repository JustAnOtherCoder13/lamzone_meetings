package com.picone.lamzonemeetings;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.picone.lamzonemeetings.Utils.DeleteViewAction;
import com.picone.lamzonemeetings.Utils.RecyclerViewMatcher;
import com.picone.lamzonemeetings.view.MeetingsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.picone.lamzonemeetings.Utils.RecyclerViewItemCountAssertion.withItemCount;
import static com.picone.lamzonemeetings.Utils.SetTextInTextView.setTextInTextView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListMeetingTest {

    private final int MEETINGS_COUNT = 10;
    private RecyclerViewMatcher withRecyclerView = new RecyclerViewMatcher(R.id.container);

    @Rule
    public ActivityTestRule<MeetingsActivity> mActivityRule =
            new ActivityTestRule(MeetingsActivity.class);
    private ViewInteraction recyclerView = onView(withId(R.id.container));
    private ViewInteraction firstMeetingTitleTxt = onView(withRecyclerView.atPositionOnView(0, R.id.item_meeting_title_txt));

    @Before
    public void setUp() {
        mActivityRule.getActivity();
    }

    @Test
    public void meetingListShouldContainsTenMeeting() {
        //ensure recyclerView show all dummy meetings
        recyclerView.check(withItemCount(MEETINGS_COUNT));
    }

    @Test
    public void listMeetingDeleteActionShouldRemoveItem() {
        //perform click on delete icon on first meeting and check if item is delete
        recyclerView
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()))
                .check(withItemCount(MEETINGS_COUNT - 1));
    }

    @Test
    public void meetingTitleShouldBeFilledWithSubjectHourPlace() {
        // ensure first meeting's title is well filled
        firstMeetingTitleTxt.check(matches(withText("Meeting I - 11h00 - Peach")));
    }

    @Test
    public void meetingParticipantsShouldBeFilledWithMails() {
        // ensure first meeting's participants are mails
        onView(withRecyclerView.atPositionOnView(0, R.id.item_meeting_participants_txt))
                .check(matches(withText("maxime@lamzone.com, alex@lamzone.com, paul@lamzone.com")));
    }

    @Test
    public void filterMeetingsByPlaceShouldShowFilteredMeetings() {
        // filter by place "peach"
        onView(withId(R.id.filter_icon)).perform(click());
        onView(withText("filter by place")).perform(click());
        onView(withText("Peach")).perform(click());
        onView(withText("Ok")).perform(click());
        //ensure we have 2 meeting on room "peach"
        recyclerView.check(withItemCount(2));
        //only first meeting is hard coding, so ensure it is in filtered meetings
        firstMeetingTitleTxt.check(matches(withText("Meeting I - 11h00 - Peach")));
    }

    @Test
    public void filterMeetingsByDateShouldShowFilteredMeeting() {
        // filter by date on today
        onView(withId(R.id.filter_icon)).perform(click());
        onView(withText("filter by date")).perform(click());
        onView(withText("OK")).perform(click());
        //only first meeting must have date on today, so ensure filter meeting size is one
        recyclerView.check(withItemCount(1));
        // and check if it is the first meeting
        firstMeetingTitleTxt.check(matches(withText("Meeting I - 11h00 - Peach")));
    }

    @Test
    public void addNewMeetingShouldCreateMeeting() {
        //add meeting
        onView(withId(R.id.add_meeting_btn)).perform(click());
        //filled subject
        onView(withId(R.id.subject_text_field))
                .perform(click());
        onView(withId(R.id.subject_editText))
                .perform(replaceText("Meeting test"));
        //select room
        onView(withContentDescription("roomDropDown")).perform(click());
        onView(withText("Peach")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        //select date to today
        onView(withId(R.id.date_btn)).perform(click());
        onView(withText("OK")).perform(click());
        //select hour
        onView(withId(R.id.hour_txt)).perform(setTextInTextView("14h00"));
        //select a town to show chip group
        onView(withContentDescription("townDropDown")).perform(click());
        onView(withText("Marseille")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        //select 3 participants
        onView(withText("Alex")).perform(click());
        onView(withText("Lino")).perform(click());
        onView(withText("Paul")).perform(click());
        //click on add meeting
        onView(withId(R.id.add_new_meeting_btn)).perform(click());
        //ensure title is well filled
        onView(withRecyclerView.atPositionOnView(10, R.id.item_meeting_title_txt))
                .check((matches(withText("Meeting test - 14h00 - Peach"))));
        onView(withRecyclerView.atPositionOnView(10, R.id.item_meeting_participants_txt))
                .check(matches(withText("alex@lamzone.com, paul@lamzone.com, lino@lamzone.com")));
    }
}


