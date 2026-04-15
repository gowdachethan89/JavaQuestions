package com.alti.course;

class Course {
    /* Data about a particular course. */
    public String title;  // The name of the obstacle course
    public int obstacleCount;  // The number of obstacles in the course

    public Course(String courseTitle, int obstacles) {
        title = courseTitle;
        obstacleCount = obstacles;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Course)) { return false; }
        Course c = (Course) o;
        return c.title == this.title && c.obstacleCount == this.obstacleCount;
    }

    @Override
    public int hashCode() {
        return (title == null ? 0 : title.hashCode()) * obstacleCount;
    }
}