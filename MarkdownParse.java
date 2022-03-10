// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;

class LinkVisitor extends AbstractVisitor {
    ArrayList<String> links = new ArrayList<>();;

    @Override
    public void visit(Link link){
        links.add(link.getDestination());
    }
}

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        
        LinkVisitor vis = new LinkVisitor();
        document.accept(vis);
        return vis.links;
    }
    public static Map<String, List<String>> getLinks(File dirOrFile) throws IOException {
        Map<String, List<String>> result = new HashMap<>();
        if(dirOrFile.isDirectory()) {
            for(File f: dirOrFile.listFiles()) {
                result.putAll(getLinks(f));
            }
            return result;
        }
        else {
            Path p = dirOrFile.toPath();
            int lastDot = p.toString().lastIndexOf(".");
            if(lastDot == -1 || !p.toString().substring(lastDot).equals(".md")) {
                return result;
            }
            ArrayList<String> links = getLinks(Files.readString(p));
            result.put(dirOrFile.getPath(), links);
            return result;
        }
    }
    public static void main(String[] args) throws IOException {
        Map<String, List<String>> links = getLinks(Path.of(args[0]).toFile());
        System.out.println(links);
    }
}
