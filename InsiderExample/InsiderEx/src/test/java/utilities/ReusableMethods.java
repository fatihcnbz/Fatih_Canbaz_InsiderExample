package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.sukgu.Shadow;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ReusableMethods Class
 * <p>
 * This class provides a collection of reusable methods for common interactions with web elements,
 * such as executing JavaScript, scrolling, handling alerts, and waiting for specific conditions.
 * It aims to enhance test automation by promoting maintainability and reusability of code.
 * </p>
 * <p>
 * Author: Fatih Canbaz
 * </p>
 * <p>
 * Usage:
 * - Utilize the methods in this class to perform various actions on web elements and handle common scenarios.
 * - Enhance the efficiency and readability of your test automation scripts by leveraging these reusable methods.
 * </p>
 * <p>
 * Note: Before using methods from this class, ensure that the WebDriver instance (Driver.getDriver()) is set.
 * </p>
 * <p>
 * Index of Existing Methods:
 * - executeJavaScript(String script, Object[] args, boolean log): Executes JavaScript code on the page.
 * - scrollToElement(WebElement element): Scrolls the page to the specified WebElement.
 * - scrollDown(): Scrolls the page down.
 * - scrollUp(): Scrolls the page up.
 * - hoverOverElement(WebElement element, String elementName): Hovers over the specified WebElement.
 * - dragAndDrop(WebElement sourceElement, WebElement targetElement, String sourceElementName, String targetElementName): Drags and drops between two WebElements.
 * - switchToFrame(WebElement frameElement, String frameElementName): Switches to an IFrame.
 * - switchToDefaultContent(): Exits the current IFrame.
 * - shadowRootElementHandlingWithCss(String parentShadowLocator, String shadowElementLocator): Handles shadow DOM elements with CSS locators.
 * - shadowRootElementHandlingWithXPath(String parentShadowLocator, String shadowElementLocator): Handles shadow DOM elements with XPath locators.
 * - shadowRootElementHandlingWithWebElement(WebElement parentShadowRootElement, String shadowElementLocator): Handles shadow DOM elements with WebElement locators.
 * - acceptAlert(): Accepts (OK) an alert.
 * - dismissAlert(): Dismisses (Cancel) an alert.
 * - getAlertText(): Retrieves the text of an alert.
 * - sendKeysToAlert(String keysToSend): Sends keys to an alert.
 * - waitForPageTitle(String title, Duration timeout): Waits for the page title to match the specified text.
 * - waitForPageTitleToContain(String titlePart, Duration timeout): Waits for the page title to contain the specified text.
 * - waitForUrlToContain(String urlPart, Duration timeout): Waits for the page URL to contain the specified text.
 * - waitForUrlToBe(String url, Duration timeout): Waits for the page URL to match the specified text.
 * - waitForElementToBeInvisible(WebElement element, String elementName, Duration timeout): Waits for a WebElement to become invisible.
 * - waitForElementToBeVisible(WebElement element, String elementName, Duration timeout): Waits for a WebElement to become visible.
 * - waitForElementToBeClickable(WebElement element, String elementName, Duration timeout): Waits for a WebElement to be clickable.
 * - waitForElementToBeSelected(WebElement element, String elementName, Duration timeout): Waits for a WebElement to be selected.
 * - waitForElementToBeDeselected(WebElement element, String elementName, Duration timeout): Waits for a WebElement to be deselected.
 * - waitForElementAttributeToContain(WebElement element, String elementName, String attribute, String value, Duration timeout): Waits for a WebElement's attribute to contain a specific value.
 * - waitForElementAttributeToBe(WebElement element, String elementName, String attribute, String value, Duration timeout): Waits for a WebElement's attribute to be a specific value.
 * - waitForElementTextToContain(WebElement element, String elementName, String textPart, Duration timeout): Waits for a WebElement's text to contain the specified text.
 * - waitForElementTextToBe(WebElement element, String elementName, String text, Duration timeout): Waits for a WebElement's text to be the specified text.
 * </p>
 */

public class ReusableMethods {


    // Base directory for screenshots
    static String BASE_DIR = System.getProperty("user.dir") + "\\imgs\\";

