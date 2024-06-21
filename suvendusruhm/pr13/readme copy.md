# Praktikum 13: classpath
#### Kopeeritud repo autor: [Märt Bakhoff](https://github.com/mbakhoff/classpath-template)

### Compiling an application
1. download *storage.zip* from the root of this repository:

```wget https://github.com/mbakhoff/classpath-template/blob/master/storage.zip```


2. unzip *storage.zip*:   ```unzip storage.zip``` EI TÖÖTA: 

   ```
   Archive:  storage.zip
   End-of-central-directory signature not found.  Either this file is not
   a zipfile, or it constitutes one disk of a multi-part archive.  In the
   latter case the central directory and zipfile comment will be found on
   the last disk(s) of this archive.
   unzip:  cannot find zipfile directory in one of storage.zip or
   storage.zip.zip, and cannot find storage.zip.ZIP, period.*, teen seda käsitsi
   ```


3. open the command line and navigate to the unpacked *storage* directory: ```cd storage```
   you should not leave this directory for the rest of this task.


4. check that the source files are in *src* and the gson jar is in *lib*.: ```ls src/* lib/*jar*```
   
   read the source code: ```nano  src/app/*```


5. create a directory named *build*: **mkdir build**
   this is where we want the compiler to put the compiled class files.
   by default the compiler will put the class files next to the source, but packaging the application is easier when the class files are in a separate directory.


6. run `javac -help` on the command line.


7. use *javac* to compile the code in *src*.
   specify *utf-8* for the character encoding of the source files (`-encoding`).
   place the generated class files in the *build* directory (`-d`).
   pass all the source files to *javac* at once (use relative paths).

```javac -encoding utf-8 -d build src/app/*.java```

```
src/app/StorageApp.java:3: error: package com.google.gson does not exist
import com.google.gson.Gson;
                      ^
src/app/StorageApp.java:10: error: cannot find symbol
    Gson gson = new Gson();
    ^
  symbol:   class Gson
  location: class StorageApp
src/app/StorageApp.java:10: error: cannot find symbol
    Gson gson = new Gson();
                    ^
  symbol:   class Gson
  location: class StorageApp
3 errors
```

   the source code references classes from package *com.google.gson*.
   try to compile the classes without setting the classpath: ``` javac -encoding utf-8 -d build```
   what error do you get? ```error: no source files```

   try again, but this time add gson-2.7.jar to the classpath (`-cp`): ```javac -encoding utf-8 -d build -cp lib/gson-2.7.jar src/app/*.java```


8. make sure the class files were created in *build* and not in *src*: ```ls build/app```

### Running the application

1. make sure you're still in the *storage* directory (don't leave it).
2. run `java -help` on the command line.
3. the main method is in *app.StorageApp*.
   try to execute the class using `java app.StorageApp`.
   note that the argument is a *fully qualified class name* (package name + class name), not a path to a *.class* file.
   the command should fail.
   ```
   Error: Could not find or load main class app.StorageApp
   Caused by: java.lang.ClassNotFoundException: app.StorageApp
   ```

   why can the JVM not find the main class? **because it's not compiled in the src directory**


4. try to start the application again, but this time add the *build* directory to the classpath (`-cp`): `java -cp build app.StorageApp`
   the command should still fail, but with a different error.

   ```
   Exception in thread "main" java.lang.NoClassDefFoundError: com/google/gson/Gson
   at app.StorageApp.main(StorageApp.java:10)
   Caused by: java.lang.ClassNotFoundException: com.google.gson.Gson
   at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:641)
   at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188)
   at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520)
   ... 1 more
   ```

   why can the JVM not find the `Gson` class? ***because the gson jar was passed during compilation and therefore isnät included in the files, so it needs to be manually passed at runtime***

   5. try to start the application again, but this time add both the *build* directory and the gson jar to the classpath.
      you should specify the `-cp` option only once (use `:` or `;` as the separator, depending on your OS): `java -cp build:lib/gson-2.7.jar app.StorageApp`
      the application should start up and generate some output.

   ```
   app.FileResult@6659c656
   {"fileName":"files/input0.txt","min":-42,"max":42,"sum":0}
   app.FileResult@22927a81
   Application finished successfully!
   ```

### Packaging the application

As mentioned earlier in the maven practice session, jar files are regular zip archives that contain class files and resources.
The JDK includes a command line tool to generate jar files.

1. make sure you're still in the *storage* directory (don't leave it).
2. run `jar --help` on the command line.
3. package the contents of the *build* directory into a jar *build.jar*.
   run `jar cvf build.jar -C build .` (dot is part of the command).
4. open the jar in your favourite archive tool and see what's inside: ` jar tf build.jar`
   ```
   META-INF/
   META-INF/MANIFEST.MF
   app/
   app/FileResult.class
   app/StorageApp.class
   ```
5. start the application again.
   this time only add the *build.jar* and gson jar to the classpath (exclude the build directory). `java -cp build.jar:lib/gson-2.7.jar app.StorageApp`

   ```
   app.FileResult@45283ce2
   {"fileName":"files/input0.txt","min":-42,"max":42,"sum":0}
   app.FileResult@1376c05c
   Application finished successfully!
   ```

### Loading classpath resources

1. clone this repository and open it in the IDE: `git clone https://github.com/mbakhoff/classpath-template.git` 
2. implement ClassGenerator#getStream so that the template.txt is loaded from the classpath using ClassLoader#getResourceAsStream.
3. open the command line and navigate to the project. `cd classpath-template`
4. run *mvn clean package*.
   this will compile the code and package the jar into the *target* directory.
   5. run the `generator.ClassGenerator` class from the command line.
      the classpath should contain only the *class-generator.jar* from *target*: `java -cp target/class-generator.jar generator.ClassGenerator`

   ```
   public class Toiduaine {
   
      private int koostisained;
      private List<Roog> vajabTähelepanu;
   
      public void setkoostisained(int koostisained) {
      this.koostisained = koostisained;
      }
   
      public int getkoostisained() {
      return koostisained;
      }
   
      public void setvajabTähelepanu(List<Roog> vajabTähelepanu) {
      this.vajabTähelepanu = vajabTähelepanu;
      }
   
      public List<Roog> getvajabTähelepanu() {
      return vajabTähelepanu;
      }
   
      @Override
      public String toString() {
      return "Toiduaine {" +
      "koostisained=" + koostisained +
      ", vajabTähelepanu=" + vajabTähelepanu + "}";
      }
   }
```
