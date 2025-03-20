@echo off
setlocal EnableDelayedExpansion

set gitPath="C:\Program Files\Git\cmd\git.exe"

echo Removing git submodules...

:: Array of module names
set modules=common admin

:: Loop through each module
for %%m in (%modules%) do (
    set "path=src/main/java/com/base/%%m"
    echo Removing %%m...
    %gitPath% submodule deinit -f !path!
    rmdir /S /Q ".git\modules\!path!"
    %gitPath% rm -f !path!
)

echo All modules removed successfully
pause
