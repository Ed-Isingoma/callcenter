package com.callcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Views {
    public static int getIntInput() {
        System.out.print("Input here: ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int number = scanner.nextInt();
            if (number > 0) {
                return number;
            }
        }
        System.out.println("Needs to be a positive integer");
        return 0;
    }

    public static String[] getAgentData() {
        System.out.print("Enter Agent Name: ");
        Scanner scanner = new Scanner(System.in);

        String agentName = scanner.nextLine();
        System.out.print("Enter Agent Email: ");
        String agentEmail = scanner.nextLine();

        return new String[]{agentName, agentEmail};
    }

    public static String[] getCustomerData() {
        System.out.print("Enter Customer Name: ");
        Scanner scanner = new Scanner(System.in);

        String customerName = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String customerEmail = scanner.nextLine();
        return new String[]{customerName, customerEmail};
    }

    public static String[] getTicketData() {
        System.out.print("Enter Ticket Category: ");
        Scanner scanner = new Scanner(System.in);

        String ticketCategory = scanner.nextLine();
        System.out.print("Enter Ticket Description: ");
        String ticketDescription = scanner.nextLine();
        System.out.println("Enter Respective Customer ID:");
        int customerId;
        do {
            customerId = getIntInput();
        } while (customerId <= 0);
        return new String[]{ticketCategory, ticketDescription, Integer.toString(customerId)};
    }

    static void showAgents(List<Agent> agents) {
        for (Agent o : agents) {
            System.out.println("Agent Name: " + o.getAgentName());
            System.out.println("Agent Email: " + o.getAgentEmail());
            System.out.println("Agent ID: " + o.getAgentId());
            System.out.println("==================");
        }
    }

    static void showCustomers(List<Customer> customers) {
        for (Customer c : customers) {
            System.out.println("Customer Name: " + c.getCustomerName());
            System.out.println("Customer Email: " + c.getCustomerEmail());
            System.out.println("Customer ID: " + c.getCustomerId());
            System.out.println("==================");
        }
    }

    static void showTickets(List<Ticket> tickets, List<Customer> customers, List<Agent> agents) {
        for (Ticket t : tickets) {
            String attachedCustomer = null;
            String attachedAgent = null;

            for (Customer c : customers) {
                if (c.getCustomerId() == t.getCustomerId()) {
                    attachedCustomer = c.getCustomerName();
                    break;
                }
            }
            for (Agent a : agents) {
                if (a.getAgentId() == t.getAgentId()) {
                    attachedAgent = a.getAgentName();
                    break;
                }
            }

            System.out.println("Ticket Category: " + t.getCategory());
            System.out.println("Ticket Status: " + t.getStatus());
            System.out.println("Ticket Description: " + t.getDescription());
            System.out.println("Ticket ID: " + t.getTicketId());
            System.out.println("Attached Customer: " + (attachedCustomer != null ? attachedCustomer : "not found"));
            System.out.println("Attached agent: " + (attachedAgent != null ? attachedAgent : "not found"));
        }
    }

    static Agent redoAgent(Agent agent) {
        List<Agent> showPackage = new ArrayList<>();
        showPackage.add(agent);
        showAgents(showPackage);
        System.out.println("This is the data to be overwritten (Press enter to keep a variable as is)");
        String[] overwrite = getAgentData();
        if (!overwrite[0].isEmpty()) {
            agent.setAgentName(overwrite[0]);
        }
        if (!overwrite[1].isEmpty()) {
            agent.setAgentEmail(overwrite[1]);
        }
        return agent;
    }

    static Customer redoCustomer(Customer customer) {
        List<Customer> showPackage = new ArrayList<>();
        showPackage.add(customer);
        showCustomers(showPackage);
        System.out.println("This is the data to be overwritten (Press enter to keep a variable as is)");
        String[] overwrite = getCustomerData();
        if (!overwrite[0].isEmpty()) {
            customer.setCustomerName(overwrite[0]);
        }
        if (!overwrite[1].isEmpty()) {
            customer.setCustomerEmail(overwrite[1]);
        }
        return customer;
    }

    static Ticket redoTicket(Ticket ticket, Customer customer, Agent agent) {
        List<Ticket> showPackage = new ArrayList<>();
        showPackage.add(ticket);

        List<Customer> customerPackage = new ArrayList<>();
        customerPackage.add(customer);

        List<Agent> agentPackage = new ArrayList<>();
        agentPackage.add(agent);

        showTickets(showPackage, customerPackage, agentPackage);
        System.out.println("This is the data to be overwritten (Press enter to keep a variable as is)");
        String[] overwrite = getTicketData();
        if (!overwrite[0].isEmpty()) {
            ticket.setCategory(overwrite[0]);
        }
        if (!overwrite[1].isEmpty()) {
            ticket.setDescription(overwrite[1]);
        }
        if (!overwrite[2].isEmpty()) {
            ticket.setCustomerId(Integer.parseInt(overwrite[2]));
        }
        return ticket;
    }

    static void searchTicket(List<Ticket> ticketsList, List<Customer> customersList, List<Agent> agentsList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose what to search by:");
        System.out.println("1. Ticket Category \n2. Ticket Description \n3. Ticket Status \n4.Customer Name");
        int choice;
        do {
            choice = Views.getIntInput();
            if (choice < 1 || choice > 4) {
                System.out.println("Wrong Input. Try again");
            }
        } while (choice < 1 || choice > 4);

        List<Ticket> matched;
        switch (choice) {
            case 1:
                System.out.print("Enter category: ");
                String cat = sc.nextLine();
                matched = ticketsList.stream()
                        .filter(t -> t.getCategory().equalsIgnoreCase(cat))
                        .collect(Collectors.toList());
                break;
            case 2:
                System.out.print("Enter keyword in description: ");
                String desc = sc.nextLine();
                matched = ticketsList.stream()
                        .filter(t -> t.getDescription().toLowerCase().contains(desc.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case 3:
                System.out.print("Enter status (pending/resolved): ");
                String stat = sc.nextLine();
                matched = ticketsList.stream()
                        .filter(t -> t.getStatus().equalsIgnoreCase(stat))
                        .collect(Collectors.toList());
                break;
            case 4:
                System.out.print("Enter customer name: ");
                String name = sc.nextLine();

                Set<Integer> ids = customersList.stream()
                        .filter(c -> c.getCustomerName().toLowerCase().contains(name.toLowerCase()))
                        .map(Customer::getCustomerId)
                        .collect(Collectors.toSet());
                matched = ticketsList.stream()
                        .filter(t -> ids.contains(t.getCustomerId()))
                        .collect(Collectors.toList());
                break;
            default:
                matched = List.of();
        }

        if (matched.isEmpty()) {
            System.out.println("No tickets found for that criteria.");
        } else {
            showTickets(matched, customersList, agentsList);
        }
    }
}
