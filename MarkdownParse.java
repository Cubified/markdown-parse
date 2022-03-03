// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum ParserState {
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPEN_PAREN,
    CLOSE_PAREN,
    OPEN_BACKTICK,
    CLOSE_BACKTICK,
}

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // Find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            /*
            int tmp = markdown.indexOf("[", currentIndex++);
            if(tmp == -1) break;

            boolean escape = false, backtick = false;
            ParserState state = ParserState.OPEN_BRACKET;
            String sub = "";
            while(++tmp < markdown.length() &&
                  state.compareTo(ParserState.CLOSE_PAREN) < 0){
                char c = markdown.charAt(tmp);

                if(escape){
                    escape = false;
                    continue;
                }
                if(backtick){
                    if(c == '`') backtick = false;
                    continue;
                }

                switch(c){
                    case '\\':
                        escape = true;
                        break;
                    case ']':
                        if(state == ParserState.OPEN_BRACKET){
                            state = ParserState.CLOSE_BRACKET;
                        }
                        break;
                    case '(':
                        if(state == ParserState.CLOSE_BRACKET){
                            state = ParserState.OPEN_PAREN;
                        }
                        break;
                    case ')':
                        if(state == ParserState.OPEN_PAREN){
                            toReturn.add(sub);
                            state = ParserState.CLOSE_PAREN;
                        }
                        break;
                    case '`':
                        backtick = true;
                        break;
                    default:
                        if(state == ParserState.OPEN_PAREN){
                            sub += markdown.charAt(tmp);
                        }
                        break;
                }
            }
            currentIndex = tmp + 1;
            */

            
            int nextOpenBracket = markdown.indexOf("[", currentIndex),
                nextCloseBracket = markdown.indexOf("]", nextOpenBracket),
                openParen = markdown.indexOf("(", nextCloseBracket),
                closeParen = markdown.indexOf(")", openParen);

            if(nextOpenBracket > 0 &&
               markdown.charAt(nextOpenBracket-1) == '!'){
                // Image, skip
                currentIndex = nextOpenBracket + 1;
            } else if(nextOpenBracket >= 0 &&
                      nextCloseBracket >= 0 &&
                      nextCloseBracket > nextOpenBracket+1 &&
                      openParen == nextCloseBracket+1 &&
                      closeParen >= 0
            ){
                String sub = markdown.substring(openParen + 1, closeParen).trim();
                if(sub.indexOf(" ") > -1){
                    // Invalid link (contains non-trailing/leading whitespace),
                    //   advance one character
                    currentIndex += 1;
                } else {
                    // Valid link, add to list
                    toReturn.add(sub);
                    currentIndex = closeParen + 1;
                }
            } else {
                // Invalid link, advance one character
                currentIndex += 1;
            }
        }
        return toReturn;
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
		Path fileName = Path.of(args[0]);
        Map<String, List<String>> links = getLinks(fileName.toFile());
        System.out.println(links);
    }
}
