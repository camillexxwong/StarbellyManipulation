Use following command to run Image Comparer

java -jar ImageCompare.jar -b %BASELINEFOLDER -o %OUTPUTFOLDER -r %RESULTFILE
//In this way, you specified both baseline, output and resultsummary file
or

java -jar ImageCompare.jar -b %BASELINEFOLDER -o %OUTPUTFOLDER
//In this way, the resultsummary file will be put under output folder
or

java -jar ImageCompare.jar
// In this way, a GUI will pop up.



Note:
1. Only PNG format images are supported
2. Use Integrity Manager to open resultsummary file to view test results
3. Any problem, contact Wang, Xiaolei ( xiawang@microstrategy.com )