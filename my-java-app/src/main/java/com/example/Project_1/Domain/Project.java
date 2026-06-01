package com.example.Project_1.Domain;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;
    private int requiredWork;
    private int progress;
    private List<Workable> team;
    private List<Employee> assignedEmployees;
    private ProjectStatus status;
    private double budget;
    private int deadline;
    private String priority;
    private int turnsCompleted;

    public Project(String name, int requiredWork, double budget, int deadline, String priority) {
        this.name = name;
        this.requiredWork = requiredWork;
        this.budget = budget;
        this.deadline = deadline;
        this.priority = priority;
        this.progress = 0;
        this.turnsCompleted = 0;
        this.team = new ArrayList<>();
        this.assignedEmployees = new ArrayList<>();
        this.status = ProjectStatus.PLANNED;
    }

    // Legacy constructor for backward compatibility
    public Project(String name, int requiredWork) {
        this(name, requiredWork, 10000, 30, "NORMAL");
    }

    public void addWorker(Workable workable) {
        if (workable == null) {
            throw new IllegalArgumentException("Worker cannot be null.");
        }
        team.add(workable);
    }

    public void assignEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (!assignedEmployees.contains(employee)) {
            assignedEmployees.add(employee);
            addWorker(employee);
        }
    }

    public void removeEmployee(Employee employee) {
        if (assignedEmployees.remove(employee)) {
            team.remove(employee);
        }
    }

    public boolean hasEmployee(Employee employee) {
        return assignedEmployees.contains(employee);
    }

    public void start() {
        if (status == ProjectStatus.PLANNED) {
            status = ProjectStatus.IN_PROGRESS;
        }
    }
    public void putOnHold() {
        if (status == ProjectStatus.IN_PROGRESS) {
            status = ProjectStatus.ONHOLD;
        }
    }
    public void resume() {
        if (status == ProjectStatus.ONHOLD) {
            status = ProjectStatus.IN_PROGRESS;
        }
    }

    public void workOneTurn() {
        if (status != ProjectStatus.IN_PROGRESS) {
            return;
        }

        turnsCompleted++;

        for (Workable workable : team) {
            progress += workable.work();
        }

        if (progress >= requiredWork) {
            progress = requiredWork;
            status = ProjectStatus.FINISHED;
        }

        if (turnsCompleted > deadline) {
            status = ProjectStatus.FINISHED;
        }
    }

    public boolean isFinished() {
        return status == ProjectStatus.FINISHED;
    }

    public String getName() {
        return name;
    }

    public int getProgress() {
        return progress;
    }

    public int getRequiredWork() {
        return requiredWork;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public double getBudget() {
        return budget;
    }

    public int getDeadline() {
        return deadline;
    }

    public String getPriority() {
        return priority;
    }

    public int getTurnsCompleted() {
        return turnsCompleted;
    }

    public List<Employee> getAssignedEmployees() {
        return new ArrayList<>(assignedEmployees);
    }

    public int getTeamSize() {
        return assignedEmployees.size();
    }

    public double getCompletionPercentage() {
        return (double) progress / requiredWork * 100;
    }

    public boolean isDeadlineExceeded() {
        return turnsCompleted > deadline && status != ProjectStatus.FINISHED;
    }
}