# Why JMoviedb? #

My primary motivation for developing JMoviedb is to replace the now unusable [moviedb](moviedb.md) application. Having a movie collection of more than 1500 titles, I am completely dependent on a way to keep an overview. I am aware of a few free and non-free moviedb alternatives, but I don't like any of them very much.

This is mainly a hobby project, though seeing as I am currently a computer science student a little extra programming experience is probably not a bad idea.

### What's with the name? ###

It's written in Java and is meant as a replacement for moviedb. JMoviedb. Get it? Well, I couldn't think of anything else... You're obviously welcome to suggest a better name if you wish.

### Why SWT? ###

I'm using the [Eclipse SWT](http://www.eclipse.org/swt/) GUI toolkit instead of Swing. I prefer SWT because it uses the OS native GUI controls on all supported platforms, and because of this it will look better and behave more like a native GUI application. You only need to write one GUI and it automagiacally works on all platforms, and it will use the native GUI controls of each. Swing's default design isn't much to look at, and its layout managers are somewhat confusing. In addition, SWT is more pleasant to work with, and the JFace classes provides lots of helpful ready-to-use and half-implemented classes. (ApplicationWindow, ProgressMonitorDialog, etc.) Finally, it is always fun to learn something new!