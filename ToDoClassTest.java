package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

class ToDoClassTest {
    //WebDriver driver = new ChromeDriver();
    //ToDoClass toDoClass = new ToDoClass(driver);
    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/snuse/chromedriver.exe");
    }

    @Test
    void addTodo() {
        WebDriver driver = new ChromeDriver();
        ToDoClass toDoClass = new ToDoClass(driver);
        String todo = "Let's GOOOOOO!";
        toDoClass.addTodo(todo);
        toDoClass.addTodo("Dio love");
        By li = By.xpath(toDoClass.getLabelPath(1));
        assertEquals(todo, driver.findElement(li).getText());
    }

    @Test
    void editTodo() {
        WebDriver driver = new ChromeDriver();
        ToDoClass toDoClass = new ToDoClass(driver);
        String todo = "Let's GOOOOOO!";
        String editedText = "Зашла улитка в бар";
        toDoClass.addTodo(todo);
        toDoClass.editTodo(editedText);
        By li = By.xpath(toDoClass.getLabelPath(1));
        assertEquals(editedText, driver.findElement(li).getText());
    }

    @Test
    void deleteToDo() {
        WebDriver driver = new ChromeDriver();
        ToDoClass toDoClass = new ToDoClass(driver);
        String todo = "Let's GOOOOOO!";
        toDoClass.addTodo(todo);
        toDoClass.addTodo("Dio love");
        toDoClass.deleteToDo(1);
        By li = By.xpath(toDoClass.getLabelPath(1));
        assertNotEquals(todo, driver.findElement(li).getText());
    }

    @Test
    void setMarkAsCompleted() {
        WebDriver driver = new ChromeDriver();
        ToDoClass toDoClass = new ToDoClass(driver);
        String todo = "Let's GOOOOOO!";
        String todo2 = "Dio love";
        int markNumber = 2;
        toDoClass.addTodo(todo);
        toDoClass.addTodo(todo2);
        toDoClass.setMarkAsCompleted(markNumber);
        By li = By.className("completed");
        assertEquals(todo2, driver.findElement(li).getText());
    }

    @Test
    void viewActive() {
        WebDriver driver = new ChromeDriver();
        ToDoClass toDoClass = new ToDoClass(driver);
        String todo = "ABOBA";
        String todo2 = "Nagatoro love";
        toDoClass.addTodo(todo);
        toDoClass.addTodo(todo2);
        toDoClass.setMarkAsCompleted(1);
        toDoClass.viewActive();
        By li = By.xpath(toDoClass.getLabelPath(1));
        assertEquals(todo2, driver.findElement(li).getText());
    }

    @Test
    void viewCompleted() {
        WebDriver driver = new ChromeDriver();
        ToDoClass toDoClass = new ToDoClass(driver);
        String todo = "ABOBA";
        String todo2 = "Dio love";
        int markNumber = 1;
        toDoClass.addTodo(todo);
        toDoClass.addTodo(todo2);
        toDoClass.setMarkAsCompleted(markNumber);
        toDoClass.setMarkAsCompleted(2);
        toDoClass.viewCompleted();
        By li = By.xpath(toDoClass.getLabelPath(markNumber));
        assertEquals(todo, driver.findElement(li).getText());
    }

    @Test
    void viewAll() {
        WebDriver driver = new ChromeDriver();
        ToDoClass toDoClass = new ToDoClass(driver);
        String todo = "ABOBA";
        String todo2 = "Nagatoro love";
        String todo3 = "Sell Dogecoin";
        toDoClass.addTodo(todo);
        toDoClass.addTodo(todo2);
        toDoClass.addTodo(todo3);
        toDoClass.setMarkAsCompleted(2);
        toDoClass.viewActive();
        toDoClass.viewAll();
        By li = By.xpath(toDoClass.getLabelPath(1));
        By li2 = By.xpath(toDoClass.getLabelPath(2 ));
        assertEquals(todo, driver.findElement(li).getText());
        assertEquals(todo2, driver.findElement(li2).getText());
    }
}