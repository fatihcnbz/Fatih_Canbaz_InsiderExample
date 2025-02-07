package tests.Automation_Exercises;


import org.testng.annotations.Test;
import pages.InsiderExercisePage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

import java.util.ArrayList;
import java.util.List;




public class AutomationExercise01 extends TestBaseRapor {

    InsiderExercisePage autEx = new InsiderExercisePage();

    @Test
    public void TC01() throws InterruptedException {
        extentTest = extentReports.createTest("Test Case 1: Register User", "User registers");
        // Step 1: Launch browser and navigate to the URL
        extentTest.info("Step 1: Visit https://useinsider.com/ and check Insider home page is opened or not");
        Driver.getDriver().get(ConfigReader.getProperty("autExUrl"));
        ReusableMethods.waitForPageToLoad(5);


        // Step 2: Select the “Company” menu in the navigation bar,
        extentTest.info("Step 2: Select the “Company” menu in the navigation bar");
        ReusableMethods.highLightToElement(autEx.headerCompanyLink).click();


        // Step 3: Select the “Careers” menu in the Compmay menu in the navigation bar,
        extentTest.info("Step 3: Select the “Careers” menu in the navigation bar");
        ReusableMethods.highLightToElement(autEx.headerCompanyCareersLink).click();


        // Step 4: Verify 'See all teams' is visible
        extentTest.info(" Step4 : Verify 'See all teams' is visible");
        ReusableMethods.highLightToElement(autEx.careersPageSeeAllTeams).isDisplayed();

        // Step 5: Verify 'Our Locations' is visible
        extentTest.info(" Step5 : Verify 'Our Locations' is visible");
        ReusableMethods.highLightToElement(autEx.careersPageOurLocations).isDisplayed();

        // Step 6: Verify 'Life at Insider' is visible
        extentTest.info(" Step6 : Verify 'Life at Insider' is visible");
        ReusableMethods.highLightToElement(autEx.careersPageLifeAtInsider).isDisplayed();


        // Step 7: Launch browser and navigate to the URL
        extentTest.info("Go to https://useinsider.com/careers/quality-assurance/");
        Driver.getDriver().get(ConfigReader.getProperty("autExUrl2"));
        ReusableMethods.waitForPageToLoad(5);


        // Step 8: click “See all QA jobs”, bekleme konulacak sonrasında geldiği görülecek.click sılınecek
        extentTest.info("click “See all QA jobs”");
        ReusableMethods.highLightToElement(autEx.careersQualityAssuranceSeeAllQaJobs).click();
        ReusableMethods.waitForPageToLoad(5);
        Thread.sleep(10000);


        // Step 9: click “Filter by Location”,
        extentTest.info("click “Filter by Location");
        Thread.sleep(10000);
        ReusableMethods.highLightToElement(autEx.careersFilterFilterByLocation).click();
        ReusableMethods.waitForPageToLoad(5);



        // Step 10: click “Istanbul/Turkey”,
        extentTest.info("click “Istanbul/Turkey”");
        ReusableMethods.highLightToElement(autEx.careersFilterIstanbulTurkiye).click();
        // Driver.getDriver().findElement(By.cssSelector("#select2-filter-by-location-result-3agb-Istanbul\\,\\ Turkiye")).click();
        ReusableMethods.waitForPageToLoad(5);



        // Step 11: click “view role”,
        extentTest.info("click “view role");
        ReusableMethods.scrollToMiddleOfPage(Driver.getDriver(), autEx.carreersFilterSeniorSofwareQualityAssuranceEngineer);
        Thread.sleep(10000);
        ReusableMethods.highLightToElement(autEx.carreersFilterSeniorSofwareQualityAssuranceEngineerViewRole).click();


        // Tabları listeleme
        extentTest.info("Tabları listeleme");
        List<String> browserTabs = new ArrayList<String>(Driver.getDriver().getWindowHandles());
        // Yeni taba geçme
        Driver.getDriver().switchTo().window(browserTabs.get(0));
        // işin bittiğinde şuanda olduğun tabı kapatma
        Driver.getDriver().close();
        //Dogru tabda oldugumuzun kontrolunu tittle ile yapıyoruz.





    }
}

