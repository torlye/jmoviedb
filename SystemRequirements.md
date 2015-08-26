## System requirements ##


### Summarized ###
  * Sun Java Runtime Environment, version 1.5 (5.0) or newer
  * Any combination of system architecture and operating system that supports Eclipse SWT. See [their website](http://www.eclipse.org/swt/) for a complete list.
  * An internet connection


### Details ###
An internet connection is not required to run the program but is recommended in order to download information from [IMDb](http://www.imdb.com/) when adding new movies to the database.

CPU and memory requirements are unknown, but JMoviedb will probably work satisfactorily on any system capable of running Windows 2000 or newer.

JMoviedb requires less than 10 MB of hard drive space.

JMoviedb will only be tested on a few operating systems and architectures, namely the following:
  * Windows - x86 and x86-64
  * Linux - x86, x86-64 and PowerPC
  * Mac OS X
Windows testing will mainly be done on Windows XP. The latest release of Xubuntu will be used for Linux testing, while an [OSx86](http://en.wikipedia.org/wiki/OSx86) release of Leopard will have to suffice for Mac testing.


#### Notes for 64-bit Windows: ####
JMoviedb will run in 64-bit mode on x86-64 systems, also known as AMD64, EM64T or x64. A 64-bit version of Windows (such as Windows XP Professional x64 Edition) and a 64-bit Java environment are needed.

Sun has a 64-bit version of Java 6 available, but it is not prominently displayed on their website and may be hard to find. Direct download link [here](http://javadl.sun.com/webapps/download/AutoDL?BundleId=12796). (Known to be working as of January 2008.)

The 32-bit version of JMoviedb will work perfectly on 64-bit systems using a 32-bit Java version. Seeing as most people use 32-bit Java even on 64-bit systems (because of browser plugin compatibility), a 64-bit Windows version of JMoviedb is probably unnecessary. I have chosen to include it nonetheless because of special interest, i.e. I'm a geek. The 32- and 64-bit versions of Java can coexist on the same system without problems.


#### BrowserLauncher2 notes: ####
JMoviedb uses the [BrowserLauncher2](http://browserlaunch2.sourceforge.net) library, which enables a Java program to open websites in the system's web browser. BrowserLauncher2 uses platform specific system calls, and naturally has limited platform and browser support. It should work with the most common counfigurations, though. (More details [here](http://browserlaunch2.sourceforge.net/platformsupport.shtml).) JMoviedb works fine without BrowserLauncher2 support, but the user will have to open any websites maually by copying/pasting URLs.