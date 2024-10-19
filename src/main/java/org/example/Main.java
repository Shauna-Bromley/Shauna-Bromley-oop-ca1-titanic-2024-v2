package org.example;
// CA1
import java.io. * ;
import java.util.*;


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

        //The questions only said to use separate comparator classes from Q12
        //onwards but after setting the passengerID as the natural order
        //I didn't know how to use the comparable compareTo to sort by another parameter
        //so, I used a comparator for this as well
        System.out.println("\nQ11 sorting passengers by name");
        sortPassengersByName(passengerList);

        System.out.println("\nQ12 sorting passengers by age and then by name");
        sortPassengersByAgeThenName(passengerList);

        System.out.println("\nQ13 sorting passengers by gender then by passenger number");
        sortPassengersByGenderThenPassengerNumber(passengerList);

        System.out.println("\nQ14 sorting passengers by fare then whether they survived or not");
        sortPassengersByFareThenSurvival(passengerList);

        System.out.println("\nQ15 sorting passengers by ticket class");
        sortPassengersByTicketClass(passengerList);

        System.out.println("\nQ16 sorting passengers by age using anonymous class");
        Collections.sort(passengerList, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                int age = ((Passenger)o1).getAge();
                int age2 = ((Passenger)o2).getAge();
                return age-age2;
            }
        });
        System.out.println(passengerList);

        System.out.println("\nQ17 sorting passengers by by ticket number using a lambda function");
        sortPassengersByTicketNumberLambda(passengerList);

        System.out.println("\nQ18 sorting passengers by ticket number");
        sortPassengersByTicketNumberStatic(passengerList);

        System.out.println("\nQ19 finding a passenger with a certain ticket number using binary search");
        //This did not return the passenger I wanted as it
        //says that the key value was not found
        //despite that being a ticket number
        //I think it may be to do with the binary search
        //relying on natural order which is assigned to
        //PassengerID which I am unsure how to change to suit this question
        Collections.sort (passengerList, new PassengersTicketNumberComparator());
        Passenger key = new Passenger ("",0,0,"","",0,0,0,"W.E.P.",0,"","");
        int index = Collections.binarySearch(passengerList, key);
        if (index < 0)
        {
            System.out.println("Key value not found");
        }
        else {
            System.out.println(passengerList.get(index).toString());
        }

        System.out.println("\nQ20 finding a passenger with a certain id using binary search");
        Collections.sort (passengerList);
        Passenger key1 = new Passenger ("93",0,0,"","",0,0,0,"",0,"","");
        index = Collections.binarySearch(passengerList, key1);
        if (index < 0)
        {
            System.out.println("Key value not found");
        }
        else {
            System.out.println(passengerList.get(index).toString());
        }
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

    //Q11 sorPassengersByName
    public static void sortPassengersByName (ArrayList<Passenger> passengers) {
        Collections.sort(passengers, new PassengerNameComparator());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    //Q12 sortPassengersByAgeThenNameComparator
    public static void sortPassengersByAgeThenName(ArrayList<Passenger> passengers) {
        Collections.sort(passengers, new PassengerNameWithinAgeComparator());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    //Q13 sortPassengersByGenderThenPassengerNumber
    //while this method did sort by gender first it did not seem
    //to sort by passenger number, I cannot see anything particularly
    //wrong with my code in the comparator class for this
    public static void sortPassengersByGenderThenPassengerNumber (ArrayList<Passenger> passengers) {
        Collections.sort(passengers, new PassengerPNumberWithinGenderComparator());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    //Q14 sortPassengersByFareThenSurvival
    public static void sortPassengersByFareThenSurvival(ArrayList<Passenger> passengers) {
        Collections.sort(passengers, new PassengersSurvivedWithinFareComparator());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    //Q15 sortPassengersByTicketClass
    //I wasn't entirely sure how to compare enum values
    //the sorting seemed to have worked for the first couple of
    //passengers as it had all THIRD first
    //but after a while it seemed to devolve and have some
    //THIRD in with SECOND and the same further on
    //I have tried multiple methods to fix this, but I
    //couldn't find an answer
    public static void sortPassengersByTicketClass (ArrayList<Passenger> passengers) {
        Collections.sort(passengers, new PassengersTicketClassComparator());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    //Q17 sortPassengersByTicketNumberLambda
    public static void sortPassengersByTicketNumberLambda (ArrayList<Passenger> passengers) {
        Collections.sort(passengers,(p1,p2) -> p1.getTicketNumber().compareTo(p2.getTicketNumber()));
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }

    //Q18 sortPassengersByTicketNumberStatic
    public static void sortPassengersByTicketNumberStatic (ArrayList<Passenger> passengers) {
        Collections.sort(passengers, new PassengersTicketNumberComparator());
        for (Passenger passenger : passengers) {
            System.out.println(passenger);
        }
    }
}
