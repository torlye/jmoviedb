## Completed or nearly completed ##
  * The main application window - menus, toolbars, status line etc.
  * Saving to and loading from the `*`.jmdb file format.
  * Embedded Derby SQL Database for storage.
  * A simple movie list in the main window showing the contents of the database.
  * Sorting the main window movie list on ID, title, year, rating or format. Ascending or descending sort direction.
  * A tabbed movie dialog box for viewing/editing detailed information about a movie.
  * Movie dialog: "Main" tab - movie title and cover image, most details about the movie itself is located here.
  * Movie dialog: "Tagline/plot" tab.
  * Movie dialog: "Actors" tab - a list of the cast.
  * Import data from IMDb
  * Import data from the CSV-format that moviedb uses - needs an improved GUI dialog.
  * Open a website in the system's browser when clicking a link in JMoviedb. Would be used to easily open a movie's IMDb page, etc. Will only work on [certain platforms](http://browserlaunch2.sourceforge.net/platformsupport.shtml). Intelligent handling of cases where it does not work is planned.
  * "Live" search feature, search as you type. - Done, except for selecting search parameters (currently it only searches through movie titles).
  * [Ant buildfile](http://ant.apache.org/) for build automation. Downloads build dependencies automatically and merges their contents into the application jar file, but filters so that only the referenced classes are used. Able to build jar files for all supported platforms in one go. In the future it may be extended to creating deb/rpm/pkg packages and the NSIS install script.
  * Windows installer/uninstaller based on [NSIS](http://nsis.sourceforge.net/).

## Under heavy development right now ##
  * An advanced movie list showing cover image, format, rating, runtime etc. for each movie type. Will include colour coding on seen/not seen status and format type. See [the moviedb page](moviedb.md) for details.
  * IMDb mass import - update all movies in the list with data from imdb.com.

## Not implemented yet ##
  * Movie dialog: "Format/video" tab - details about the format type (DVD, VHS, Blu-Ray etc) and the video format of the movie.
  * Movie dialog: "Audio/subtitles" tab - details about the audio/subtitle languages and formats.
  * Special handling of TV-series. Example: A TV-series entry in the database may represent a DVD box set containing season 1 of the series.
  * Filtering the main window movie list different criteria, for example "show only movies that I have not seen".
  * Importing data from plain text files.
  * Exporting data to other formats (plain text, CSV, HTML?).
  * .deb and .rpm packages for popular Linux distributions.
  * .pkg packages for OSX.

## Would be fun to have, but not very important right now ##
  * Loans - keep track of which movies you have lent to your buddies.
  * Printing support.
  * Barcode scanner support. Scan the UPC code on your DVD-covers and the movies are added automatically.
  * Show movie collection statistics with pretty pie charts, perhaps using the [Google Chart API](http://code.google.com/apis/chart/).