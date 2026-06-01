package com.example.Project_1.Engine;

import com.example.Project_1.Domain.Company;
import com.example.Project_1.Domain.Project;
import com.example.Project_1.Domain.ProjectStatus;
import com.example.Project_1.Domain.Developer;
import com.example.Project_1.Domain.Tester;
import com.example.Project_1.Domain.Intern;
import com.example.Project_1.Domain.Employee;
import com.example.Project_1.ConsoleUI.ConsoleUI;
import java.util.Scanner;

public class GameEngine {

    private Company company;
    private ConsoleUI ui;
    private boolean running;
    private int turn;
    private Scanner scanner;

    public GameEngine(Company company, ConsoleUI ui) {
        this.company = company;
        this.ui = ui;
        this.running = true;
        this.turn = 1;
        this.scanner = new Scanner(System.in);
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
            case 4 -> hireEmployee();
            case 5 -> createNewProject();
            case 6 -> manageProjectTeam();
            case 7 -> showProjectDetails();
            case 8 -> running = false;
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

    private void hireEmployee() {
        boolean hiring = true;
        while (hiring) {
            ui.showHireMenu();
            int choice = ui.readHireChoice();

            Employee newEmployee = null;
            String employeeType = null;
            double salary = 0;

            switch (choice) {
                case 1 -> {
                    employeeType = "Developer";
                    salary = 8000;
                    int skill = getValidatedSkill(8, 10);
                    if (skill == -1) continue;
                    newEmployee = new Developer(ui.readEmployeeName(), skill, salary);
                }
                case 2 -> {
                    employeeType = "Tester";
                    salary = 6500;
                    int skill = getValidatedSkill(5, 8);
                    if (skill == -1) continue;
                    newEmployee = new Tester(ui.readEmployeeName(), skill, salary);
                }
                case 3 -> {
                    employeeType = "Intern";
                    salary = 3000;
                    int skill = getValidatedSkill(3, 5);
                    if (skill == -1) continue;
                    newEmployee = new Intern(ui.readEmployeeName(), skill, salary);
                }
                case 4 -> hiring = false;
                default -> ui.showMessage("Invalid selection. Please try again.");
            }

            if (newEmployee != null) {
                if (company.canHire(salary)) {
                    company.hire(newEmployee);
                    ui.showHireResult(true, newEmployee.getName() + " (" + employeeType +
                            ") hired successfully! Cost: $" + String.format("%.0f", salary));
                } else {
                    ui.showHireResult(false, "Not enough cash to hire " + employeeType +
                            ". Required: $" + String.format("%.0f", salary) +
                            ", Available: $" + String.format("%.0f", company.getCash()));
                }
            }
        }
    }

    private int getValidatedSkill(int min, int max) {
        int skill = ui.readEmployeeSkill();
        if (skill >= min && skill <= max) {
            return skill;
        } else {
            ui.showMessage("Invalid skill level. Must be between " + min + " and " + max);
            return -1;
        }
    }

    private void createNewProject() {
        ui.showCreateProjectMenu();

        String projectName = ui.readProjectName();
        if (projectName.isEmpty()) {
            ui.showMessage("Project name cannot be empty.");
            return;
        }

        int requiredWork = ui.readRequiredWork();
        if (requiredWork <= 0 || requiredWork > 100) {
            ui.showMessage("Invalid work units. Must be between 1 and 100.");
            return;
        }

        double budget = ui.readBudget();
        if (budget <= 0) {
            ui.showMessage("Budget must be positive.");
            return;
        }

        if (company.getCash() < budget) {
            ui.showProjectCreationResult(false, "Not enough cash. Required: $" + String.format("%.0f", budget) +
                    ", Available: $" + String.format("%.0f", company.getCash()));
            return;
        }

        int deadline = ui.readDeadline();
        if (deadline <= 0) {
            ui.showMessage("Deadline must be positive.");
            return;
        }

        String priority = ui.readProjectPriority();

        Project newProject = new Project(projectName, requiredWork, budget, deadline, priority);
        company.startProject(newProject);

        ui.showProjectCreationResult(true, projectName + " created with budget $" + String.format("%.0f", budget));

        // Ask if user wants to assign employees immediately
        if (ui.readYesNoInput("Would you like to assign employees to this project now?")) {
            assignEmployeeToProject(newProject);
        }
    }

    private void manageProjectTeam() {
        if (company.getProjects().isEmpty()) {
            ui.showMessage("No projects available.");
            return;
        }

        System.out.println("\n  Select project to manage:");
        for (int i = 0; i < company.getProjects().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + company.getProjects().get(i).getName());
        }
        System.out.println("  " + (company.getProjects().size() + 1) + ". Back");

        System.out.print("  ► Select project: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return;
        }
        int projectChoice = scanner.nextInt();
        scanner.nextLine();

        if (projectChoice < 1 || projectChoice > company.getProjects().size()) {
            return;
        }

        Project selectedProject = company.getProjects().get(projectChoice - 1);
        ui.showManageTeamMenu(selectedProject);

        boolean managing = true;
        while (managing) {
            System.out.println("\n  1. ░ Assign Employee to Project");
            System.out.println("  2. ░ Remove Employee from Project");
            System.out.println("  3. ░ View Team");
            System.out.println("  4. ░ Back to Main Menu");
            System.out.print("  ► Select action: ");

            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                continue;
            }
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1 -> assignEmployeeToProject(selectedProject);
                case 2 -> removeEmployeeFromProject(selectedProject);
                case 3 -> viewProjectTeam(selectedProject);
                case 4 -> managing = false;
                default -> ui.showMessage("Invalid action.");
            }
        }
    }

    private void assignEmployeeToProject(Project project) {
        java.util.List<Employee> availableEmployees = new java.util.ArrayList<>(company.getEmployees());
        availableEmployees.removeAll(project.getAssignedEmployees());

        if (availableEmployees.isEmpty()) {
            ui.showMessage("No available employees to assign.");
            return;
        }

        ui.showEmployeeSelectionMenu(availableEmployees);
        int choice = ui.readEmployeeSelection(availableEmployees.size() + 1);

        if (choice < 1 || choice > availableEmployees.size()) {
            return;
        }

        Employee selectedEmployee = availableEmployees.get(choice - 1);
        project.assignEmployee(selectedEmployee);
        ui.showAssignmentResult(true, selectedEmployee.getName() + " assigned to " + project.getName());
    }

    private void removeEmployeeFromProject(Project project) {
        java.util.List<Employee> teamMembers = project.getAssignedEmployees();

        if (teamMembers.isEmpty()) {
            ui.showMessage("No team members to remove.");
            return;
        }

        System.out.println("\n  Current team members:");
        for (int i = 0; i < teamMembers.size(); i++) {
            Employee emp = teamMembers.get(i);
            System.out.println("  " + (i + 1) + ". " + emp.getName() + " [" + emp.getRoleName() + "]");
        }
        System.out.println("  " + (teamMembers.size() + 1) + ". Back");

        System.out.print("  ► Select employee to remove: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > teamMembers.size()) {
            return;
        }

        Employee selectedEmployee = teamMembers.get(choice - 1);
        project.removeEmployee(selectedEmployee);
        ui.showAssignmentResult(true, selectedEmployee.getName() + " removed from " + project.getName());
    }

    private void viewProjectTeam(Project project) {
        java.util.List<Employee> team = project.getAssignedEmployees();
        System.out.println("\n  Team members for " + project.getName() + ":");
        if (team.isEmpty()) {
            System.out.println("    No employees assigned yet.");
        } else {
            for (Employee emp : team) {
                System.out.println("    • " + emp.getName() + " [" + emp.getRoleName() + "] - Skill: " + emp.getSkill());
            }
        }
    }

    private void showProjectDetails() {
        if (company.getProjects().isEmpty()) {
            ui.showMessage("No projects available.");
            return;
        }

        System.out.println("\n  Select project to view:");
        for (int i = 0; i < company.getProjects().size(); i++) {
            System.out.println("  " + (i + 1) + ". " + company.getProjects().get(i).getName());
        }
        System.out.println("  " + (company.getProjects().size() + 1) + ". Back");

        System.out.print("  ► Select project: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > company.getProjects().size()) {
            return;
        }

        ui.showProjectDetails(company.getProjects().get(choice - 1));
    }

    private void advanceTurn() {
        if (allProjectsFinished()) {
            ui.showMessage("All projects are finished. Congratulations! Game over.");
            running = false;
        }
    }

    private boolean allProjectsFinished() {
        if (company.getProjects().isEmpty()) {
            return false;
        }

        // Check only started projects (not PLANNED)
        boolean hasStartedProjects = false;
        for (Project project : company.getProjects()) {
            // Count only projects that have been started
            if (project.getStatus() != ProjectStatus.PLANNED) {
                hasStartedProjects = true;
                // If any started project is not finished, game continues
                if (!project.isFinished()) {
                    return false;
                }
            }
        }

        // Game ends only if there are started projects and all are finished
        return hasStartedProjects;
    }
}
