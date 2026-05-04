package com.example.Project_1.Engine;

import com.example.Project_1.Domain.Company;
import com.example.Project_1.Domain.Project;
import com.example.Project_1.Domain.ProjectStatus;
import com.example.Project_1.ConsoleUI.ConsoleUI;

public class GameEngine {

    private Company company;
    private ConsoleUI ui;
    private boolean running;
    private int turn;

    public GameEngine(Company company, ConsoleUI ui) {
        this.company = company;
        this.ui = ui;
        this.running = true;
        this.turn = 1;
    }

    public void start() {
        while (running) {
            ui.showTurnHeader(turn);
            ui.showCompanyStatus(company);
            ui.showMainMenu();

            int choice = ui.readMenuChoice();

            handleChoice(choice);

            if (running) {
                advanceTurn();
                turn++;
            }
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> ui.showCompanyStatus(company);
            case 2 -> startPlannedProjects();
            case 3 -> workOnProjects();
            case 4 -> running = false;
            default -> ui.showMessage("Invalid menu option.");
        }
    }

    private void startPlannedProjects() {
        boolean started = false;
        for (Project project : company.getProjects()) {
            if (project.getStatus() == ProjectStatus.PLANNED) {
                project.start();
                started = true;
            }
        }
        if (started) {
            ui.showMessage("All planned projects have been started.");
        } else {
            ui.showMessage("No planned projects to start.");
        }
    }

    private void workOnProjects() {
        for (Project project : company.getProjects()) {
            project.workOneTurn();
        }
        ui.showMessage("Projects worked for one turn.");
    }

    private void advanceTurn() {
        if (allProjectsFinished()) {
            ui.showMessage("All projects are finished. Game over.");
            running = false;
        }
    }

    private boolean allProjectsFinished() {
        if (company.getProjects().isEmpty()) {
            return false;
        }

        for (Project project : company.getProjects()) {
            if (!project.isFinished()) {
                return false;
            }
        }

        return true;
    }
}
