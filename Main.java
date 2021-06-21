package com.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:/snuse/chromedriver.exe");
        WebDriver drive = new ChromeDriver();
        ToDoClass tdc = new ToDoClass(drive);
        tdc.addTodo("Feed Nagatoro");
        tdc.addTodo("Don't give computer to Denis");
        tdc.addTodo("Play Fortnite with Mtthw");
        try {
            Thread.sleep(1000);
            tdc.deleteToDo(3);
            Thread.sleep(1000);
            tdc.setMarkAsCompleted(2);
            Thread.sleep(1000);
            tdc.viewActive();
            Thread.sleep(1000);
            tdc.viewCompleted();
            Thread.sleep(1000);
            tdc.viewAll();
            Thread.sleep(1000);
            tdc.editTodo("Feed Nagatoro x2");
        }catch(Exception e){

        }
    }
}
