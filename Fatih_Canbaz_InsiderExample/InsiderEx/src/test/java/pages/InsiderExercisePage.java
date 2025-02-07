package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class InsiderExercisePage {

    public InsiderExercisePage() {

        PageFactory.initElements(Driver.getDriver(), this);

    }



    /*
     * ==========================================
     *   Main Page
     * ==========================================
     */


    //Main page  → Header → "Company" Link Locator
    @FindBy(xpath ="/html[1]/body[1]/nav[1]/div[2]/div[1]/ul[1]/li[6]/a[1]")
    public WebElement headerCompanyLink;


    //Main page  → Header → Company → Careers  Link Locator
    @FindBy(xpath ="/html[1]/body[1]/nav[1]/div[2]/div[1]/ul[1]/li[6]/div[1]/div[2]/a[2]")
    public WebElement headerCompanyCareersLink;





    /*
     * ==========================================
     *   Careers -> Quality Assurance
     * ==========================================
     */

    //Careers -> Quality Assurance -->SeeAllQaJobs
    @FindBy(xpath ="/html[1]/body[1]/div[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/a[1]")
    public WebElement careersQualityAssuranceSeeAllQaJobs;

    //Careers  → Filter → FilterByLocation → Careers  Link Locator
    @FindBy(xpath ="//span[@id='select2-filter-by-location-container']")
    public WebElement careersFilterFilterByLocation;


    //Careers  → Filter → FilterByLocation → IstanbulTurkiye  Link Locator
    @FindBy(xpath ="/html/body/span/span/span[2]/ul/li[2]" )
    public WebElement careersFilterIstanbulTurkiye;

    //Careers  → Filter → FilterByLocation → IstanbulTurkiye  Link Locator
    //@FindBy(xpath ="/html[1]/body[1]/section[2]/div[1]/div[1]/div[2]/div[1]/form[1]/div[2]/span[1]/span[1]/span[1]/span[2]")
    //public WebElement headerCareersLink5;

    //@FindBy(xpath ="//p[contains(text(),'Software QA Tester- Insider Testinium Tech Hub (Re')]")
    //public WebElement headerCareersLink6;


    //Careers  → Filter → QA JOBS →Senior Software Quality Assurance Engineer -> View Role Link Locator
    @FindBy(xpath ="//p[text()='Senior Software Quality Assurance Engineer']/following::a[1]")
    public WebElement carreersFilterSeniorSofwareQualityAssuranceEngineerViewRole;


    //Careers  → Filter → QA JOBS →Senior Software Quality Assurance Engineer  Link Locator
    @FindBy(xpath ="//p[text()='Senior Software Quality Assurance Engineer']")
    public WebElement carreersFilterSeniorSofwareQualityAssuranceEngineer;


    /*
     * ==========================================
     *   /Careers Page --
     * ==========================================
     */

    //Careers page  → "See All Teams" Text element
    @FindBy(xpath = "/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/a[1]")
    public WebElement careersPageSeeAllTeams;

    //Careers page  → "Our Locations" Text element
    @FindBy(xpath = "/html[1]/body[1]/div[1]/section[3]/div[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[1]/div[1]/h3[1]")
    public WebElement careersPageOurLocations;

    //Careers page  → "Life at Insider" Text element
    @FindBy(xpath = "/html[1]/body[1]/div[1]/section[4]/div[1]/div[1]/div[1]/div[1]/div[1]/h2[1]")
    public WebElement careersPageLifeAtInsider;






    //Login page  → Content → Form → "Name" input element
    @FindBy(xpath = "//section[@id='form']  //div[@class='signup-form'] //input[@name='name']")
    public WebElement loginContentFormNameInput;

    //Login page  → Content → Form → "Email Adress" input element
    @FindBy(xpath = "//section[@id='form']  //div[@class='signup-form'] //input[@name='email']")
    public WebElement loginContentFormEmailInput;

    //Login page  → Content → Form → "Singup" button element
    @FindBy(xpath = "//section[@id='form'] //div[@class='signup-form']  //button[contains(text(), 'Signup')]")
    public WebElement loginContentFormSignupButton;






}
