package org.example;

import java.util.Comparator;

public class PassengersSurvivedWithinFareComparator implements Comparator<Passenger> {
    public int compare(Passenger p1, Passenger p2) {
        if (p1.getFare() == p2.getFare())
        {
            if (p1.getSurvived() == p2.getSurvived())
            {
                return 0;
            }
            else if (p1.getSurvived() > p2.getSurvived())
            {
                return 1;
            }
            else if (p1.getSurvived() < p2.getSurvived())
            {
                return -1;
            }
            return 0;
        }
        else if (p1.getFare() < p2.getFare())
        {
            return -1;
        }
        else {
            return 1;
        }
    }
}
