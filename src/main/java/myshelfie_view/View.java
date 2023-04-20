package myshelfie_view;

import myshelfie_controller.EventDispatcher;

public class View {

    protected EventDispatcher eventDispatcher;

    public View(EventDispatcher dispatcher) {
        eventDispatcher = dispatcher;
    }

}
