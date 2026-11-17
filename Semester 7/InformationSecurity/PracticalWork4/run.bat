@echo off
chcp 65001 >nul
if not exist out mkdir out
javac -encoding UTF-8 -d out src\main\java\ru\ijackdaniel\infsecurity\pr4\*.java
if errorlevel 1 pause & exit /b 1
copy /Y src\main\resources\bigrams.csv out\ >nul
java -Dfile.encoding=UTF-8 -cp out ru.ijackdaniel.infsecurity.pr4.Main
pause
