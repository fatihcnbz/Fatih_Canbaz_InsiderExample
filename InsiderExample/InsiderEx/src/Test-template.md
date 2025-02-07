```java
package tests.XXXXXXXX;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.XXXXXXXXXX;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseRapor;

public class XXXCLASSNAMEXXX extends TestBaseRapor {

    @Test
    public void XXXTESTNAMEXXX(){
        extentTest = extentReports.createTest("Testin Adi","Bu adimin aciklamasi");

        extentTest.info("Bu adimin aciklamasi");
    
        extentTest.info("Bu adimin aciklamasi");
        
        extentTest.info("Bu adimin aciklamasi");
       
        extentTest.info("Bu adimin aciklamasi");
        
        extentTest.info("Bu adimin aciklamasi");
        
        extentTest.pass("Bu adimin aciklamasi");
       
        Driver.closeDriver();
    }
}
```