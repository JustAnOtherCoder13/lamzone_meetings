package com.picone.lamzonemeetings.controller.di;
import com.picone.lamzonemeetings.controller.service.ApiService;
import com.picone.lamzonemeetings.controller.service.DummyMeetingService;

public class DI {

        private static DummyMeetingService service = new DummyMeetingService();

        public static ApiService getMeetingApiService() { return service; }

        //for test
        public static ApiService getNewInstanceApiService() { return new DummyMeetingService(); }

}
