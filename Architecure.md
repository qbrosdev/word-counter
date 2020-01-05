
# Architecture

This document contains information about the overall architecture of the 
proposed solution for the Word count problem. 
The proposed solution utilizes Java 8 streams and also CompletableFuture objects.

The solution was developed based on the following assumptions.
* The user provides the path to a directory that contains the text input files.
* Each input text file is UTF-8 encoded and for text inputs with other encodings, 
the application will show an error message.
* By default, the application will count every group of characters that are separated with space, as a word.
However, the application can run in strict mode. In which, only character groups that are consist of 
alphanumeric characters plus (_) are considered as a word. 
* For better performance and less memory consumption it is advised that the input files be 
multi line [see improvements](#Improvements).  


![Image description](https://github.com/qbrosdev/word-counter/blob/master/imgs/arch.JPG)

### Data Flow
The user specifies the path to the directory which contains the text files. Then the application 
(FileUtils.extractFilePathsFrom) 
tries to find all the ".txt" files in the path then an stream from words in the file is created (FileReader). 
Note that the definition of word can be strict as just 'alphanumeric plus underscore' character or it can be as 
loose as any combination of characters 
that are separated with spaces (this can be specified in the command line argument).

Then in order to process the stream of words that are extracted form each file, a separate word counter task
is generated. These tasks will run on separate threads and separately update the cache 
(a thread-safe collection of word count result objects).

Finally the main thread waits until all the counting task are finished and the cache is updated with the latest result.
Then another method will read all the cache entries and generates an stream of OutputEntry objects. 
Then these objects are 
shown in the console.  

## Improvements
The file reader tries to read the file, line by line. However in some cases the read line might be considerably large
(compared to the allocated memory to the application) therefore, memory exception could be thrown while converting the line into
stream of words. To address this problem we need to add a back-pressure mechanism to reduce the speed of the stream.    

Here are some other suggestions:
* Using a DI framework such as Spring to delegate all the instance creation/deletion.
* Add a logging framework to capture and log exceptions and other incidents


