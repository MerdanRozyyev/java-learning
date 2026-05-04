package com.example.Project_1;

import com.example.Project_1.Domain.Company;
import com.example.Project_1.Domain.Developer;
import com.example.Project_1.Domain.Tester;
import com.example.Project_1.Domain.Project;
import com.example.Project_1.Engine.GameEngine;
import com.example.Project_1.ConsoleUI.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        Company company = new Company("TechCorp", 50_000);

        company.hire(new Developer("Anna", 9, 8_000));
        company.hire(new Tester("Piotr", 6, 6_500));

        Project mobileApp = new Project("Mobile App", 30);
        Project website = new Project("Website", 20);

        for (var employee : company.getEmployees()) {
            mobileApp.addWorker(employee);
        }

        company.startProject(mobileApp);
        company.startProject(website);

        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(company, ui);

        engine.start();
    }
}
