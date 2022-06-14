package com.googlecode.jmoviedb.gui.moviedialog;

import org.eclipse.swt.custom.CTabFolder;

import com.googlecode.jmoviedb.model.movietype.AbstractMovie;

public interface IMovieDialogTab {
    void createTabArea(CTabFolder tabFolder);
    void configureListeners();
    void setModel(AbstractMovie m);
    void save(AbstractMovie movie);
    void dispose();
}
