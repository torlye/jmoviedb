rem set PATH=%PATH%;%1

call mvn install:install-file -Dfile=.\lib\BrowserLauncher2-1_3.jar -DgroupId=edu.stanford.ejalbert -DartifactId=BrowserLauncher2 -Dversion=1.3 -Dpackaging=jar

call mvn install:install-file -Dfile=.\lib\org.eclipse.jface_3.10.0.v20140604-0740.jar -DgroupId=org.eclipse -DartifactId=jface -Dversion=3.10.0.v20140604-0740 -Dpackaging=jar

call mvn install:install-file -Dfile=.\lib\org.eclipse.core.commands_3.6.100.v20140528-1422.jar -DgroupId=org.eclipse.core -DartifactId=commands -Dversion=3.6.100.v20140528-1422 -Dpackaging=jar

call mvn install:install-file -Dfile=.\lib\org.eclipse.equinox.common_3.6.200.v20130402-1505.jar -DgroupId=org.eclipse.equinox -DartifactId=common -Dversion=3.6.200.v20130402-1505 -Dpackaging=jar

call mvn install:install-file -Dfile=.\lib\swt.jar -DgroupId=org.eclipse -DartifactId=swt -Dversion=3.659 -Dpackaging=jar

call mvn install:install-file -Dfile=.\lib\KTable_2.2.0.jar -DgroupId=de.kupzog -DartifactId=ktable -Dversion=2.2.0 -Dpackaging=jar

