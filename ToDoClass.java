package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class ToDoClass {

    WebDriver driver;
    private static String TODOPATH = "/html/body/ng-view/section/section/ul/li[%d]";
    private static String deletePath = TODOPATH + "/div/";
    private By inputField1 = By.className("new-todo");
    private By toDoLabel = By.xpath("/html/body/ng-view/section/section/ul/li[1]/div/label");
    private String markAsCompletedString = "/html/body/ng-view/section/section/ul/li[%d]/div/input";
    private By activeButton = By.linkText("Active");
    private By completedButton = By.linkText("Completed");
    private By allButton = By.linkText("All");

    public ToDoClass(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://todomvc.com/examples/angularjs/#/");

    }

    public void addTodo(String todo){
        driver.findElement(inputField1).sendKeys(todo);
        driver.findElement(inputField1).sendKeys(Keys.RETURN);
    }

    public void editTodo(String editTodo){
        WebElement wb = driver.findElement(toDoLabel);
        Actions act = new Actions(driver);
        int textLength = wb.getText().length();
        act.doubleClick(wb).perform();
        for(int i = 0; i < textLength; i++){
            act.sendKeys(Keys.BACK_SPACE).perform();
        }
        act.sendKeys(editTodo);
        act.sendKeys(Keys.RETURN).build().perform();
    }

    public void deleteToDo(int number){
        By deleteLabel = By.xpath(String.format(deletePath, number) + "label");
        WebElement wb = driver.findElement(deleteLabel);
        Actions builder = new Actions(driver);
        builder.moveToElement(wb).build().perform();
        WebElement deleteButton = wb.findElement(By.xpath(String.format(deletePath, number) + "button"));
        deleteButton.click();
    }

    public void setMarkAsCompleted(int number){
        By markAsCompleted = By.xpath(String.format(markAsCompletedString, number));
        driver.findElement(markAsCompleted).click();
    }

    public void viewActive(){
        driver.findElement(activeButton).click();
    }

    public void viewCompleted(){
        driver.findElement(completedButton).click();
    }

    public void viewAll(){
        driver.findElement(allButton).click();
    }

    public String getLabelPath(int num){
        return String.format(TODOPATH, num);
    }
}
