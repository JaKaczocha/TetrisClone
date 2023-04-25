package com.k.logic;

public final class DownData {
    private final DeleteRow clearRow;
    private final ViewData viewData;

    public DownData(DeleteRow clearRow, ViewData viewData) {
        this.clearRow = clearRow;
        this.viewData = viewData;
    }

    public DeleteRow getClearRow() {
        return clearRow;
    }

    public ViewData getViewData() {
        return viewData;
    }
}
