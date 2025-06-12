package com.callcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Views {
    public static int getIntInput() {
        System.out.print("Input here: ");
        Scanner scanner = new Scanner(System.in);
        int number;
        if (scanner.hasNextInt()) {
            number = scanner.nextInt();
            return number;
        } else {
            System.out.println("Needs to be an integer");
            return 0;
        }
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
            System.out.println("Ticket Description: " + t.getDescription());
            System.out.println("Ticket ID: " + t.getTicketId());
            System.out.println("Attached Customer: " + (attachedCustomer != null ? attachedCustomer : "not found"));
            System.out.println("Attached agent: " + (attachedAgent != null ? attachedAgent : "not found"));
        }
    }

    static String[] redoAgent(Agent agent) {
        List<Agent> showPackage = new ArrayList<>();
        showPackage.add(agent);
        showAgents(showPackage);
        System.out.println("This is the data to be overwritten (Press enter to keep a variable as is)");
        String[] overwrite = getAgentData();
        if (overwrite[0].isEmpty()) {
            overwrite[0] = agent.getAgentName();
        }
        if (overwrite[1].isEmpty()) {
            overwrite[1] = agent.getAgentEmail();
        }
        return overwrite;
    }

    static String[] redoCustomer(Customer customer) {
        List<Customer> showPackage = new ArrayList<>();
        showPackage.add(customer);
        showCustomers(showPackage);
        System.out.println("This is the data to be overwritten (Press enter to keep a variable as is)");
        String[] overwrite = getCustomerData();
        if (overwrite[0].isEmpty()) {
            overwrite[0] = customer.getCustomerName();
        }
        if (overwrite[1].isEmpty()) {
            overwrite[1] = customer.getCustomerEmail();
        }
        return overwrite;
    }

    static String[] redoTicket(Ticket ticket, Customer customer, Agent agent) {
        List<Ticket> showPackage = new ArrayList<>();
        showPackage.add(ticket);

        List<Customer> customerPackage = new ArrayList<>();
        customerPackage.add(customer);

        List<Agent> agentPackage = new ArrayList<>();
        agentPackage.add(agent);

        showTickets(showPackage, customerPackage, agentPackage);
        System.out.println("This is the data to be overwritten (Press enter to keep a variable as is)");
        String[] overwrite = getTicketData();
        if (overwrite[0].isEmpty()) {
            overwrite[0] = ticket.getCategory();
        }
        if (overwrite[1].isEmpty()) {
            overwrite[1] = ticket.getDescription();
        }
        if (overwrite[2].isEmpty()) {
            overwrite[2] = Integer.toString(ticket.getTicketId());
        }
        return overwrite;
    }
}
