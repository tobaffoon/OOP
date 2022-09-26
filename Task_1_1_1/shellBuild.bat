javadoc -d doc -charset utf-8  -sourcepath src\main\java -subpackages ru.nsu.amazyar
javac -sourcepath .\src\main\java -d bin src\main\java\ru\nsu\amazyar\Main.java
java -classpath ./bin ru.nsu.amazyar.Main