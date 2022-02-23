package com.avatarmind.floatingclock.util.event;

import com.avatarmind.floatingclock.util.ClockInfo;

public class UpdateClockViewEvent {
    private ClockInfo clockInfo;

    public UpdateClockViewEvent(ClockInfo clockInfo) {
        this.clockInfo = clockInfo;
    }

    public ClockInfo getClockInfo() {
        return clockInfo;
    }
}
