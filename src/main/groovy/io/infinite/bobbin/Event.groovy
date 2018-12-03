package io.infinite.bobbin

class Event {
    Level level
    Date date = new Date()
    String message
    String className
    String formattedMessage
    def arguments = []
    Throwable throwable
}