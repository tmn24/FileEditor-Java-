# FileEditor-Java-
This is a very useful Java class that I made to streamline the File I/O process for my projects.  
I found it really annoying to have to constantly check the Java API for file operations since I used it sporatically. It also required a lot more in-depth work for simple operations than I liked. I decided to streamline this process by making this custom class do all the hard work for me so I could focus on the even harder projects I was working on. Not File I/O.

It is capable of:
1. Creating new files
2. Checking if files exist
3. Listing the files in a directory
4. Copy and pasting a file at a new path
5. Saving images
6. Writing to text files
7. Appending text files
8. Reading the whole text file at once
9. Writing to UTF-8 Binary files
10. Reading UTF-8 Binary Files
11. Reading UTF-8 Binary Files line by line

NOTE: When I made this (sophomore year of highs school) I didn't understand how Java handles OOP as well as I do now. So in order to use this you need to call the methods as an instance of the FileEditor class. I know, I know, I could've just made all of these methods static and saved myself a single line of code. But! It is useful if you want to do separate File I/O operations at once. You can create multiple instances of the FileEditor class and have them run independently. (This is because the appendWriting method keeps your data stored until closeFile is called and it writes it all at once.)
