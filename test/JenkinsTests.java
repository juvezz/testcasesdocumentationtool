import main.logichelpers.JenkinsHelper;
import org.junit.Test;

import java.io.IOException;

public class JenkinsTests {
    @Test
    public void runBuild() throws IOException {
        JenkinsHelper.runJobWithFeatureAttribute("@cpfilters");
    }
}
