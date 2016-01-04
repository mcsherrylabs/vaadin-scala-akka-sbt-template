package sss.ui.template

import com.vaadin.annotations.{ Push, Theme }
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.navigator.{ Navigator, ViewChangeListener }
import com.vaadin.server.VaadinRequest
import com.vaadin.ui._
import sss.ui.reactor.UIReactor

import scala.language.implicitConversions

@Theme("template")
@Push
class TemplateUI extends UI with ViewChangeListener {

  override def init(vaadinRequest: VaadinRequest): Unit = {

    val uiReactor = UIReactor(this)
    val navigator = new Navigator(this, this)
    navigator.addViewChangeListener(this)
    val mainView = new MainView(uiReactor)
    val loginView = new LoginView(mainView.name, uiReactor)
    navigator.addView(LoginView.name, loginView)
    navigator.addView(mainView.name, mainView)
    navigator.navigateTo(mainView.name)
  }

  override def afterViewChange(viewChangeEvent: ViewChangeEvent): Unit = Unit

  override def beforeViewChange(viewChangeEvent: ViewChangeEvent): Boolean = {

    (getSession().getAttribute("user") != null, viewChangeEvent.getViewName == LoginView.name) match {
      case (false, false) =>
        getNavigator().navigateTo(LoginView.name); false
      case (false, true) => true
      case (true, _) => true
    }
    //true // <---- WARNING WARNING WARNING!
  }
}

