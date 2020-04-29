package com.picone.lamzonemeetings.controller.di;

import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.controller.service.MeetingService;

public class DI {

    public static ApiService getMeetingApiService() { return new MeetingService(); }

}
