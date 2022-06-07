mvn install:install-file -Dfile="lib/BrowserLauncher2-1_3.jar" -DgroupId="edu.stanford.ejalbert" -DartifactId=BrowserLauncher2 -Dversion="1.3" -Dpackaging=jar

mvn install:install-file -Dfile="lib/org.eclipse.jface_3.25.0.v20220218-1636.jar" -DgroupId="org.eclipse" -DartifactId=jface -Dversion="3.25.0.v20220218-1636" -Dpackaging=jar

mvn install:install-file -Dfile="lib/org.eclipse.core.commands_3.10.100.v20210722-1426.jar" -DgroupId="org.eclipse.core" -DartifactId=commands -Dversion="3.10.100.v20210722-1426" -Dpackaging=jar

mvn install:install-file -Dfile="lib/org.eclipse.equinox.common_3.16.0.v20220211-2322.jar" -DgroupId="org.eclipse.equinox" -DartifactId=common -Dversion="3.16.0.v20220211-2322" -Dpackaging=jar

mvn install:install-file -Dfile="lib/org.eclipse.swt.gtk.linux.x86_64_3.119.0.v20220223-1102.jar" -DgroupId="org.eclipse" -DartifactId=swt -Dversion="3.119.0.v20220223-1102" -Dpackaging=jar
#mvn install:install-file -Dfile="lib/org.eclipse.swt.win32.win32.x86_64_3.119.0.v20220223-1102.jar" -DgroupId="org.eclipse" -DartifactId=swt -Dversion="3.119.0.v20220223-1102" -Dpackaging=jar

mvn install:install-file -Dfile="lib/KTable_2.2.0.jar" -DgroupId="de.kupzog" -DartifactId=ktable -Dversion="2.2.0" -Dpackaging=jar
