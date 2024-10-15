import java.util.*;

class Bike {
    private String bikeId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Bike(String bikeId, String brand, String model, double basePricePerDay) {
        this.bikeId = bikeId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getBikeId() {
        return bikeId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnBike() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Bike bike;
    private Customer customer;
    private int days;

    public Rental(Bike bike, Customer customer, int days) {
        this.bike = bike;
        this.customer = customer;
        this.days = days;
    }

    public Bike getBike() {
        return bike;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class BikeRentalSystem {
    private List<Bike> bikes;
    private List<Customer> customers;
    private List<Rental> rentals;

    public BikeRentalSystem() {
        bikes = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addBike(Bike bike) {
        bikes.add(bike);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentBike(Bike bike, Customer customer, int days) {
        if (bike.isAvailable()) {
            bike.rent();
            rentals.add(new Rental(bike, customer, days));

        } else {
            System.out.println("Bike is not available for rent.");
        }
    }

    public void returnBike(Bike bike) {
        bike.returnBike();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getBike() == bike) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Bike was not rented.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n\t\t\t\t\t\t\t ---------- Welcome to Bike Rental Corner  ----------\n");
            System.out.println("What you want to do ...\n");
            System.out.println("1. Rent a Bike");
            System.out.println("2. Return a Bike");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            if (choice == 1) {
                System.out.println("\n---- Rent a Bike ----\n");
                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("\nAvailable Bikes:");
                for (Bike bike : bikes) {
                    if (bike.isAvailable()) {
                        System.out.println(bike.getBikeId() + " - " + bike.getBrand() + " " + bike.getModel());
                    }
                }

                System.out.print("\nEnter the bike ID you want to rent: ");
                String bikeId = sc.nextLine();

                System.out.print("Enter the number of how many days you want to rent the bike: ");
                int rentalDays = sc.nextInt();
                sc.nextLine(); 

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Bike selectedBike = null;
                for (Bike bike : bikes) {
                    if (bike.getBikeId().equals(bikeId) && bike.isAvailable()) {
                        selectedBike = bike;
                        break;
                    }
                }

                if (selectedBike != null) {
                    double totalPrice = selectedBike.calculatePrice(rentalDays);
                    System.out.println("\n---- Rental Information ----\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Bike: " + selectedBike.getBrand() + " " + selectedBike.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nDo you want to rent the bike (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentBike(selectedBike, newCustomer, rentalDays);
                        System.out.println("\nCongratulations "+customerName+" !!! "+selectedBike.getBrand() + " " + selectedBike.getModel()+" rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid bike selection or bike not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n---- Return a Bike ----\n");
                System.out.print("Enter the bike ID you want to return: ");
                String bikeId = sc.nextLine();

                Bike bikeToReturn = null;
                for (Bike bike : bikes) {
                    if (bike.getBikeId().equals(bikeId) && !bike.isAvailable()) {
                        bikeToReturn = bike;
                        break;
                    }
                }

                if (bikeToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getBike() == bikeToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnBike(bikeToReturn);
                        System.out.println("Bike returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Bike was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid bike ID or bike is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the bike Rental Corner!");
        sc.close();
    }

}
public class Main{
    public static void main(String[] args) {
        BikeRentalSystem rentalSystem = new BikeRentalSystem();


        Bike bike1 = new Bike("B001", "Royal Enfild", "Hunter 350", 120.0); 
        Bike bike2 = new Bike("B002", "Yamaha", "XSR155", 70.0);
        Bike bike3 = new Bike("B003", "Bajaj", "Avenger 220", 90.0);
        Bike bike4 = new Bike("B004", "Royal Enfild", "Himalayan 450", 150.0);
        Bike bike5 = new Bike("B005", "Hero", "Xpuls", 80.0);
        Bike bike6 = new Bike("B006", "BMW", "310", 150.0);
        rentalSystem.addBike(bike1);
        rentalSystem.addBike(bike2);
        rentalSystem.addBike(bike3);
        rentalSystem.addBike(bike4);
        rentalSystem.addBike(bike5);
        rentalSystem.addBike(bike6);

        rentalSystem.menu();
    }
}