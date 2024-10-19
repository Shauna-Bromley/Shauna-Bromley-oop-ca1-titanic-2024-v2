package org.example;

import java.util.Comparator;

public class PassengerPNumberWithinGenderComparator implements Comparator<Passenger> {
    public int compare(Passenger p1, Passenger p2) {
        if (p1.getGender().compareTo(p2.getGender()) == 0)
        {
            if (p1.getPassengerId().compareTo(p2.getPassengerId()) == 0)
            {
                return p1.getPassengerId().compareTo(p2.getPassengerId());
            }
        }

        return p1.getGender().compareTo(p2.getGender());
    }
}
