import by.epam.like_it.controller.util.DescriptionImageParser;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.List;

public class DescriptionImageParserTest {

    private static final String DESCRIPTION="I have some problem with my styles." +
            "[img]Screenshot_26547595841040749504.png[/img]" +
            "[img]Screenshot_26547595841040749504.png[/img]" +
            "[img]Screenshot_26547595841040749504.png[/img]" +
            "Some Text" +
            "[img]Screenshot_26547595841040749504.png[/img]" +
            "Another text" +
            "[code]" +
            "Code" +
            "[/code]" +
            "[img]Screenshot_26547595841040749504.png[/img]";

    private static final String IMAGE="Screenshot_26547595841040749504.png";

    private static final int IMAGES_COUNT=5;

    @Test
    public void parseImagesTest(){
        List<String> images= DescriptionImageParser.parseImages(DESCRIPTION);
        for (String image: images){
            assertEquals(IMAGE,image);
        }
        assertEquals(IMAGES_COUNT,images.size(),0.0001);
    }
}
