using OpenQA.Selenium;
using OpenQA.Selenium.Chrome;
using OpenQA.Selenium.Interactions;
using System;

namespace TodosTests
{
    public class TodosPage
    {
        private IWebDriver driver;

        public TodosPage(IWebDriver driver)
        {
            this.driver = driver;
        }
        IWebElement NewTodo => driver.FindElement(By.ClassName("new-todo"));
        IWebElement ToggleAll => driver.FindElement(By.CssSelector("label[for=toggle-all]"));
        IWebElement ItemsClener => driver.FindElement(By.CssSelector($"button.clear-completed"));
        IWebElement ItemsCounter => driver.FindElement(By.CssSelector("strong.ng-binding"));
        IWebElement AllItems => driver.FindElement(By.CssSelector($"ul.filters li:nth-child(1) a"));
        IWebElement ActiveItems => driver.FindElement(By.CssSelector($"ul.filters li:nth-child(2) a"));
        IWebElement CompletedItems => driver.FindElement(By.CssSelector($"ul.filters li:nth-child(3) a"));

        public void AddItem(string item)
        {
            NewTodo.SendKeys(item);
            NewTodo.Submit();
        }
        public void CompleteAll()
        {
            ToggleAll.Click();
        }
        public void CompleteItem(int id)
        { 
            driver.FindElement(By.CssSelector($"ul.todo-list li:nth-child({id}) input.toggle")).Click();
        }
        public void ClearItem(int id)
        {
            var selectedTask = driver.FindElement(By.CssSelector($"li:nth-child({id}) label.ng-binding"));

            var action = new Actions(driver);
            action.MoveToElement(selectedTask).Build().Perform();

            driver.FindElement(By.CssSelector($"li:nth-child({id}) button.destroy")).Click();
        }
        public void ClearCompletedItems() 
        {
            ItemsClener.Click();
        }

        public int GetTodoCount()
        {
            return Int32.Parse(ItemsCounter.GetAttribute("innerHTML"));
        }

        public int GetTabElementNumber() 
        {
            return driver.FindElements(By.CssSelector($"li.ng-scope")).Count;
        }

        public void ShowItems(ItemsStatus itemsStatus) =>
            (itemsStatus switch
            {
                ItemsStatus.All => AllItems,
                ItemsStatus.Active => ActiveItems,
                ItemsStatus.Completed => CompletedItems,
                _ => throw new ArgumentException()
            })
            .Click();


        public void Close()
        {
            driver.Quit();
        }

        public void GoToPage()
        {
            driver.Navigate().GoToUrl("https://todomvc.com/examples/angularjs/#/");
        }

    }

    public enum ItemsStatus
    {
        All,
        Active,
        Completed,
    }
}
