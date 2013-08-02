@echo off
ECHO WELCOME!
set x=%1
set compression=%2
set api=%3
set debug=%4

ECHO DEODEXING JAR..
ECHO Compression=%compression%
ECHO API=%api%

cd framework
java -Xmx512m -jar baksmali.jar -x %x%.odex -a%api%
java -Xmx512M -jar smali.jar out/ -o classes.dex 

cd..
xcopy "framework\classes.dex" "%cd%\working\build\"
xcopy "apks\%x%.jar" "%cd%\working\"

cd working
7za x -o"build" %x%.jar >> LOG-JAR.txt
xcopy "7za.exe" "%cd%\build\"
cd build
7za a -tzip Done.jar * -mx%compression% >> LOG-JAR.txt
ren Done.jar Done.zip
7za d Done.zip 7za.exe >> LOG-JAR.txt
ren Done.zip Done.jar
cd..
cd..
xcopy "%cd%\working\build\Done.jar" "deodexed"

cd framework
del classes.dex
rmdir out /S /Q
cd..

cd deodexed
ren Done.jar %x%.jar
cd..
cd working
del %x%.jar
rmdir build /S /Q
md build
cd..
ECHO DEODEXED SUCCESSFULLY.
%debug%

