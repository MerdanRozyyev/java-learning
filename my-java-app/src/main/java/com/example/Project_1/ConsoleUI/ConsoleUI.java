package com.example.Project_1.ConsoleUI;

import com.example.Project_1.Domain.Company;
import com.example.Project_1.Domain.Project;
import com.example.Project_1.Domain.Employee;
import com.example.Project_1.Domain.Developer;
import com.example.Project_1.Domain.Tester;
import com.example.Project_1.Domain.Intern;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);
    private static final String BORDER = "════════════════════════════════════════════════════";
    private static final String SEPARATOR = "────────────────────────────────────────────────────";

    public void showTurnHeader(int turn) {
        System.out.println();
        System.out.println(BORDER);
        System.out.println("               ► TURN " + turn + " ◄");
        System.out.println(BORDER);
    }

    public void showMainMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("            ★ MAIN MENU ★");
        System.out.println(SEPARATOR);
        System.out.println("  1. ░ Show Company Status");
        System.out.println("  2. ░ Start Planned Projects");
        System.out.println("  3. ░ Work on Projects");
        System.out.println("  4. ░ Hire Employee");
        System.out.println("  5. ░ Create New Project");
        System.out.println("  6. ░ Manage Project Team");
        System.out.println("  7. ░ Project Details");
        System.out.println("  8. ░ Exit Game");
        System.out.println(SEPARATOR);
    }

    public int readMenuChoice() {
        System.out.print("\n  ► Enter choice: ");

        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }

        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public void showCompanyStatus(Company company) {
        System.out.println("\n" + BORDER);
        System.out.println("            ★ COMPANY STATUS ★");
        System.out.println(BORDER);
        System.out.println("  Company Name: " + company.getName());
        System.out.println("  Cash: $" + String.format("%,.0f", company.getCash()));
        System.out.println("  Employees: " + company.getEmployees().size());
        System.out.println(SEPARATOR);
        
        if (company.getEmployees().isEmpty()) {
            System.out.println("  No employees hired yet.");
        } else {
            System.out.println("  EMPLOYEES:");
            for (Employee emp : company.getEmployees()) {
                System.out.println(String.format("    • %-20s [%s] - Skill: %d, Salary: $%.0f",
                        emp.getName(), emp.getRoleName(), emp.getSkill(), emp.getSalary()));
            }
        }
        System.out.println(SEPARATOR);
        
        if (company.getProjects().isEmpty()) {
            System.out.println("  No active projects.");
        } else {
            System.out.println("  PROJECTS:");
            for (Project project : company.getProjects()) {
                int progress = project.getProgress();
                int required = project.getRequiredWork();
                int percentage = (int) ((progress * 100) / required);
                String progressBar = createProgressBar(percentage, 20);
                
                System.out.println(String.format("    ◇ %-18s [%s]",
                        project.getName(), project.getStatus()));
                System.out.println(String.format("      Progress: %s %d%%",
                        progressBar, percentage));
                System.out.println(String.format("      %d/%d work units", progress, required));
            }
        }
        System.out.println(BORDER);
    }

    public void showHireMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("            ★ HIRE EMPLOYEE ★");
        System.out.println(SEPARATOR);
        System.out.println("  Select employee type:");
        System.out.println("  1. ░ Developer   (Salary: $8,000 | Skill: 8-10)");
        System.out.println("  2. ░ Tester      (Salary: $6,500 | Skill: 5-8)");
        System.out.println("  3. ░ Intern      (Salary: $3,000 | Skill: 3-5)");
        System.out.println("  4. ░ Back to Main Menu");
        System.out.println(SEPARATOR);
    }

    public int readHireChoice() {
        System.out.print("  ► Select type: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public String readEmployeeName() {
        System.out.print("  ► Enter employee name: ");
        return scanner.nextLine().trim();
    }

    public int readEmployeeSkill() {
        System.out.print("  ► Enter skill level (1-10): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }
        int skill = scanner.nextInt();
        scanner.nextLine();
        return skill;
    }

    public void showHireResult(boolean success, String message) {
        if (success) {
            System.out.println("\n  ✓ SUCCESS: " + message);
        } else {
            System.out.println("\n  ✗ FAILED: " + message);
        }
    }

    public void showMessage(String message) {
        System.out.println("\n  ► " + message);
    }

    public void showCreateProjectMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println("            ★ CREATE NEW PROJECT ★");
        System.out.println(SEPARATOR);
        System.out.println("  NOTE: New projects start in PLANNED state.");
        System.out.println("        Use 'Start Planned Projects' to activate them.");
        System.out.println(SEPARATOR);
    }

    public String readProjectName() {
        System.out.print("  ► Enter project name: ");
        return scanner.nextLine().trim();
    }

    public int readRequiredWork() {
        System.out.print("  ► Enter required work units (1-100): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }
        int work = scanner.nextInt();
        scanner.nextLine();
        return work;
    }

    public double readBudget() {
        System.out.print("  ► Enter project budget ($): ");
        if (!scanner.hasNextDouble()) {
            scanner.nextLine();
            return -1;
        }
        double budget = scanner.nextDouble();
        scanner.nextLine();
        return budget;
    }

    public int readDeadline() {
        System.out.print("  ► Enter deadline (turns): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }
        int deadline = scanner.nextInt();
        scanner.nextLine();
        return deadline;
    }

    public String readProjectPriority() {
        System.out.println("  Priority levels:");
        System.out.println("    1. LOW");
        System.out.println("    2. NORMAL");
        System.out.println("    3. HIGH");
        System.out.print("  ► Select priority (1-3): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return "NORMAL";
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 1 -> "LOW";
            case 3 -> "HIGH";
            default -> "NORMAL";
        };
    }

    public void showProjectCreationResult(boolean success, String message) {
        if (success) {
            System.out.println("\n  ✓ PROJECT CREATED: " + message);
        } else {
            System.out.println("\n  ✗ CREATION FAILED: " + message);
        }
    }

    public void showProjectDetails(Project project) {
        System.out.println("\n" + BORDER);
        System.out.println("            ★ PROJECT DETAILS ★");
        System.out.println(BORDER);
        System.out.println("  Project Name: " + project.getName());
        System.out.println("  Status: " + project.getStatus());
        System.out.println("  Priority: " + project.getPriority());
        System.out.println(SEPARATOR);
        System.out.println("  Work Progress: " + project.getProgress() + "/" + project.getRequiredWork() +
                " (" + String.format("%.1f", project.getCompletionPercentage()) + "%)");
        System.out.println("  Progress Bar: " + createProgressBar((int) project.getCompletionPercentage(), 20));
        System.out.println(SEPARATOR);
        System.out.println("  Budget: $" + String.format("%.0f", project.getBudget()));
        System.out.println("  Deadline: " + project.getDeadline() + " turns");
        System.out.println("  Turns Completed: " + project.getTurnsCompleted());
        if (project.isDeadlineExceeded()) {
            System.out.println("  ⚠ WARNING: Deadline exceeded!");
        }
        System.out.println(SEPARATOR);
        System.out.println("  Team Members: " + project.getTeamSize());
        if (project.getAssignedEmployees().isEmpty()) {
            System.out.println("    No employees assigned.");
        } else {
            for (Employee emp : project.getAssignedEmployees()) {
                System.out.println("    • " + emp.getName() + " [" + emp.getRoleName() + "] - Skill: " + emp.getSkill());
            }
        }
        System.out.println(BORDER);
    }

    public void showManageTeamMenu(Project project) {
        System.out.println("\n" + SEPARATOR);
        System.out.println("            ★ MANAGE TEAM - " + project.getName() + " ★");
        System.out.println(SEPARATOR);
    }

    public void showEmployeeSelectionMenu(java.util.List<Employee> employees) {
        System.out.println("\n  Available Employees:");
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            System.out.println("  " + (i + 1) + ". " + emp.getName() + " [" + emp.getRoleName() +
                    "] - Skill: " + emp.getSkill());
        }
        System.out.println("  " + (employees.size() + 1) + ". Back");
    }

    public int readEmployeeSelection(int maxOptions) {
        System.out.print("  ► Select employee (1-" + maxOptions + "): ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine();
            return -1;
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public void showAssignmentResult(boolean success, String message) {
        if (success) {
            System.out.println("\n  ✓ SUCCESS: " + message);
        } else {
            System.out.println("\n  ✗ FAILED: " + message);
        }
    }

    public boolean readYesNoInput(String question) {
        System.out.print("  " + question + " (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("yes") || answer.equals("y");
    }

    private String createProgressBar(int percentage, int length) {
        int filled = (percentage * length) / 100;
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) {
            sb.append(i < filled ? "█" : "░");
        }
        sb.append("]");
        return sb.toString();
    }
}
