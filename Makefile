CLASSPATH=".:lib/*"

MarkdownParse.class: MarkdownParse.java
	javac -cp $(CLASSPATH) MarkdownParse.java

MarkdownParseTest.class: MarkdownParseTest.java MarkdownParse.class
	javac -cp $(CLASSPATH) MarkdownParseTest.java

test: MarkdownParseTest.class
	java -cp $(CLASSPATH) org.junit.runner.JUnitCore MarkdownParseTest

TryCommonMark.class: TryCommonMark.java
	javac -cp $(CLASSPATH) TryCommonMark.java
