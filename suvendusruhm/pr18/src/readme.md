## Task 1

Mis on SimpleFileVisitor? *(What is this SimpleFileVisitor?)* 

**Tegemist on Java sisseehitatud klassiga, mis võimaldab kõiki kasutas olevaid faile vaadata. Vigade korral viskab erindeid (IOException).**
https://www.geeksforgeeks.org/java-nio-file-simplefilevisitor-class-in-java/

Kas siin saab anonümse sisemise klassi lambdaga asendada? Miks või miks mitte? *(Can this anonymous inner class be replaced with a lambda? Why? Why not?)*
**[Dokumentatsiooni kohaselt](https://docs.oracle.com/javase/8/docs/api/java/nio/file/SimpleFileVisitor.html) on klassil neli meetodit, järelikult ei saa seda käsitleda funktsonaalse liidesena ning lambdafunktsiooni kasutamine on välistatud.**
```
Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
@Override
public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
filenames.add(file.toString());
return FileVisitResult.CONTINUE;
}
});
```

// TODO: Can we use method references anywhere? Why? Why not?


## Task 5

// TODO: Find which built-in functional interface from java.util.function could be used for the second argument (or if you can't find one, define it yourself)

https://www.geeksforgeeks.org/java-bifunction-interface-methods-apply-and-andthen/
https://docs.oracle.com/javase/8/docs/api/java/util/function/BiFunction.html

// TODO: Figure out the signature for combine method