package myPackage;

import java.util.*;

public class Train {
    // private Map<Integer, String> berths = new HashMap<>();
    // boolean[] isBooked = new boolean[9];
    int totalTickets = 0;
    int availableSeats = 7;
    int currWL = 0;
    private Set<Integer> u = new HashSet<>();
    private Set<Integer> m = new HashSet<>();
    private Set<Integer> l = new HashSet<>();
    private Set<Integer> rac1 = new HashSet<>();
    private Set<Integer> rac2 = new HashSet<>();
    Queue<Passenger> wl = new LinkedList<>();
    List<Set<Integer>> berths = new ArrayList<>();
    // List<Set<Integer>> racAndwl = new ArrayList<>();
    private Map<Integer, Passenger> tickets = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    public Train() {
        // berths.put(1, "u");
        // berths.put(2, "m");
        // berths.put(3, "l");
        // berths.put(4, "u");
        // berths.put(5, "m");
        // berths.put(6, "l");
        // berths.put(7, "rac");
        // berths.put(8, "rac");

        u.add(1);
        u.add(4);
        u.add(8);
        m.add(2);
        m.add(5);
        l.add(3);
        l.add(6);
        rac1.add(7);
        rac2.add(7);

        berths.add(l);
        berths.add(m);
        berths.add(u);
        // berths.add(rac);
        // racAndwl.add(rac);
        // racAndwl.add(wl);
    }

    public void printChat() {
        System.out.println("TicketNo   PassengerName   PassengerAge   Gender   BerthNumber   ConfirmedBerth   Status");
        // for (Map.Entry<Integer, Passenger> e : tickets.entrySet()) {
        // int ticket = e.getKey();
        // Passenger p = e.getValue();

        // System.out.println(ticket + " " + p.name + " " + p.age + " " + p.gender + " "
        // + p.berthPreference);
        // }

        for (int i = 1; i <= totalTickets; i++) {
            Passenger p = tickets.get(i);
            System.out.println(i + "          " + p.name + "         " + p.age + "           " + p.gender + "          "
                    + p.berthNumber + "         "
                    + p.berthPreference + "       " + p.status);
        }

        System.out.println("____________________________________________________________________");
    }

    public void printTicketDetails(int ticket) {
        Passenger currPassenger = tickets.get(ticket);

        System.out.println("Ticket Number: " + ticket);
        System.out.println("Passenger Name: " + currPassenger.name);
        System.out.println("Passenger Age: " + currPassenger.age);
        System.out.println("Passenger Gender: " + currPassenger.gender);
        System.out.println("Passenger Berth Number: " + currPassenger.berthNumber);
        System.out.println("Passenger Berth: " + currPassenger.berthPreference);
        System.out.println("Status: " + currPassenger.status);
        System.out.println("___________________________________________");
    }

