package org.example;
// CA1
import java.io. * ;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;



public class Main {
    public static void main(String[] args) {

        String fileName = "titanic-data-100.csv"; // file should be in the project folder (below pom.xml)

        ArrayList<Passenger> passengerList = new ArrayList<>();

        loadPassengerDataFromFile(passengerList, fileName);

        displayAllPassengers(passengerList);

        System.out.println("\nQ1 get passenger names");
        getPassengerNames(passengerList);

        System.out.println("\nQ2 get passenger names with a specific name");
        getPassengersContainingNames(passengerList, "William");

        System.out.println("\nQ3 get passengers older than a certain age");
        getPassengersOlderThan(passengerList,42);

        System.out.println("\nQ4 get passengers based on gender");
        countPassengersByGender(passengerList,"female");

        System.out.println("\nQ5 The sum of all the ticket fares");
        sumFares(passengerList);

        System.out.println("\nQ6 getting male surviving passengers");
        maleSurvivors(passengerList);

        System.out.println("\nQ7 getting the owner of a specific ticket number");
        ticketOwner(passengerList,"PC 17759");

        System.out.println("\nQ8 getting the average age of passengers");
        averageAge(passengerList);

        System.out.println("\nQ9 getting passengers by ticket class");
        getPassengersByTicketClass(passengerList,PassengerClass.FIRST);

        System.out.println("\nQ10 sorting passengers by ID");
        sortPassengersByPassengerId(passengerList);

        Collections.sort(passengerList, new PassengerNameWithinAgeComparator());
        for (int i=0; i<passengerList.size(); i++){
            System.out.println(passengerList.get(i));
        }
//        sortPassengersByName();
//        sortPassengersByAgeThenName();
//        sortPassengersByGenderThenPassengerNumber()
//        sortPassengersByFareThenSurvival();
//        sortPassengersByTicketClass()
//        sortPassengersByAge();
//        sortPassengersByTicketNumberLambda();
//        sortPassengersByTicketNumberStatic();
//        findPassengerByTicketNumber();
//        findPassengerByPassengerId();

        System.out.println("Finished, Goodbye!");
    }

    /**
     * Populate an ArrayList of Passenger objects with data from a text file
     *
     * @param passengerList - an ArrayList to be filled with Passenger objects
     * @param fileName      - name of CSV text file containing passenger details
     */
    public static void loadPassengerDataFromFile(ArrayList<Passenger> passengerList, String fileName) {

        // Format of each row of data is:
        // Name,Age,Height(m),GPA  - these heading names are included as the first row in file
        // John Malone,20,1.78,3.55   for example

        // Use a Regular Expression to set both comma and newline as delimiters.
        //  sc.useDelimiter("[,\\r\\n]+");
        // Text files in windows have lines ending with "CR-LF" or "\r\n" sequences.
        // or may have only a newline - "\n"
        // Windows uses CRLF (\r\n, 0D 0A) line endings while Unix just uses LF (\n, 0A).
        //
        try (Scanner sc = new Scanner(new File(fileName))
                .useDelimiter("[,\\r\\n]+")) {

            // skip past the first line, as it has field names (not data)
            if (sc.hasNextLine())
                sc.nextLine();   // read the header line containing column titles, but don't use it

            // while there is a next token to read....
            System.out.println("Go...");

            while (sc.hasNext()) {
                String passengerId = sc.next();    // read passenger ID
                int survived = sc.nextInt();     // 0=false, 1=true
                int passengerClass = sc.nextInt();  // passenger class, 1=1st, 2=2nd or 3rd
                String name = sc.next();
                String gender = sc.next();
                int age = sc.nextInt();
                int siblingsAndSpouses = sc.nextInt();
                int parentsAndChildren = sc.nextInt();
                String ticketNumber = sc.next();
                double fare = sc.nextDouble();
                String cabin = sc.next();
                String embarkedAt = sc.next();

                System.out.println(passengerId + ", " + name);

                passengerList.add(
                        new Passenger(passengerId, survived, passengerClass,
                                name, gender, age, siblingsAndSpouses, parentsAndChildren, ticketNumber,
                                fare, cabin, embarkedAt)
                );
            }
        } catch (FileNotFoundException exception) {
            System.out.println("FileNotFoundException caught. The file " + fileName + " may not exist." + exception);
        }
    }

    public static void displayAllPassengers(ArrayList<Passenger> passengerList) {
        System.out.println("Displaying all passengers:");
        for (Passenger passenger : passengerList) {
            System.out.println(passenger);
        }
    }

    // Q1 getPassengerNames();
    public static void getPassengerNames(ArrayList<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            System.out.println(passenger.getName());
        }
    }

    //Q2. getPassengerContainingName();
    public static void getPassengersContainingNames(ArrayList<Passenger> passengers, String name) {
        for (Passenger passenger : passengers) {
            if (passenger.getName().contains(name)) {
                System.out.println(passenger.getName());
            }
        }
    }
    //Q3 getPassengersOlderThan();
    public static void getPassengersOlderThan(ArrayList<Passenger> passengers, int age) {
        for (Passenger passenger : passengers) {
            if (passenger.getAge() > age) {
                System.out.println(passenger);
            }
        }
    }

    //Q4 countPassengersByGender();
    public static void countPassengersByGender(ArrayList<Passenger> passengers, String gender) {
        for (Passenger passenger : passengers) {
            if (passenger.getGender().equals(gender)) {
                System.out.println(passenger);
            }
        }
    }

    //Q5 sumFares();
    public static void sumFares (ArrayList<Passenger> passengers) {
        double sumFare = 0;
        for (Passenger passenger : passengers) {
            sumFare += passenger.getFare();
        }
        System.out.println(sumFare);
    }

    //Q6 maleSurvivors();
    public static void maleSurvivors (ArrayList<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            if (passenger.getSurvived()==1 && passenger.getGender().equals("male")) {
                System.out.println(passenger);
            }
        }
    }

    //Q7 ticketOwner
    public static void ticketOwner(ArrayList<Passenger> passengers, String ticketNumber) {
        for (Passenger passenger : passengers) {
            if (passenger.getTicketNumber().equals(ticketNumber)) {
                System.out.println(passenger);
            }
        }
    }

    //Q8 averageAge
    public static void averageAge(ArrayList<Passenger> passengers) {
        int total = 0;
        for (Passenger passenger : passengers) {
            total += passenger.getAge();
        }
        double averageAge  = (double) total / passengers.size();
        System.out.println(averageAge);
    }

    //Q9 getPassengersByTicketClass
    public static void getPassengersByTicketClass(ArrayList<Passenger> passengers, PassengerClass passengerClass) {
        for (Passenger passenger : passengers) {
            if (passenger.getPassengerClass().equals(passengerClass)) {
                System.out.println(passenger);
            }
        }
    }

    //Q10 sortPassengersByPassengerID
    public static void sortPassengersByPassengerId (ArrayList<Passenger> passengers) {
        Collections.sort(passengers);

        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }
}