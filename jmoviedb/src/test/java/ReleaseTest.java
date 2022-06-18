import org.eclipse.core.runtime.Assert;
import org.junit.Test;

import com.googlecode.jmoviedb.model.Release;

public class ReleaseTest {
    @Test
    public void setTerritoriesJson_1() {
        Release release = new Release();
        release.setTerritoriesJson(null);
        Assert.isTrue(release.getTerritories().size() == 0);
        release.setTerritoriesJson("");
        Assert.isTrue(release.getTerritories().size() == 0);

        release.setTerritoriesJson("[{\"United Kingdom\":\"PG\"},{\"Ireland\":\"12\"}]");
        Assert.isTrue(release.getTerritories().size() == 2);
        Assert.isTrue(release.getTerritories().get(0).getValue1().equals("United Kingdom"));
        Assert.isTrue(release.getTerritories().get(0).getValue2().equals("PG"));
        Assert.isTrue(release.getTerritories().get(1).getValue1().equals("Ireland"));
        Assert.isTrue(release.getTerritories().get(1).getValue2().equals("12"));
    }

    @Test
    public void setTerritoriesJson_2() {
        Release release = new Release();
        release.setTerritoriesJson(null, null);
        Assert.isTrue(release.getTerritories().size() == 0);
        release.setTerritoriesJson("", "");
        Assert.isTrue(release.getTerritories().size() == 0);

        release.setTerritoriesJson("[\"United Kingdom\",\"Ireland\"]", "[\"PG\",\"12\"]");
        Assert.isTrue(release.getTerritories().size() == 2);
        Assert.isTrue(release.getTerritories().get(0).getValue1().equals("United Kingdom"));
        Assert.isTrue(release.getTerritories().get(0).getValue2().equals("PG"));
        Assert.isTrue(release.getTerritories().get(1).getValue1().equals("Ireland"));
        Assert.isTrue(release.getTerritories().get(1).getValue2().equals("12"));
    }

    @Test
    public void getTerritoriesJson() {
        Release release = new Release();
        Assert.isTrue(release.getTerritoriesJson().equals("[]"));
        release.setTerritoriesJson("[\"United Kingdom\",\"Ireland\"]", "[\"PG\",\"12\"]");
        Assert.isTrue(release.getTerritoriesJson().equals("[{\"United Kingdom\":\"PG\"},{\"Ireland\":\"12\"}]"));
    }
}

/* https://www.blu-ray.com/dvd/Dead-Poets-Society-DVD/35375/	Døde Poeters Klub		[{"barcode":"7393834220601"},{"catalogNumber":"Z6D/30911014"}]	2002	["retail"]	[{"DVD":1}]
https://www.blu-ray.com/dvd/1944-The-Final-Defence-DVD/242170/	1944 The Final Defence	["United Kingdom","Ireland"]	[{"barcode":"5055002558740"},{"catalogNumber":"MTD5874"}]	2014	["retail"]	[{"DVD":1}]
*/