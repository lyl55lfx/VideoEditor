package com.github.xch168.videoeditor.entity;


import android.graphics.Rect;

public class TrackEditEntry {
    private String clipContent;
    private int defaultInPointPosition;
    private long defaultTrimIn;
    private long inPoint;
    private int longPressDragOffsetPosition;
    private long outPoint;
    private Rect rect;
    private long totalTime;
    private long trimIn;
    private long trimOut;

    public TrackEditEntry(Rect rect, long trimIn, long trimOut, long totalTime, int defaultInPointPosition, long defaultTrimIn) {
        this.defaultInPointPosition = defaultInPointPosition;
        this.defaultTrimIn = defaultTrimIn;
        this.rect = rect;
        this.totalTime = totalTime;
        this.trimIn = trimIn;
        this.trimOut = trimOut;
    }

    public TrackEditEntry(String clipContent, Rect rect, long totalTime, long inPoint, long outPoint, long trimIn, long trimOut) {
        this.clipContent = clipContent;
        this.rect = rect;
        this.totalTime = totalTime;
        this.inPoint = inPoint;
        this.outPoint = outPoint;
        this.trimIn = trimIn;
        this.trimOut = trimOut;
    }

    public int getHandleOffsetPosition() {
        return rect.left - defaultInPointPosition - longPressDragOffsetPosition;
    }

    public String getClipContent()
    {
        return clipContent;
    }

    public void setClipContent(String clipContent)
    {
        this.clipContent = clipContent;
    }

    public int getDefaultInPointPosition()
    {
        return defaultInPointPosition;
    }

    public void setDefaultInPointPosition(int defaultInPointPosition)
    {
        this.defaultInPointPosition = defaultInPointPosition;
    }

    public long getDefaultTrimIn()
    {
        return defaultTrimIn;
    }

    public void setDefaultTrimIn(long defaultTrimIn)
    {
        this.defaultTrimIn = defaultTrimIn;
    }

    public long getInPoint()
    {
        return inPoint;
    }

    public void setInPoint(long inPoint)
    {
        this.inPoint = inPoint;
    }

    public int getLongPressDragOffsetPosition()
    {
        return longPressDragOffsetPosition;
    }

    public void setLongPressDragOffsetPosition(int longPressDragOffsetPosition)
    {
        this.longPressDragOffsetPosition = longPressDragOffsetPosition;
    }

    public long getOutPoint()
    {
        return outPoint;
    }

    public void setOutPoint(long outPoint)
    {
        this.outPoint = outPoint;
    }

    public Rect getRect()
    {
        return rect;
    }

    public void setRect(Rect rect)
    {
        this.rect = rect;
    }

    public long getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(long totalTime)
    {
        this.totalTime = totalTime;
    }

    public long getTrimIn()
    {
        return trimIn;
    }

    public void setTrimIn(long trimIn)
    {
        this.trimIn = trimIn;
    }

    public long getTrimOut()
    {
        return trimOut;
    }

    public void setTrimOut(long trimOut)
    {
        this.trimOut = trimOut;
    }
}
