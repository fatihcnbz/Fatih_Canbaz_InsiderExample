```java
package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class FacebookPage {

    public FacebookPage() {

        PageFactory.initElements(Driver.getDriver(), this);

    }

    /*
     * ==========================================
     *   Main Page
     * ==========================================
     */


    //Classic Css Selector
    @FindBy(css = "tag#id.class")
    public WebElement elementByCss;
    
    //Classic Tag name - Attribute - Value Selector
    @FindBy(xpath = "//div[@id='elementId']")
    public WebElement elementByXPath;
    
    //Match Any Element Containing Partial Text
    @FindBy(xpath = "//*[contains(text(), 'partialText')]")
    public WebElement elementWithPartialText;

    //Match Specific Element Type Containing Partial Text
    @FindBy(xpath = "//div[contains(text(), 'partialText')]")
    public WebElement divWithPartialText;

    //Match Specific Element with Multiple Conditions
    @FindBy(xpath = "//div[contains(text(), 'partialText') and @class='someClass']")
    public WebElement specificDivWithPartialTextAndClass;

    //Match Anchor (Link) Element with Partial Text
    @FindBy(xpath = "//a[contains(text(), 'partialText')]")
    public WebElement linkWithPartialText;
    
    //Match Button Element with Partial Text
    @FindBy(xpath = "//button[contains(text(), 'partialText')]")
    public WebElement buttonWithPartialText;

    //Match Element with Partial Attribute Value
    @FindBy(xpath = "//input[contains(@name, 'partialText')]")
    public WebElement inputWithPartialName;
    
    // List types ↓
    @FindBy(xpath = "/div[@id='elementId']")
    public List<WebElement> listElementsByXPath;

    @FindBy(css = "tag#id.class")
    public List<WebElement> listElementsByCss;

    //Other basic selectors ↓
    @FindBy(id = "elementId")
    public WebElement elementById;

    @FindBy(name = "elementName")
    public WebElement elementByName;

    @FindBy(className = "elementClassName")
    public WebElement elementByClassName;

    @FindBy(tagName = "div")
    public WebElement elementByTagName;

    @FindBy(linkText = "Click me")
    public WebElement elementByLinkText;

    @FindBy(partialLinkText = "Partial Link")
    public WebElement elementByPartialLinkText;


}
```