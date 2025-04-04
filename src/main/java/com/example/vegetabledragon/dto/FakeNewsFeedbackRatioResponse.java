package com.example.vegetabledragon.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeNewsFeedbackRatioResponse {
    private long fakeNewsCount;
    private long trueNewsCount;
    private double fakeNewsRatio;
    private double trueNewsRatio;

    public FakeNewsFeedbackRatioResponse(long fakeNewsCount, long trueNewsCount, double fakeNewsRatio, double trueNewsRatio) {
        this.fakeNewsCount = fakeNewsCount;
        this.trueNewsCount = trueNewsCount;
        this.fakeNewsRatio = fakeNewsRatio;
        this.trueNewsRatio = trueNewsRatio;
    }
}
