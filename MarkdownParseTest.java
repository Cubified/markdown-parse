import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.*;

public class MarkdownParseTest {
    @Test
    public void addition() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void testGetLinks_testFile() throws IOException {
        String test_file = Files.readString(Path.of("test-file.md"));
        assertEquals(
            "getLinks() should get all links in a valid file",
            List.of("https://something.com", "some-page.html"),
            MarkdownParse.getLinks(test_file)
        );
    }

    @Test
    public void testGetLinks_breakingTest() throws IOException {
        String breaking_test = Files.readString(Path.of("breaking_test.md"));
        assertEquals(
            "getLinks() should find only valid links",
            List.of(
                "https:/https://ucsd-cse15l-w22.github.io/week/week3"
            ),
            MarkdownParse.getLinks(breaking_test)
        );
    }
    @Test
    public void testGetLinks_breakingTest2() throws IOException {
        String breaking_test_2 =
            Files.readString(Path.of("breaking_test_2.md"));
        assertEquals(
            "getLinks() should find no links in an invalid file " +
            "(missing opening square bracket)",
            List.of(),
            MarkdownParse.getLinks(breaking_test_2)
        );
    }
    @Test
    public void testGetLinks_breakingTest3() throws IOException {
        String breaking_test_3 =
            Files.readString(Path.of("breaking_test_3.md"));
        assertEquals(
            "getLinks() should find no link in an invalid file " +
            "(missing closing parenthesis)",
            List.of(),
            MarkdownParse.getLinks(breaking_test_3)
        );
    }

    /*
     * TEST FILES 2-8
     */
    @Test
    public void testGetLinks_testilFile2() throws IOException {
        String str =
            Files.readString(Path.of("test-file2.md"));
        assertEquals(
            "Validate MarkdownParse on test-file2.md",
            List.of("https://something.com", "some-page.html"),
            MarkdownParse.getLinks(str)
        );
    }
    @Test
    public void testGetLinks_testilFile3() throws IOException {
        String str =
            Files.readString(Path.of("test-file3.md"));
        assertEquals(
            "Validate MarkdownParse on test-file3.md",
            List.of(),
            MarkdownParse.getLinks(str)
        );
    }
    @Test
    public void testGetLinks_testilFile4() throws IOException {
        String str =
            Files.readString(Path.of("test-file4.md"));
        assertEquals(
            "Validate MarkdownParse on test-file4.md",
            List.of(),
            MarkdownParse.getLinks(str)
        );
    }
    @Test
    public void testGetLinks_testilFile5() throws IOException {
        String str =
            Files.readString(Path.of("test-file5.md"));
        assertEquals(
            "Validate MarkdownParse on test-file5.md",
            List.of(),
            MarkdownParse.getLinks(str)
        );
    }
    @Test
    public void testGetLinks_testilFile6() throws IOException {
        String str =
            Files.readString(Path.of("test-file6.md"));
        assertEquals(
            "Validate MarkdownParse on test-file6.md",
            List.of(),
            MarkdownParse.getLinks(str)
        );
    }
    @Test
    public void testGetLinks_testilFile7() throws IOException {
        String str =
            Files.readString(Path.of("test-file7.md"));
        assertEquals(
            "Validate MarkdownParse on test-file7.md",
            List.of(),
            MarkdownParse.getLinks(str)
        );
    }
    @Test
    public void testGetLinks_testilFile8() throws IOException {
        String str =
            Files.readString(Path.of("test-file8.md"));
        assertEquals(
            "Validate MarkdownParse on test-file8.md",
            List.of(),
            MarkdownParse.getLinks(str)
        );
    }
}
