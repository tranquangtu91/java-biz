@echo off

echo "Check common-module"
if not exist "src/common-module" (
    echo "Add common-module"
    git submodule add --force -- "https://bitbucket.org/tranquangtu91/java-common" "src/main/java/com/base/common"
) else (
    echo "Common-module already exist"
)

echo "Check admin-module"
if not exist "src/admin-module" (
    echo "Add admin-module"
    git submodule add --force -- "https://bitbucket.org/tranquangtu91/java-admin" "src/main/java/com/base/admin"  
) else (
    echo "Admin-module already exist"
)

pause
