@ECHO OFF
ECHO Waiting for device..
working\adb wait-for-device
ECHO Pulling framework!
working\adb pull /system/framework "%cd%\framework"
ECHO DONE!
pause
exit