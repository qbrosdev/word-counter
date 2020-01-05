# Word Counter

## Getting Started

These instructions will guide you on how to build and run the project.
For more detailed explanations on the overall architecture of the 
project read [Architecture](Architecure.md#Architecture). 


## How to run

To run this application, the user needs to provide a path to the directory 
that contains the text files. This directory may contain several files. 
But the application will look for
text files (.txt) files and counts the words in them.     


#### Using CLI (Running the .jar file)

To create the runnable `JAR` file and executing it do the following steps:

Navigate to the root directory of the project 
```
cd ~/word-counter
```

Execute a `maven clean package`, to clean all previous builds and take the compiled code and package it in a `JAR` file.<br/>

```
mvn clean package
```

And the following output should be displayed
```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 12.680 s
[INFO] Finished at: 2020-01-03T17:49:58+03:30
[INFO] Final Memory: 21M/197M
[INFO] ------------------------------------------------------------------------
```
The `word-counter` project is now ready to run.

Navigate to the location of the produced file `WordCounter.jar` file

```
cd ~/word-counter/target
```
To start up the application, execute the following command.

```
java -jar WordCounter.jar -r Path/to/directory/containing/textfiles
```
<b>NOTE:</b> You can use double quotes to specify the directory which contains 
text files but don't use single quotes. 

<b>NOTE:</b> There are some other flags in the CLI. 
* -d debug: (default false) Show debugging information and stack traces
* -s strict (default false) Only count words that are consist of alphanumeric characters and underscore

#### Using Intellij-IDE
In IDE you can put the text files in the "input" directory and then run the application.
 

## Requirements
Since no particular framework is used in developing this solution, then there are no special requirements, nevertheless, this is the list of basic requirements for compiling and running this code:
 * Maven (3+)
 * Java 8

