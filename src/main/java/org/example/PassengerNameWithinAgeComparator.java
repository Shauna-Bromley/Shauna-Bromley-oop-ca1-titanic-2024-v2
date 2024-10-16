package org.example;

import java.util.Comparator;

public class PassengerNameWithinAgeComparator implements Comparator<Passenger> {
    @Override
    public int compare(Passenger p1, Passenger p2) {
        if (p1.getAge() == p2.getAge())
        {
            if (p1.getName().compareTo(p2.getName()) == 0)
            {
                return p1.getName().compareTo(p2.getName());
            }
        }
        else if (p1.getAge() < p2.getAge())
        {
            return -1;
        }
        else {
            return 1;
        }
        return 0;
    }
}
