package org.example;

import java.util.Comparator;

public class PassengersTicketClassComparator implements Comparator<Passenger> {
    public int compare(Passenger p1, Passenger p2) {
        return p1.getClass().toString().compareTo(p2.getClass().toString());
    }
}
