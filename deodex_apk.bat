@echo off
ECHO WELCOME!
set x=%1
set compress=%2
set api=%3
set debug=%4

ECHO Deodexing apk..
ECHO Compression=%compress%
ECHO API=%api%

xcopy "apks\%x%.odex" "framework"
cd framework
java -Xmx512m -jar baksmali.jar -x %x%.odex -a%api%
java -Xmx512M -jar smali.jar out/ -o classes.dex 

cd..
xcopy "framework\classes.dex" "%cd%\working\build\"
xcopy "apks\%x%.apk" "%cd%\working\"
cd working
7za x -o"build" %x%.apk >> Log-APK.txt
xcopy "7za.exe" "%cd%\build\"
cd build
7za a -tzip Done.apk * -mx%compress% >> Log-APK.txt
ren Done.apk Done.zip
7za d Done.zip 7za.exe >> Log-APK.txt
ren Done.zip Done.apk
cd..
cd..
xcopy "%cd%\working\build\Done.apk" "deodexed"
cd framework
rmdir out /S /Q
del %x%.odex
del classes.dex
cd..
cd deodexed
ren Done.apk %x%.apk
cd..
cd working
del %x%.apk
rmdir build /S /Q
md build
cd..
ECHO DEODEXED SUCCESSFULLY.
%debug%