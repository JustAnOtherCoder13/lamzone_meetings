package com.picone.lamzonemeetings.controller.di;

import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.controller.service.DummyMeetingService;

public class DI {

    public static ApiService getMeetingApiService() { return new DummyMeetingService(); }

}