    // WebDriver wait
    static WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));

    // Actions for performing mouse actions
    static Actions actions = new Actions(Driver.getDriver());

    // Alert timeout
    private static final Duration ALERT_TIMEOUT = Duration.ofSeconds(5);


    // ========================================
    // WAIT METHODS
    // ========================================

    /**
     * waitFor
     * <pre>
     * Saniye bazında hard wait yapar.
     * </pre>
     *
     * @param sec kac saniye beklenmesini istiyorsunuz
     * @author Fatih Canbaz
     */
    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * waitForPageToLoad
     * <pre>
     * Verilen Web sayfasının tam anlamıyla yüklediğini kontrol eder.
     * </pre>
     *
     * @param timeout maksimum kac saniye icerisinde sayfanin yuklenmesini
     *                istediginizi yaziniz (saniye)
     * @author Fatih Canbaz
     */
    public static void waitForPageToLoad(long timeout) {
        ExpectedCondition<Boolean> expectation = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
        }
    }

    // ========================================
    // SCREENSHOT METHODS
    // ========================================

    /**
     * getScreenshot
     * <pre>
     * Gorunen ekranin ekran goruntusunu alir.
     * </pre>
     *
     * @param name Ekran görüntüsü dosyasının adı
     * @return Dosyanın tam yolu
     * @throws IOException Dosya işlemleri sırasında oluşan hata durumunda
     * @author Fatih Canbaz
     */
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/src/tmp/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }

    /**
     * getScreenShotToWholePage
     * <pre>
     * Web sayfasının tamamının ekran görüntüsünü alır.
     * </pre>
     *
     * @param driver         WebDriver instance
     * @param screenshotIsmi Ekran görüntüsü dosyasının adı
     * @throws RuntimeException Dosya işlemleri sırasında oluşan hata durumunda
     * @author Fatih Canbaz
     */
    public static void getScreenshotToWholePage(WebDriver driver, String screenshotIsmi) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;

        // dosya adini dinamik yapalim
        // target/screenshots/tumSayfaSS.png
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter istenenFormat = DateTimeFormatter.ofPattern("yyMMddHHmm");
        localDateTime.format(istenenFormat); // 2310080829

        String dinamikDosyaAdi = "src/tmp/" + screenshotIsmi +
                localDateTime.format(istenenFormat) + ".jpg";
        File tumSayfaSS = new File(dinamikDosyaAdi);
        File geciciDosya = takesScreenshot.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(geciciDosya, tumSayfaSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getWebelementScreenshot
     * <pre>
     * WebElement'in ekran görüntüsünü alır.
     * </pre>
     *
     * @param istenenWebelement WebElement
     * @param screenshotIsmi    Ekran görüntüsü dosyasının adı
     * @throws RuntimeException Dosya işlemleri sırasında oluşan hata durumunda
     * @author Fatih Canbaz
     */
    public static void getWebelementScreenshot(WebElement istenenWebelement, String screenshotIsmi) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter istenenFormat = DateTimeFormatter.ofPattern("yyMMddHHmm");
        localDateTime.format(istenenFormat); // 2310080829

        String dinamikDosyaAdi = "src/tmp/" + screenshotIsmi +
                localDateTime.format(istenenFormat) + ".jpg";
        File webelementSS = new File(dinamikDosyaAdi);

        File geciciDosya = istenenWebelement.getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(geciciDosya, webelementSS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // ========================================
    // WINDOW HANDLING METHODS
    // ========================================

    /**
     * switchToWindow
     * <pre>
     * Handle window by title.
     * </pre>
     *
     * @param targetTitle Hedef pencerenin başlığı (window title)
     * @author Fatih Canbaz
     */
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    /**
     * closeOtherWindows
     * <pre>
     * Closes all windows except the main window.
     * </pre>
     *
     * @param mainWindowHandle Ana pencerenin window handle değeri (hash)
     * @author Fatih Canbaz
     */
    public static void closeOtherWindows(String mainWindowHandle) {
        for (String handle : Driver.getDriver().getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                Driver.getDriver().switchTo().window(handle);
                Driver.getDriver().close();
            }
        }
        Driver.getDriver().switchTo().window(mainWindowHandle);
    }

    // ========================================
    // ELEMENT INTERACTION METHODS
    // ========================================

    /**
     * clickElement
     * <pre>
     * WebElement'e tıklar.
     * </pre>
     *
     * @param element     Tıklanacak WebElement
     * @param elementName WebElement'in adı (loglarda görünmesi için)
     * @author Fatih Canbaz
     */
    public static void clickElement(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            System.out.println(elementName + " elementine tiklandi.");
        } catch (Exception e) {
            System.out.println(elementName + " elementine tiklanamadi. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * sendKeys
     * <pre>
     * WebElement'e değer yazar.
     * </pre>
     *
     * @param element     Deger yazilacak WebElement
     * @param value       Yazi değeri
     * @param elementName WebElement'in adı (loglarda görünmesi için)
     * @throws RuntimeException Deger yazilamadiginda
     * @autor Fatih Canbaz
     */
    public static void sendKeys(WebElement element, String value, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(value);
            System.out.println(elementName + " elementine deger yazildi: " + value);
        } catch (Exception e) {
            System.out.println(elementName + " elementine deger yazilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * getText
     * <pre>
     * WebElement'ten metin alır.
     * </pre>
     *
     * @param element     Metni alınacak WebElement
     * @param elementName WebElement'in adı (loglarda görünmesi için)
     * @return WebElement'in metni
     * @throws RuntimeException Metin alınamadığında
     * @autor Fatih Canbaz
     */
    public static String getText(WebElement element, String elementName) {
        try {
            String text = wait.until(ExpectedConditions.visibilityOf(element)).getText();
            System.out.println(elementName + " elementinin metni alindi: " + text);
            return text;
        } catch (Exception e) {
            System.out.println(elementName + " elementinin metni alinamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * clearInput
     * <pre>
     * WebElement'teki input alanını temizler.
     * </pre>
     *
     * @param element     Temizlenecek input alanı
     * @param elementName WebElement'in adı (loglarda görünmesi için)
     * @throws RuntimeException Temizleme işlemi başarısız olduğunda
     * @autor Fatih Canbaz
     */
    public static void clearInput(WebElement element, String elementName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element)).clear();
            System.out.println(elementName + " elementinin icerigi temizlendi.");
        } catch (Exception e) {
            System.out.println(elementName + " elementinin icerigi temizlenemedi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * selectDropdownByIndex
     * <pre>
     * Dropdown'dan bir öğe seçer.
     * </pre>
     *
     * @param dropdownElement Dropdown WebElement'i
     * @param index           Seçilecek öğe index'i
     * @param elementName     WebElement'in adı (loglarda görünmesi için)
     * @throws RuntimeException Dropdown'dan öğe seçilemediğinde
     * @autor Fatih Canbaz
     */
    public static void selectDropdownByIndex(WebElement dropdownElement, int index, String elementName) {
        try {
            Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOf(dropdownElement)));
            dropdown.selectByIndex(index);
            System.out.println(elementName + " dropdown'indan index ile secim yapildi. Index: " + index);
        } catch (Exception e) {
            System.out.println(elementName + " dropdown'indan index ile secim yapilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * selectDropdownByValue
     * <pre>
     * Dropdown'dan bir öğe seçer.
     * </pre>
     *
     * @param dropdownElement Dropdown WebElement'i
     * @param value           Seçilecek öğe değeri
     * @param elementName     WebElement'in adı (loglarda görünmesi için)
     * @throws RuntimeException Dropdown'dan öğe seçilemediğinde
     * @autor Fatih Canbaz
     */
    public static void selectDropdownByValue(WebElement dropdownElement, String value, String elementName) {
        try {
            Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOf(dropdownElement)));
            dropdown.selectByValue(value);
            System.out.println(elementName + " dropdown'indan value ile secim yapildi. Value: " + value);
        } catch (Exception e) {
            System.out.println(elementName + " dropdown'indan value ile secim yapilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * selectDropdownByText
     * <pre>
     * Dropdown'dan bir öğe seçer.
     * </pre>
     *
     * @param dropdownElement Dropdown WebElement'i
     * @param text            Seçilecek öğe metni
     * @param elementName     WebElement'in adı (loglarda görünmesi için)
     * @throws RuntimeException Dropdown'dan öğe seçilemediğinde
     * @autor Fatih Canbaz
     */
    public static void selectDropdownByText(WebElement dropdownElement, String text, String elementName) {
        try {
            Select dropdown = new Select(wait.until(ExpectedConditions.visibilityOf(dropdownElement)));
            dropdown.selectByVisibleText(text);
            System.out.println(elementName + " dropdown'indan text ile secim yapildi. Text: " + text);
        } catch (Exception e) {
            System.out.println(elementName + " dropdown'indan text ile secim yapilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * waitForTextToBePresentInElement
     * <pre>
     * WebElement'in belirtilen süre içinde belirtilen metni içermesini bekler.
     * </pre>
     *
     * @param element     Beklenecek WebElement
     * @param elementName WebElement'in adı (loglarda görünmesi için)
     * @param text        Beklenen metin
     * @param timeout     Maksimum bekleme süresi (saniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde metni içermiyorsa
     * @autor Fatih Canbaz
     */
    public static void waitForTextToBePresentInElement(WebElement element, String elementName, String text, int timeout) {
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            customWait.until(ExpectedConditions.textToBePresentInElementValue(element, text));
            System.out.println(elementName + " elementi " + timeout + " saniye içinde belirtilen metni içeriyor: " + text);
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye içinde belirtilen metni içermiyor. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementToHaveAttributeValue
     * <pre>
     * WebElement'in belirtilen süre içinde belirtilen attribute'a sahip olmasını bekler.
     * </pre>
     *
     * @param element     Beklenecek WebElement
     * @param elementName WebElement'in adı (loglarda görünmesi için)
     * @param attribute   Beklenen attribute
     * @param value       Beklenen attribute değeri
     * @param timeout     Maksimum bekleme süresi (saniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde attribute'a sahip olmuyorsa
     * @autor Fatih Canbaz
     */
    public static void waitForElementToHaveAttributeValue(WebElement element, String elementName, String attribute, String value, int timeout) {
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            customWait.until(ExpectedConditions.attributeToBe(element, attribute, value));
            System.out.println(elementName + " elementi " + timeout + " saniye içinde belirtilen attribute'a sahip oldu: " + attribute + "=" + value);
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye içinde belirtilen attribute'a sahip olmuyor. Hata: " + e.getMessage());
            throw e;
        }
    }

    // ========================================
    // JAVASCRIPT EXECUTION METHODS
    // ========================================

    /**
     * executeJavaScript
     * <pre>
     * JavaScript kodunu çalıştırır.
     * </pre>
     *
     * @param script Çalıştırılacak JavaScript kodu
     * @param args   Kod içinde kullanılacak argümanlar
     * @param log    Kodun çalıştırılma durumunu loglarda görmek için
     * @throws RuntimeException Kodun çalıştırılmasında hata oluştuğunda
     * @autor Fatih Canbaz
     */
    public static void executeJavaScript(String script, Object[] args, boolean log) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            if (args.length == 0) {
                js.executeScript(script);
            } else {
                js.executeScript(script, args);
            }
            if (log) {
                System.out.println("JavaScript kodu basariyla calistirildi: " + script);
            }
        } catch (Exception e) {
            System.out.println("JavaScript kodu calistirilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    /* ↓↓↓↓↓↓↓↓↓↓
     * Example 1: Execute Simple JavaScript
     * <pre>
     * Execute a simple JavaScript code without any arguments
     * </pre>
     *
     * executeJavaScript("alert('Hello, World!');", new Object[]{}, true);
     *
     * Example 2: Execute JavaScript with Arguments
     * <pre>
     * Execute JavaScript code with arguments
     * </pre>
     *
     * String color = "red";
     * executeJavaScript("document.body.style.backgroundColor = arguments[0];", new Object[]{color}, true);
     *
     * Example 3: Scroll to the Bottom of the Page
     * <pre>
     * Scroll to the bottom of the page using JavaScript
     * </pre>
     *
     * executeJavaScript("window.scrollTo(0, document.body.scrollHeight);", new Object[]{}, true);
     *
     * Example 4: Get Current URL
     * <pre>
     * Retrieve the current URL using JavaScript and log it
     * </pre>
     *
     * executeJavaScript("return window.location.href;", new Object[]{}, true);
     *
     * Example 5: Highlight an Element
     * <pre>
     * Highlight a specific element using JavaScript
     * </pre>
     *
     * WebElement elementToHighlight = driver.findElement(By.id("exampleElement"));
     * executeJavaScript("arguments[0].style.border='3px solid red';", new Object[]{elementToHighlight}, true);
     *
     * Example 6: Retrieve Title and Log
     * <pre>
     * Retrieve the page title using JavaScript and log it
     * </pre>
     *
     * executeJavaScript("var title = document.title; return title;", new Object[]{}, true);
     */

    /**
     * Belirtilen WebElement'i görünür alana getirmek için sayfayı kaydırır.
     *
     * @param element Görünür alana getirilecek WebElement.
     * @autor Fatih Canbaz
     */
    public static void scrollToElementInView(WebElement element) {
        try {
            executeJavaScript("arguments[0].scrollIntoView(true);", new Object[]{element}, false);
            System.out.println("Sayfa, WebElement'e kadar kaydirildi.");
        } catch (Exception e) {
            System.out.println("Sayfa, WebElement'e kadar kaydirilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Belirtilen WebElement'i sayfanın en üstüne getirmek için sayfayı kaydırır.
     *
     * @param driver  Tarayıcıyı kontrol eden WebDriver örneği.
     * @param element En üstüne getirilecek WebElement.
     * @autor Fatih Canbaz
     */
    public static void scrollToTopOfPage(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String script = "arguments[0].scrollIntoView(true);";
        jsExecutor.executeScript(script, element);
    }

    /**
     * Belirtilen WebElement'i görünür alanın ortasına getirmek için sayfayı kaydırır.
     *
     * @param driver  Tarayıcıyı kontrol eden WebDriver örneği.
     * @param element Görünür alanın ortasına getirilecek WebElement.
     * @autor Fatih Canbaz
     */
    public static void scrollToMiddleOfPage(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String script = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);" +
                "var elementTop = arguments[0].getBoundingClientRect().top;" +
                "window.scrollBy(0, elementTop - (viewPortHeight / 2));";
        jsExecutor.executeScript(script, element);
    }

    /**
     * Belirtilen WebElement'i sayfanın en altına getirmek için sayfayı kaydırır.
     *
     * @param driver  Tarayıcıyı kontrol eden WebDriver örneği.
     * @param element En altına getirilecek WebElement.
     * @autor Fatih Canbaz
     */
    public static void scrollToBottomOfPage(WebDriver driver, WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String script = "arguments[0].scrollIntoView(false);";
        jsExecutor.executeScript(script, element);
    }

    /**
     * Sayfayı yukarı kaydırır.
     *
     * @autor Fatih Canbaz
     */
    public static void scrollUpPage() {
        try {
            executeJavaScript("window.scrollBy(0,-250)", new Object[]{}, false);
            System.out.println("Sayfa yukarı kaydırıldı.");
        } catch (Exception e) {
            System.out.println("Sayfa yukarı kaydırılamadı. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Sayfayı aşağı kaydırır.
     *
     * @autor Fatih Canbaz
     */
    public static void scrollDownPage() {
        try {
            executeJavaScript("window.scrollBy(0,250)", new Object[]{}, false);
            System.out.println("Sayfa aşağı kaydırıldı.");
        } catch (Exception e) {
            System.out.println("Sayfa aşağı kaydırılamadı. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    // ========================================
    // MOUSE AND KEYBOARD INTERACTION METHODS
    // ========================================

    /**
     * hoverOverElement
     * <pre>
     * WebElement üzerine gelir (hover).
     * </pre>
     *
     * @param element     Üzerine gelilecek WebElement
     * @param elementName WebElement'in adı (loglarda görünmesi için)
     * @autor Fatih Canbaz
     */
    public static void hoverOverElement(WebElement element, String elementName) {
        try {
            actions.moveToElement(element).perform();
            System.out.println(elementName + " elementine hover yapildi.");
        } catch (Exception e) {
            System.out.println(elementName + " elementine hover yapilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * dragAndDrop
     * <pre>
     * Bir WebElement'i başka bir WebElement'e sürükler ve bırakır.
     * </pre>
     *
     * @param sourceElement     Sürüklenecek WebElement
     * @param targetElement     Bırakılacak WebElement
     * @param sourceElementName Sürüklenecek WebElement'in adı (loglarda görünmesi için)
     * @param targetElementName Bırakılacak WebElement'in adı (loglarda görünmesi için)
     * @autor Fatih Canbaz
     */
    public static void dragAndDrop(WebElement sourceElement, WebElement targetElement, String sourceElementName, String targetElementName) {
        try {
            actions.dragAndDrop(sourceElement, targetElement).perform();
            System.out.println(sourceElementName + " elementi " + targetElementName + " elementine suruklendi ve birakildi.");
        } catch (Exception e) {
            System.out.println(sourceElementName + " elementi " + targetElementName + " elementine suruklenemedi ve birakilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // ========================================
    // FRAME HANDLING METHODS
    // ========================================

    /**
     * switchToFrame
     * <pre>
     * IFrame'e geçiş yapar.
     * </pre>
     *
     * @param frameElement     IFrame'in WebElement'i
     * @param frameElementName IFrame'in adı (loglarda görünmesi için)
     * @autor Fatih Canbaz
     */
    public static void switchToFrame(WebElement frameElement, String frameElementName) {
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
            System.out.println(frameElementName + " IFrame'e gecildi.");
        } catch (Exception e) {
            System.out.println(frameElementName + " IFrame'e gecilemedi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * switchToDefaultContent
     * <pre>
     * IFrame'den çıkış yapar, ana sayfaya geri döner.
     * </pre>
     *
     * @autor Fatih Canbaz
     */
    public static void switchToDefaultContent() {
        try {
            Driver.getDriver().switchTo().defaultContent();
            System.out.println("Ana sayfaya geri donuldu.");
        } catch (Exception e) {
            System.out.println("Ana sayfaya geri donulurken hata olustu. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // ========================================
    // SHADOW DOM HANDLING METHODS
    // ========================================

    /**
     * Handles shadow DOM elements and returns the shadow element based on the provided CSS locators.
     *
     * @param parentShadowLocator  CSS locator of the parent shadow root element
     * @param shadowElementLocator CSS locator of the shadow root element
     * @return WebElement representing the shadow element
     * @autor Fatih Canbaz
     */
    public static WebElement shadowRootElementHandlingWithCss(String parentShadowLocator, String shadowElementLocator) {
        // Initialize Shadow object
        Shadow shadow = new Shadow(Driver.getDriver());

        // Wait for a short duration
        ReusableMethods.waitFor(1);

        // Find the parent shadow root element using CSS locator
        WebElement parentShadowRootElement = Driver.getDriver().findElement(By.cssSelector(parentShadowLocator));

        // Return the shadow element based on CSS locators
        return shadow.getShadowElement(parentShadowRootElement, shadowElementLocator);
    }

    /**
     * Handles shadow DOM elements and returns the shadow element based on the provided XPath locators.
     *
     * @param parentShadowLocator  XPath locator of the parent shadow root element
     * @param shadowElementLocator XPath locator of the shadow root element
     * @return WebElement representing the shadow element
     * @autor Fatih Canbaz
     */
    public static WebElement shadowRootElementHandlingWithXPath(String parentShadowLocator, String shadowElementLocator) {
        // Initialize Shadow object
        Shadow shadow = new Shadow(Driver.getDriver());

        // Wait for a short duration
        ReusableMethods.waitFor(1);

        // Find the parent shadow root element using XPath locator
        WebElement parentShadowRootElement = Driver.getDriver().findElement(By.xpath(parentShadowLocator));

        // Return the shadow element based on XPath locators
        return shadow.getShadowElement(parentShadowRootElement, shadowElementLocator);
    }

    /**
     * Handles shadow DOM elements and returns the shadow element based on the provided WebElement locators.
     *
     * @param parentShadowRootElement WebElement of the parent shadow root element
     * @param shadowElementLocator    XPath locator of the shadow root element
     * @return WebElement representing the shadow element
     * @autor Fatih Canbaz
     */
    public static WebElement shadowRootElementHandlingWithWebElement(WebElement parentShadowRootElement, String shadowElementLocator) {
        // Initialize Shadow object
        Shadow shadow = new Shadow(Driver.getDriver());

        // Wait for a short duration
        ReusableMethods.waitFor(1);

        // Return the shadow element based on WebElement locators
        return shadow.getShadowElement(parentShadowRootElement, shadowElementLocator);
    }


    // ========================================
    // ALERT HANDLING METHODS
    // ========================================

    /**
     * acceptAlert
     * <pre>
     * Alert'i kabul eder (OK).
     * </pre>
     *
     * @autor Fatih Canbaz
     */
    public static void acceptAlert() {
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), ALERT_TIMEOUT);
            customWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.getDriver().switchTo().alert();
            alert.accept();
            System.out.println("Alert kabul edildi.");
        } catch (Exception e) {
            System.out.println("Alert kabul edilemedi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * dismissAlert
     * <pre>
     * Alert'i reddeder (Cancel).
     * </pre>
     *
     * @autor Fatih Canbaz
     */
    public static void dismissAlert() {
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), ALERT_TIMEOUT);
            customWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.getDriver().switchTo().alert();
            alert.dismiss();
            System.out.println("Alert reddedildi.");
        } catch (Exception e) {
            System.out.println("Alert reddedilemedi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * getAlertText
     * <pre>
     * Alert'in metnini alır.
     * </pre>
     *
     * @return Alert'in metni
     * @throws RuntimeException Alert'in metni alınamadığında
     * @autor Fatih Canbaz
     */
    public static String getAlertText() {
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), ALERT_TIMEOUT);
            customWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.getDriver().switchTo().alert();
            String text = alert.getText();
            System.out.println("Alert metni alindi: " + text);
            return text;
        } catch (Exception e) {
            System.out.println("Alert metni alinamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * sendKeysToAlert
     * <pre>
     * Alert'e değer yazar.
     * </pre>
     *
     * @param keysToSend Yazılacak değer
     * @throws RuntimeException Değer yazılamadığında
     * @autor Fatih Canbaz
     */
    public static void sendKeysToAlert(String keysToSend) {
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), ALERT_TIMEOUT);
            customWait.until(ExpectedConditions.alertIsPresent());
            Alert alert = Driver.getDriver().switchTo().alert();
            alert.sendKeys(keysToSend);
            System.out.println("Alert'e deger yazildi: " + keysToSend);
        } catch (Exception e) {
            System.out.println("Alert'e deger yazilamadi. Hata: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // ========================================
    // WAIT METHODS
    // ========================================

    /**
     * waitForPageTitle
     * <pre>
     * Sayfa başlığının belirtilen metinle aynı olmasını bekler.
     * </pre>
     *
     * @param title               Beklenen sayfa başlığı
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @return true eğer başlık belirtilen süre içinde beklenen değere ulaşırsa, aksi takdirde false
     * @autor Fatih Canbaz
     */
    public static boolean waitForPageTitle(String title, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);

        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.titleIs(title));
            System.out.println("Sayfa başlığı " + timeout + " milisaniye içinde beklenen metinle aynı hale geldi: " + title);
            return true;
        } catch (TimeoutException e) {
            System.out.println("Sayfa başlığı " + timeout + " milisaniye içinde beklenen metinle aynı hale gelmedi. Hata: " + e.getMessage());
            return false;
        }
    }

    /**
     * waitForPageTitleToContain
     * <pre>
     * Sayfa başlığının belirtilen metni içermesini bekler.
     * </pre>
     *
     * @param titlePart           Beklenen metin
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @return true eğer başlık belirtilen süre içinde belirtilen metni içerirse, aksi takdirde false
     * @autor Fatih Canbaz
     */
    public static boolean waitForPageTitleToContain(String titlePart, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.titleContains(titlePart));
            System.out.println("Sayfa başlığı " + timeout + " milisaniye içinde belirtilen metni içeriyor: " + titlePart);
            return true;
        } catch (TimeoutException e) {
            System.out.println("Sayfa başlığı " + timeout + " milisaniye içinde belirtilen metni içermiyor. Hata: " + e.getMessage());
            return false;
        }
    }

    /**
     * waitForUrlToContain
     * <pre>
     * Sayfa URL'sinin belirtilen metni içermesini bekler.
     * </pre>
     *
     * @param urlPart             Beklenen metin
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @return true eğer URL belirtilen süre içinde belirtilen metni içerirse, aksi takdirde false
     * @autor Fatih Canbaz
     */
    public static boolean waitForUrlToContain(String urlPart, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.urlContains(urlPart));
            System.out.println("Sayfa URL'si " + timeout + " milisaniye içinde belirtilen metni içeriyor: " + urlPart);
            return true;
        } catch (TimeoutException e) {
            System.out.println("Sayfa URL'si " + timeout + " milisaniye içinde belirtilen metni içermiyor. Hata: " + e.getMessage());
            return false;
        }
    }

    /**
     * waitForUrlToBe
     * <pre>
     * Sayfa URL'sinin belirtilen metinle aynı olmasını bekler.
     * </pre>
     *
     * @param url                 Beklenen URL
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @return true eğer URL belirtilen süre içinde beklenen değere ulaşırsa, aksi takdirde false
     * @autor Fatih Canbaz
     */
    public static boolean waitForUrlToBe(String url, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.urlToBe(url));
            System.out.println("Sayfa URL'si " + timeout + " milisaniye içinde beklenen metinle aynı hale geldi: " + url);
            return true;
        } catch (TimeoutException e) {
            System.out.println("Sayfa URL'si " + timeout + " milisaniye içinde beklenen metinle aynı hale gelmedi. Hata: " + e.getMessage());
            return false;
        }
    }


    /**
     * waitForElementToBeInvisible
     * <pre>
     * WebElement'in belirtilen süre içinde görünmez olmasını bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde görünmez olmadığında
     * @autor Fatih Canbaz
     */
    public static void waitForElementToBeInvisible(WebElement element, String elementName, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.invisibilityOf(element));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde gorunmez hale geldi.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde gorunmez hale gelmedi. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementToBeVisible
     * <pre>
     * WebElement'in belirtilen süre içinde görünür olmasını bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde görünür olmadığında
     * @autor Fatih Canbaz
     */
    public static void waitForElementToBeVisible(WebElement element, String elementName, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.visibilityOf(element));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde gorunur hale geldi.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde gorunur hale gelmedi. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementToBeClickable
     * <pre>
     * WebElement'in belirtilen süre içinde tıklanabilir olmasını bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde tıklanabilir olmadığında
     * @autor Fatih Canbaz
     */
    public static void waitForElementToBeClickable(WebElement element, String elementName, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.elementToBeClickable(element));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde tiklanabilir hale geldi.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde tiklanabilir hale gelmedi. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementToBeSelected
     * <pre>
     * WebElement'in belirtilen süre içinde seçilmiş (selected) olmasını bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde seçilmiş olmadığında
     * @autor Fatih Canbaz
     */
    public static void waitForElementToBeSelected(WebElement element, String elementName, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.elementToBeSelected(element));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde secilmis hale geldi.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde secilmis hale gelmedi. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementToBeDeselected
     * <pre>
     * WebElement'in belirtilen süre içinde seçilmemiş (deselected) olmasını bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde seçilmemiş olmadığında
     * @autor Fatih Canbaz
     */
    public static void waitForElementToBeDeselected(WebElement element, String elementName, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.elementSelectionStateToBe(element, false));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde secilmemis hale geldi.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde secilmemis hale gelmedi. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementAttributeToContain
     * <pre>
     * WebElement'in belirtilen süre içinde belirtilen özniteliği (attribute) ve değeri içermesini bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param attribute           Beklenen öznitelik (attribute)
     * @param value               Beklenen değer
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde öznitelik ve değeri içermediğinde
     * @autor Fatih Canbaz
     */
    public static void waitForElementAttributeToContain(WebElement element, String elementName, String attribute, String value, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.attributeContains(element, attribute, value));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen ozelligi (" + attribute + ") ve degeri (" + value + ") iceriyor.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen ozelligi (" + attribute + ") ve degeri (" + value + ") icermiyor. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementAttributeToBe
     * <pre>
     * WebElement'in belirtilen süre içinde belirtilen özniteliği (attribute) ve değeri olmasını bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param attribute           Beklenen öznitelik (attribute)
     * @param value               Beklenen değer
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde öznitelik ve değeri olmadığında
     * @autor Fatih Canbaz
     */
    public static void waitForElementAttributeToBe(WebElement element, String elementName, String attribute, String value, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.attributeToBe(element, attribute, value));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen ozelligi (" + attribute + ") ve degeri (" + value + ") hale geldi.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen ozelligi (" + attribute + ") ve degeri (" + value + ") hale gelmedi. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementTextToContain
     * <pre>
     * WebElement'in belirtilen süre içinde belirtilen metni içermesini bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param textPart            Beklenen metin
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde metni içermediğinde
     * @autor Fatih Canbaz
     */
    public static void waitForElementTextToContain(WebElement element, String elementName, String textPart, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.textToBePresentInElement(element, textPart));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen metni (" + textPart + ") iceriyor.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen metni (" + textPart + ") icermiyor. Hata: " + e.getMessage());
            throw e;
        }
    }

    /**
     * waitForElementTextToBe
     * <pre>
     * WebElement'in belirtilen süre içinde belirtilen metni olmasını bekler.
     * </pre>
     *
     * @param element             Beklenecek WebElement
     * @param elementName         WebElement'in adı (loglarda görünmesi için)
     * @param text                Beklenen metin
     * @param timeoutMilliseconds Maksimum bekleme süresi (milisaniye cinsinden)
     * @throws TimeoutException Belirtilen süre içinde metin olmadığında
     * @autor Fatih Canbaz
     */
    public static void waitForElementTextToBe(WebElement element, String elementName, String text, int timeoutMilliseconds) {
        Duration timeout = Duration.ofMillis(timeoutMilliseconds);
        try {
            WebDriverWait customWait = new WebDriverWait(Driver.getDriver(), timeout);
            customWait.until(ExpectedConditions.textToBePresentInElement(element, text));
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen metni (" + text + ") iceriyor.");
        } catch (TimeoutException e) {
            System.out.println(elementName + " elementi " + timeout + " saniye icinde belirtilen metni (" + text + ") iceriyor. Hata: " + e.getMessage());
            throw e;
        }
    }


    /**
     * highLightToElement
     * <pre>
     * WebElement'in çevresinde bir vurgulama efekti oluşturur. Bu yöntem,
     * özellikle hata ayıklama sırasında belirli bir elementi vurgulamak için kullanılır.
     * Elementin arka planı sarı renkle ve kenarlığı kırmızı renkle gösterilir.
     * </pre>
     *
     * @param element Vurgulanacak WebElement
     * @return Vurgulanan WebElement (değişiklik yapmadan geri döndürür)
     * @autor Fatih Canbaz
     */
    public static WebElement highLightToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        return element;
    }




}
