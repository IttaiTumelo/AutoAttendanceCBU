package com.example.autoattendance.Entities;

import com.example.autoattendance.BaseEntity;

public class Schedule  extends BaseEntity {
    public int courseId;
    public Course course ;
    public int weekDay ;
    public int timeSlotId ;
    public TimeSlot timeSlot ;

    public Schedule() {
    }

    public Schedule(int courseId, int weekDay, int timeSlotId) {
        this.courseId = courseId;
        this.weekDay = weekDay;
        this.timeSlotId = timeSlotId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}
