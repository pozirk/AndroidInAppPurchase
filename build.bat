rm java\InAppPurchase\build\libInAppPurchase.jar

rm InAppPurchase.ane

xcopy java\InAppPurchase\bin\classes java\InAppPurchase\bin /S /Y /R

rd java\InAppPurchase\bin\classes /S /Q

jar.exe cvf java/InAppPurchase/build/libInAppPurchase.jar -C java/InAppPurchase/bin .

adt.bat -package -target ane InAppPurchase.ane as3/extension.xml -swc as3/InAppPurchase/bin/InAppPurchase.swc -platform Android-ARM -C java/InAppPurchase/build/ .