    public Passenger createPassenger() {
        System.out.println("Enter name: ");
        String name = sc.nextLine();
        System.out.println("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine(); // It will consume the next line character "\n" left by nextInt() method
                       // Otherwise the gender will be automatically take the \n next line character by
                       // the nextLine() method at gender
        System.out.println("Enter gender: ");
        String gender = sc.nextLine();
        System.out.println("Enter berth preference u-Upper m-Middle l-Lower: ");
        String berthPreference = sc.nextLine();

        return new Passenger(name, age, gender, berthPreference);
    }

    public String setBerthOfTicket(Set<Integer> set) {
        if (set == l)
            return "l";
        else if (set == m)
            return "m";

        return "u";
    }

    public int confirmBerth(Passenger p) {
        int ticket = 0;

        if (p.gender == null)
            p.gender = "    ";

        int berth = 0;
        switch (p.berthPreference) {
            case "l":
                berth = 0;
                break;
            case "m":
                berth = 1;
                break;
            case "u":
                berth = 2;
                break;
            // case "rac":
            // berth = 3;
            // break;
            default:
                if (p.age >= 60)
                    berth = 0;
                else if (p.age < 60 && p.age > 30)
                    berth = 1;
                else
                    berth = 2;
        }

        // List<Set<Integer>> berths = new ArrayList<>();
        // berths.add(l);
        // berths.add(m);
        // berths.add(u);
        // berths.add(rac);

        int i = 0;
        while (true) {
            if (i == berth) {
                if (berths.get(i % 3).size() > 0) {
                    Iterator<Integer> iterator = berths.get(i % 3).iterator();
                    ticket = (int) iterator.next();
                    berths.get(i % 3).remove(ticket);
                    p.berthPreference = setBerthOfTicket(berths.get(i % 3)); // we have to set the booked berth here in
                                                                             // the place of "m"
                    p.status = "Confirmed";
                    totalTickets++;
                    p.berthNumber = ticket;
                    tickets.put(totalTickets, p);
                    // isBooked[ticket] = true;
                    break;
                } else {
                    berth++;
                }
            }
            i++;
        }

        return totalTickets;
    }

    public int bookTicket() {
        if (availableSeats == 0) {
            if (rac1.size() > 0) {
                totalTickets++;
                Passenger p = createPassenger();
                Iterator<Integer> iterator = rac1.iterator();
                int ticket = iterator.next();
                p.berthNumber = ticket;
                p.berthPreference = "rac1";
                p.status = "Confirmed";
                rac1.remove(ticket);
                tickets.put(totalTickets, p);
                return totalTickets;
            } else if (rac2.size() > 0) {
                totalTickets++;
                Passenger p = createPassenger();
                Iterator<Integer> iterator = rac2.iterator();
                int ticket = iterator.next();
                p.berthNumber = ticket;
                p.berthPreference = "rac2";
                p.status = "Confirmed";
                rac2.remove(ticket);
                tickets.put(totalTickets, p);
                return totalTickets;
            } else if (currWL < 2) {
                totalTickets++;
                currWL++;
                Passenger p = createPassenger();
                p.status = "wl";
                wl.add(p);
                tickets.put(totalTickets, p);
                return totalTickets;
            } else {
                return -1;
            }
        }

        Passenger p = createPassenger();
        int ticket = confirmBerth(p);

        availableSeats--;
        // for (int i = 1; i < 9; i++) {
        // if (!isBooked[i]) {
        // isBooked[i] = true;
        // return i;
        // }
        // }

        return ticket;
    }

    public boolean cancelTicket(int ticket) {
        // if (!isBooked[ticket])
        // return false;
        if (!tickets.containsKey(ticket))
            return false;

        // isBooked[ticket] = false;
        Passenger currPassenger = tickets.get(ticket);
        if (currPassenger.status == "wl") { // When the wl guy want to cancel the ticket then the ticket from wl queue
                                            // also be deleted. Here we have to code it for that purpose. And we have to
                                            // see if the wl has to store either passengers or the ticket numbers for
                                            // ease of deleting from the wl queue.And after deleting from the wl queue
                                            // we have to update the status of the passenger to "cancelled" from the
                                            // status "wl" by using the ticket number to get the passenger object from
                                            // the tickets map. We have to see, is it possible to iterate through the
                                            // queue to find the passenger or the ticketnumber to delete it from the
                                            // queue.
            currPassenger.status = "Cancelled";
            return true;
        }
        currPassenger.status = "Cancelled";

        if (currPassenger.berthPreference == "rac1" || currPassenger.berthPreference == "rac2") {
            tickets.get(ticket).status = "Cancelled";
            return true;
        }

        while (wl.peek().status == "Cancelled") {
            wl.remove();
            currWL--;
        }

        if (currWL > 0) {
            currWL--;
            Passenger p = wl.remove();
            // totalTickets++;
            p.berthPreference = currPassenger.berthPreference;
            p.status = "Confirmed";
            p.berthNumber = currPassenger.berthNumber;
            // tickets.put(totalTickets, p);
            return true;
        }

        availableSeats++;

        switch (currPassenger.berthPreference) {
            case "l":
                l.add(currPassenger.berthNumber);
                break;
            case "m":
                m.add(currPassenger.berthNumber);
                break;
            case "u":
                u.add(currPassenger.berthNumber);
                break;
            // default:
            // rac.add(ticket);
            // break;
        }

        return true;
    }
}
