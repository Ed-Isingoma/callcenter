package com.callcenter;

class Agent {
    private String agentName;
    private String agentEmail;
    private final int agentId;

    public Agent(String name, String email, int id) {
        this.agentName = name;
        this.agentEmail = email;
        this.agentId = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }
}

class Customer {
    private String customerName;
    private String customerEmail;
    private final int customerId;

    public Customer(String name, String email, int id) {
        this.customerName = name;
        this.customerEmail = email;
        this.customerId = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

class Ticket {
    private String description;
    private String category;
    private int customerId;
    private int agentId;
    private final int ticketId;
    private String status = "pending";

    public Ticket(String description, String category, int customerId, int agentId, int ticketId) {
        this.description = description;
        this.category = category;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.agentId = agentId;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getAgentId() {
        return agentId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

