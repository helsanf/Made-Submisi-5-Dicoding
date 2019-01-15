package com.example.helsanf.catalogemovie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackViewsFactory(this.getApplicationContext(), intent);

    }
}
