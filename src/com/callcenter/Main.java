package com.callcenter;

import java.util.ArrayList;

public class Main {
    private static final ArrayList<Ticket> ticketsList = new ArrayList<>();
    private static final ArrayList<Customer> customersList = new ArrayList<>();
    private static final ArrayList<Agent> agentsList = new ArrayList<>();

    public static void main(String[] args) {
        if (agentsList.isEmpty()) {
            agentsList.add(new Agent("Admin", "admin@gmail.com", 1 ));
        }
        int agentInCharge = 0;
        int outerMenu = 0;
        do {
            System.out.println("Welcome. Choose Action \n1. Log In \n2.Exit");
            int num = Views.getIntInput();
            if (num == 1) {
                System.out.println("Logging in...");
                break;
            } else if (num == 2) {
                outerMenu = -1;
                System.out.println("Bye.");
                break;
            } else {
                System.out.println("Wrong Input. Try again");
            }
        } while (true);

        if (outerMenu == -1) {
            return;
        }

        do {
            System.out.println("Enter Agent ID:");
            int agentId = Views.getIntInput();
            if (agentId == 0) {
                System.out.println("Wrong Input. Try again");
            } else if (agentsList.stream().noneMatch(agent -> agent.getAgentId() == agentId)) {
                System.out.println("Agent ID not found. Try again");
            } else {
                agentInCharge = agentId;
                break;
            }
        } while(true);

        do {
            System.out.println("1. Create entity \n2. Edit entity \n3. View entity");
            System.out.println("4. Delete entity \n5. Mark ticket as solved \n6. Search ticket \n7. Exit");
            int mainActionChoice = Views.getIntInput();
            switch (mainActionChoice) {
                case 1:
                    createEntity(agentInCharge);
                    break;
                case 2:
                    editEntity();
                    break;
                case 3:
                    viewEntity();
                    break;
                case 4:
                    deleteEntity();
                    break;
                case 5:
                    solveTicket();
                    break;
                case 6:
                    Views.searchTicket(ticketsList, customersList, agentsList);
                    break;
                case 7:
                    System.out.println("Bye");
                    return;
                default:
                    System.out.println("Wrong Input. Try again");
            }
        } while (true);
    }

    private static void createEntity(int agentInCharge) {
        int whichEntity = selectWhichEntity();
        switch (whichEntity) {
            case 1:
                String[] agentData = Views.getAgentData();
                int newAgentId = agentsList.isEmpty() ? 1 : agentsList.getLast().getAgentId() + 1;
                agentsList.add(new Agent(agentData[0], agentData[1], newAgentId));
                break;
            case 2:
                String[] customerData = Views.getCustomerData();
                int newCustomerId = customersList.isEmpty() ? 1 : customersList.getLast().getCustomerId() + 1;
                customersList.add(new Customer(customerData[0], customerData[1], newCustomerId));
                break;
            default:
                String[] ticketData = Views.getTicketData();
                int newTicketId = ticketsList.isEmpty() ? 1 : ticketsList.getLast().getTicketId() + 1;
                ticketsList.add(new Ticket(ticketData[0], ticketData[1] ,Integer.parseInt(ticketData[2]), agentInCharge, newTicketId));
        }
        System.out.println("Entity Created Successfully");
    }

    private static void deleteEntity() {
        int whichEntity = selectWhichEntity();
        switch(whichEntity) {
            case 1:
                System.out.println("Enter ID of Agent to delete");
                int agentId = Views.getIntInput();
                agentsList.removeIf(agent -> agent.getAgentId() == agentId);
                break;
            case 2:
                System.out.println("Enter ID of Customer to delete");
                int customerId = Views.getIntInput();
                customersList.removeIf(customer -> customer.getCustomerId() == customerId);
                break;
            default:
                System.out.println("Enter ID of Ticket to delete");
                int ticketId = Views.getIntInput();
                ticketsList.removeIf(ticket -> ticket.getTicketId() == ticketId);
        }
        System.out.println("Entity Deleted Successfully, (if it was there)");
    }

    private static void editEntity() {
        int whichEntity = selectWhichEntity();
        switch(whichEntity) {
            case 1:
                System.out.println("Enter ID of Agent to edit");
                int agentId = Views.getIntInput();
                agentsList.replaceAll(ag ->
                        ag.getAgentId() == agentId
                                ? Views.redoAgent(ag) : ag );
                break;
            case 2:
                System.out.println("Enter ID of Customer to edit");
                int customerId = Views.getIntInput();
                customersList.replaceAll(cus ->
                        cus.getCustomerId() == customerId ? Views.redoCustomer(cus) : cus);
                break;
            default:
                System.out.println("Enter ID of Ticket to edit");
                int ticketId = Views.getIntInput();

                ticketsList.replaceAll(tic -> {
                    if (tic.getTicketId() == ticketId) {
                        Customer cust = customersList.stream()
                                .filter(c -> c.getCustomerId() == tic.getCustomerId())
                                .findFirst()
                                .orElse(null);
                        Agent    ag   = agentsList.stream()
                                .filter(a -> a.getAgentId() == tic.getAgentId())
                                .findFirst()
                                .orElse(null);
                        return Views.redoTicket(tic, cust, ag);
                    }
                    return tic;
                });
        }
        System.out.println("Entity Edited Successfuly");
    }

    private static void viewEntity() {
        int whichEntity = selectWhichEntity();
        switch(whichEntity) {
            case 1:
                Views.showAgents(agentsList);
                break;
            case 2:
                Views.showCustomers(customersList);
                break;
            default:
                Views.showTickets(ticketsList, customersList, agentsList);
        }
    }

    private static int selectWhichEntity() {
        System.out.println("Select Which Entity.");
        System.out.println("1. Agent \n2. Customer \n3. Ticket");
        int choice;
        do {
            choice = Views.getIntInput();
            if (choice < 1 || choice > 3) {
                System.out.println("Wrong Input. Try again");
            }
        } while (choice < 1 || choice > 3);
        return choice;
    }

    private static void solveTicket() {
        do {
            System.out.println("Enter ticket ID:");
            int ticketId = Views.getIntInput();
            if (ticketId == 0) {
                System.out.println("Wrong Input. Try again");
            } else if (ticketsList.stream().noneMatch(ticket -> ticket.getTicketId() == ticketId)) {
                System.out.println("Agent ID not found. Try again");
            } else {
                ticketsList.stream().filter(ticket -> ticket.getTicketId() == ticketId)
                        .findFirst().ifPresent(ticket -> ticket.setStatus("resolved"));
                System.out.println("Done resolving ticket" + ticketId);
                break;
            }
        } while(true);
    }
}
