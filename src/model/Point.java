package model;

public class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point findStartXPoint() {
        int startX = Math.max(x - 5, 0);
        return new Point(startX, y);
    }

    public Point findEndXPoint() {
        int endX = Math.min(x + 5, 19);
        return new Point(endX, y);
    }

    public Point findStartYPoint() {
        int startY = Math.max(y - 5, 0);
        return new Point(x, startY);
    }

    public Point findEndYPoint() {
        int endY = Math.min(y + 5, 19);
        return new Point(x, endY);
    }

    public Point findLeftTopPoint() {
        int startX = Math.max(x - 5, 0);
        int startY = Math.max(y - 5, 0);
        return new Point(startX, startY);
    }

    public Point findRightTopPoint() {
        int endX = Math.min(x + 5, 19);
        int startY = Math.max(y - 5, 0);
        return new Point(endX, startY);
    }

    public Point findLeftBottomPoint() {
        int startX = Math.max(x - 5, 0);
        int endY = Math.min(y + 5, 19);
        return new Point(startX, endY);
    }

    public Point findRightBottomPoint() {
        int endX = Math.min(x + 5, 19);
        int endY = Math.min(y + 5, 19);
        return new Point(endX, endY);
    }

    public void log() {
        System.out.println("x: " + this.x + "| y: " + this.y);
    }
}

