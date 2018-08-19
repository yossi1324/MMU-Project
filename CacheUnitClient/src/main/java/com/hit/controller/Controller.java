package com.hit.controller;

import java.util.Observable;

public interface Controller extends java.util.Observer {

	void update(Observable arg0, Object arg1);

}
